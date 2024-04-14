package com.example.pineapple;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.shoppingactivities.ShoppingLoginActivity;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.databinding.ActivityHomeBinding;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.shoppingmanagers.CommodityManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BmobManager bmobManager = new BmobManager();
        GoodsManager goodsManager = new GoodsManager();
//        SqlManager manager = new SqlManager();
//        List<CommodityInfo> goodsList = manager.queryGoodsDB(this);
//        if (goodsList.isEmpty()) { //商品数据库为空导入数据进去
//            CommodityManager commodityManager = new CommodityManager();
//            List<CommodityInfo> commodityInfoList = commodityManager.getCommodity(this);
//
//
//        }
        BmobQuery<Goods> query = new BmobQuery<>("Goods");
        query.count(Goods.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
//                            manager.insertGoodsDB(HomeActivity.this, info);
                    if (count == 0) { //云端没有商品数据
                        CommodityManager commodityManager = new CommodityManager();
                        List<Commodity> commodityList = commodityManager.getCommodity(HomeActivity.this);
                        for (Commodity info : commodityList) {
                            goodsManager.saveGoods2Bmob(HomeActivity.this, info.getTitle(), info.getPrice(), info.getImage(), info.getLocation(), info.getDescription(), info.getNumber());
                        }
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    //客户端点击
    public void clickUser(View view) {
        startActivity(new Intent(this, ShoppingActivity.class));
        finish();
    }
    //管理端点击
    public void clickAdmin(View view) {
        startActivity(new Intent(this, AdminMainActivity.class));
        finish();
    }
}