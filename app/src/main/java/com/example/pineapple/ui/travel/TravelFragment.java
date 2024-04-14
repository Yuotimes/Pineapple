package com.example.pineapple.ui.travel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pineapple.R;
import com.example.pineapple.ticketadapters.TravelAdapter;
import com.example.pineapple.ticketbeans.TravelInfo;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.ticketmanagers.SqlManager;
import com.example.pineapple.ticketutils.QueryBmobFlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class TravelFragment extends Fragment implements TravelAdapter.MyTravelClickListener {

    private ListView lv_travel;
    private TextView travel_title;
    private TextView travel_time;
    private CheckBox cb_all;
    private List<TravelInfo> travelInfoList;

    private TravelAdapter adapter;
    private TextView tv_total_price;
    private ImageButton btn_use;
  

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        initView(view);
        showTravelList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initView(View view){
        lv_travel = (ListView) view.findViewById(R.id.lv_travel);
        travel_title = (TextView) view.findViewById(R.id.travel_title);
        travel_time = (TextView) view.findViewById(R.id.travel_time);
        cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        btn_use = (ImageButton) view.findViewById(R.id.btn_use);

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                cb_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (travelInfoList.isEmpty())return;
                        for (TravelInfo travelInfo : travelInfoList){
                            travelInfo.setSelected(b);
                        }
                        adapter.notifyDataSetChanged();
                        getTravelInfoList(travelInfoList);
                    }
                });
            }
        });
        btn_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCount == 0){
                    return;
                }
                SqlManager manager = new SqlManager();
                BmobManager bmobManager = new BmobManager();
                List<TravelInfo> select = new ArrayList<>();

                for (TravelInfo travelInfo : travelInfoList){
                    if (travelInfo.isSelected()){
                        select.add(travelInfo);
                        bmobManager.flight2CreditBmob(getContext(), travelInfo.getObjectid(), travelInfo.getTitle(),travelInfo.getTime(),travelInfo.getImage(),travelInfo.getNumber(),travelInfo.getPrice());
                       
//                        manager.flight2CreditDB(getContext(),travelInfo.getTotal_price(),travelInfo.getTitle(),travelInfo.getTime(),travelInfo.getImage(),travelInfo.getNumber(),travelInfo.getPrice());
                    }
                }

                UserInfo info = manager.getLoginUser(getActivity());
                if (info != null) {
                    double point = Double.parseDouble(info.getPoint());
                    //在这一行，从用户信息中取出用户的积分信息，然后将其转换为 double 类型。
                    point += totalPrice;
//                    与我获得的总价相加
                    info.setPoint(String.format(Locale.getDefault(), "%.2f", point));
//                    将更新后的积分回调到用户对象中去
                    manager.updateUserInfo(getContext(), info);
//                    更新我的数据库
                    info.setPoint(info.getPoint());
                    SharedPreferences sp=getActivity().getSharedPreferences("UP",0);
                    String uid = sp.getString("uid", "");
                    info.update(uid, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.e("更新积分:","===更新成功===");
                            }else{
                                Log.e("更新积分:","更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }

                    });
                }

                Toast.makeText(getContext(), "兑换成功", Toast.LENGTH_SHORT).show();
                travelInfoList.removeAll(select);
                adapter.notifyDataSetChanged();
                getTravelInfoList(travelInfoList);

            }

        });
    }

    private void showTravelList(){
        QueryBmobFlight.queryFlightBmob(new QueryBmobFlight.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<TravelInfo> creditInfoList, Exception e) {
                travelInfoList = new ArrayList<>();
                travelInfoList.addAll(creditInfoList);
                adapter = new TravelAdapter(getContext(), travelInfoList, TravelFragment.this);
                lv_travel.setAdapter(adapter);
            }
        }, getContext());
    }

    @Override
    public void travelclickListener(View v){
        int position = (int) v.getTag();
        TravelInfo travelInfo = travelInfoList.get(position);
        String image = travelInfo.getImage();
        String title = travelInfo.getTitle();
        String time = travelInfo.getTime();
        String number = travelInfo.getNumber();
        String price = travelInfo.getPrice();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_image, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SqlManager manager = new SqlManager();
                BmobManager bmobManager = new BmobManager();
                bmobManager.deleteFlightBmob(getContext(), travelInfo.getObjectid(), travelInfo.getTitle(),travelInfo.getTime(),travelInfo.getImage(),travelInfo.getNumber(),travelInfo.getPrice());
//                manager.deleteFlightDB(getContext(),travelInfo.getId());
                travelInfoList.remove(travelInfo);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setPositiveButton("再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private int selectCount = 0;
    private double totalPrice = 0;
    @Override
    public void travelSelectListener(TravelInfo travelInfo, int position) {
        if (travelInfo.isSelected()) {
            selectCount = Math.min(travelInfoList.size(), ++selectCount);
            totalPrice += Double.parseDouble(travelInfo.getPrice());
        } else {
            selectCount = Math.max(0, --selectCount);
            totalPrice = Math.max(0, totalPrice - Double.parseDouble(travelInfo.getPrice()));
        }
        if (selectCount == travelInfoList.size()&& travelInfoList.size()!=0) {
            cb_all.setChecked(true);
        } else {
            cb_all.setChecked(false);
        }
        String totalPriceString = String.format(Locale.getDefault(), "%.2f", totalPrice);
        tv_total_price.setText("¥" + totalPriceString);

    }

     public void getTravelInfoList(List<TravelInfo> travelInfoList){
        this.travelInfoList =travelInfoList;
        calculateAndDisplaySelectedTotalPrice(tv_total_price);
     }

    public void calculateAndDisplaySelectedTotalPrice(TextView tv_total_price) {
        totalPrice = 0.00;
        selectCount = 0;
        for (TravelInfo travelInfo : travelInfoList) {
            if (travelInfo.isSelected()) {
                try {
                    double price = Double.parseDouble(travelInfo.getPrice());
                    totalPrice += price;
                    selectCount = Math.min(travelInfoList.size(), ++selectCount);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        String totalPriceString = String.format(Locale.getDefault(), "%.2f", totalPrice);

        tv_total_price.setText("¥" + totalPriceString);
        if (selectCount == travelInfoList.size() && travelInfoList.size()!=0) {
            cb_all.setChecked(true);
        } else {
            cb_all.setChecked(false);
        }
    }
}