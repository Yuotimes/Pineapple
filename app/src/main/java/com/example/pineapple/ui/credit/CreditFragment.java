package com.example.pineapple.ui.credit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pineapple.R;
import com.example.pineapple.ticketactivities.CreditActivity;
import com.example.pineapple.ticketadapters.CreditAdapter;
import com.example.pineapple.ticketbeans.CreditInfo;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.ticketmanagers.SqlManager;
import com.example.pineapple.ticketutils.QueryBmobCredit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class CreditFragment extends Fragment implements CreditAdapter.MyTravelClickListener{

    private ListView lv_credit;
    private TextView tv_point;
    private List<CreditInfo> travelInfoList;
    private CreditAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit, container, false);

        lv_credit = (ListView) view.findViewById(R.id.lv_credit);
        tv_point = (TextView) view.findViewById(R.id.tv_point);
        ImageButton btn_create = (ImageButton) view.findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreditActivity.class);
                startActivityForResult(intent, 886);
            }
        });

        showCreditList();

        showPoint();
        return view;
    }

    //设置当前用户的积分余额
    private void showPoint() {
        UserInfo info = new SqlManager().getLoginUser(getActivity());
        if (info != null) {
            tv_point.setText("积分余额：" + info.getPoint());
        }
    }

    //获取兑换票的纪录列表显示
    private void showCreditList(){
        QueryBmobCredit.queryCreditBmob(new QueryBmobCredit.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<CreditInfo> creditInfoList, Exception e) {
//                travelInfoList = QueryCreditDB.queryCreditDB(getContext());
                travelInfoList = new ArrayList<>();
                travelInfoList.addAll(creditInfoList);
                adapter = new CreditAdapter(getContext(),travelInfoList,CreditFragment.this);
                lv_credit.setAdapter(adapter);
                System.out.println("---"+travelInfoList);
            }
        }, getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    //弹窗确认退票操作
    private void deleteCredit(CreditInfo CreditInfo) {
        new AlertDialog.Builder(getActivity())
                .setTitle("温馨提示")
                .setMessage("确定要退票吗？")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //更新数据库通知信息表
                        BmobManager bmobManager = new BmobManager();
                        SqlManager manager = new SqlManager();

                        //更新用户积分余额
                        UserInfo info = manager.getLoginUser(getActivity());
                        if (info != null) {
                            double point = Double.parseDouble(info.getPoint());

                            //todo 这里加入积分扣成负数的情况，不能再进行退票操作，积分存入0到数据库
                            if (point <= 0) {
                                Toast.makeText(getContext(), "积分不足，退票失败", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //更新数据库通知信息表
                            bmobManager.deleteCreditBmob(getContext(),CreditInfo.getObjectId(),CreditInfo.getTitle(),CreditInfo.getTime(),CreditInfo.getImage(),CreditInfo.getNumber(),CreditInfo.getPrice());
//                            manager.deleteCreditDB(getContext(), CreditInfo.getId());
                            travelInfoList.remove(CreditInfo);
    //                        把积分订票从我的总订票中移走
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "退票成功", Toast.LENGTH_SHORT).show();

                            point -= Double.parseDouble(CreditInfo.getPrice());
                            if (point < 0) {
                                point = 0;
                            }
//                            积分减去我选中的商品价格
                            info.setPoint(String.format(Locale.getDefault(), "%.2f", point));
//                            回调我的userinfo中
//                            同步更新用户表
                            manager.updateUserInfo(getContext(), info);

                            tv_point.setText("积分余额：" + info.getPoint());

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
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    @Override
    public void travelclickListener(CreditInfo CreditInfo,int position) {
        //退票按钮点击事件
        deleteCredit(CreditInfo);
    }

    @Override
    public void travelSelectListener(CreditInfo CreditInfo, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        showPoint();
    }
}