package com.example.pineapple.ticketutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pineapple.shoppingbeans.Commodity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QueryBmobCommodity {
    public static void queryCommodityBmob(Context context, OnQueryCompleteListener listener) {
        SharedPreferences sp = context.getSharedPreferences("UP", 0);
        String account = sp.getString("account", "");
        BmobQuery<Commodity> query = new BmobQuery<>("Commodity");
        List<Commodity> commodityInfoList = new ArrayList<>();
        query.addWhereEqualTo("account",account);
        query.findObjects(new FindListener<Commodity>() {
            @Override
            public void done(List<Commodity> commodityList, BmobException e) {
                if (e == null){
                    for (Commodity commodity : commodityList){
                        Commodity commodityInfo = new Commodity();
                        String account = commodity.getAccount();
                        String title = commodity.getTitle();
                        String image = commodity.getImage();
                        String location = commodity.getLocation();
                        String address = commodity.getAddress();
                        String goodsJson = commodity.getGoodsJson();
                        String phone = commodity.getPhone();
                        String price = commodity.getPrice();

                        commodityInfo.setObjectId(commodity.getObjectId());
                        commodityInfo.setTitle(title);
                        commodityInfo.setPrice(price);
                        commodityInfo.setLocation(location);
                        commodityInfo.setImage(image);
                        commodityInfo.setAddress(address);
                        commodityInfo.setGoodsJson(goodsJson);
                        commodityInfo.setGoodsList(new Gson().fromJson(goodsJson, new TypeToken<List<Commodity>>(){}.getType()));
                        commodityInfo.setPhone(phone);
                        commodityInfo.setAccount(account);
                        commodityInfoList.add(commodityInfo);
                        if (listener != null){
                            listener.onQueryComplete(commodityInfoList,null);
                        }
                    }

                }else {
                    if (listener != null) {
                        listener.onQueryComplete(null, e);
                    }
                    // 查询失败，输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }

            }
        });
    }

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<Commodity> commodityList, Exception e);
    }
}
