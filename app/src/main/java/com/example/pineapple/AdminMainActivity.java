package com.example.pineapple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.pineapple.shoppingadapters.CommodityAdminAdapter;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.databinding.ActivityShoppingAdminBinding;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;
import com.example.pineapple.ticketutils.bmobaccess.GoodsBmobOP;

import java.util.ArrayList;
import java.util.List;

//管理端首页
public class AdminMainActivity extends AppCompatActivity {

    private ActivityShoppingAdminBinding binding;
    private List<Commodity> commodityList;
    private List<Commodity> infoList = new ArrayList<>();
    private ListView lv_shopping;
    private SearchView search;
    private static final int WHAT_COMMODITYLIST_SUCCESS = 1;

    private CommodityAdminAdapter mAdapter;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            switch (msg.what){
                case WHAT_COMMODITYLIST_SUCCESS:
                    commodityList = ((List<Commodity>) msg.obj);
                    infoList.clear();
                    infoList.addAll(commodityList);
//                    SqlManager manager = new SqlManager();
                    BmobManager bmobManager = new BmobManager();
                    GoodsManager goodsManager = new GoodsManager();
                    mAdapter = new CommodityAdminAdapter(AdminMainActivity.this, infoList,
                    new CommodityAdminAdapter.CommodityAdminClickListener() {
                        @Override
                        public void commodityInfoAddListener(Commodity commodity, int position) {
//                            manager.updateGoodsDB(AdminMainActivity.this, commodityInfo);
                            goodsManager.updateGoodsBmob(commodity);
                        }

                        @Override
                        public void commodityInfoDeleteListener(Commodity commodity, int position) {
//                            manager.updateGoodsDB(AdminMainActivity.this, commodityInfo);
                            goodsManager.updateGoodsBmob(commodity);
                        }
                    });
                    lv_shopping.setAdapter(mAdapter);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView(){
        lv_shopping = (ListView) findViewById(R.id.lv_shopping);
        search = (SearchView) findViewById(R.id.search);
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
                    infoList.addAll(commodityList);
                } else {
                    for (Commodity info : commodityList) {
                        if (info.getTitle().contains(newText) || newText.contains(info.getTitle())) {
                            infoList.add(info);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
    private void showCommodityList(){
//        SqlManager manager = new SqlManager();
        new Thread(){
            @Override
            public void run(){
                GoodsManager goodsManager = new GoodsManager();
                BmobManager bmobManager = new BmobManager();
                goodsManager.queryGoodsBmob(new GoodsBmobOP.OnQueryCompleteListener() {
                    @Override
                    public void onQueryComplete(List<Commodity> goodsList, Exception e) {
                        Message msg = Message.obtain();
                        msg.obj = goodsList;
                        msg.what = WHAT_COMMODITYLIST_SUCCESS;
                        handler.sendMessage(msg);
                    }
                });
//                List<CommodityInfo> commodityInfoList = manager.queryGoodsDB(AdminMainActivity.this);
//                Message msg = Message.obtain();
//                msg.obj = commodityInfoList;
//                msg.what = WHAT_COMMODITYLIST_SUCCESS;
//                handler.sendMessage(msg);
            }
        }.start();
    }
}