package com.example.pineapple.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.pineapple.R;
import com.example.pineapple.shoppingactivities.CommodityActivity;
import com.example.pineapple.shoppingadapters.CommodityAdapter;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.databinding.FragmentShoppingBinding;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;
import com.example.pineapple.ticketmanagers.SqlManager;
import com.example.pineapple.ticketutils.bmobaccess.GoodsBmobOP;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    private FragmentShoppingBinding binding;
    private List<Commodity> commodityList=new ArrayList<>();
    private List<Commodity> infoList = new ArrayList<>();
    private ListView lv_shopping;
    private SearchView search;
    private static final int WHAT_COMMODITYLIST_SUCCESS = 1;

    private CommodityAdapter mAdapter;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            switch (msg.what){
                case WHAT_COMMODITYLIST_SUCCESS:
                    commodityList = ((List<Commodity>) msg.obj);
                    infoList.clear();
                    infoList.addAll(commodityList);
                    mAdapter = new CommodityAdapter(getActivity(), infoList);
                    lv_shopping.setAdapter(mAdapter);
                    infoClick(commodityList);
                    break;
            }
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void initView(View view){
        lv_shopping = (ListView) view.findViewById(R.id.lv_shopping);
        search = (SearchView) view.findViewById(R.id.search);
        showCommodityList();

        search.setQueryHint("搜索");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                infoList.clear();
                if (TextUtils.isEmpty(newText)) {
                   if(commodityList==null){
                       commodityList=new ArrayList<>();
                   }

                    infoList.addAll(commodityList);

                } else {
                    for (Commodity info : commodityList) {
                        if (info.getTitle().contains(newText) || newText.contains(info.getTitle())) {
                            infoList.add(info);
                            System.out.println("===="+commodityList);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    private void showCommodityList(){
        SqlManager manager = new SqlManager();
        new Thread(){
            @Override
            public void run(){
//                List<CommodityInfo> commodityInfoList = manager.queryGoodsDB(getContext());
//                Message msg = Message.obtain();
//                msg.obj = commodityInfoList;
//                msg.what = WHAT_COMMODITYLIST_SUCCESS;
//                handler.sendMessage(msg);
                BmobManager bmobManager = new BmobManager();
                GoodsManager goodsManager = new GoodsManager();
                goodsManager.queryGoodsBmob(new GoodsBmobOP.OnQueryCompleteListener() {
                    @Override
                    public void onQueryComplete(List<Commodity> goodsList, Exception e) {
                        Message msg = Message.obtain();
                        msg.obj = goodsList;
                        msg.what = WHAT_COMMODITYLIST_SUCCESS;
                        handler.sendMessage(msg);
                    }
                });
            }
        }.start();
    }
    private void infoClick(List<Commodity> commodityList) {
        lv_shopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommodityActivity.class);
                Commodity commodity = commodityList.get(position);
                intent.putExtra("list", commodity);
                startActivity(intent);
            }
        });
    }
}