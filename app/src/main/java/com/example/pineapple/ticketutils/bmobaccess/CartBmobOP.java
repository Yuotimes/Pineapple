package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.pineapple.shoppingactivities.CommodityActivity;
import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.shoppingbeans.Commodity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CartBmobOP {
    public void insertCart2Bmob(Context context, Commodity commodity){
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Cart cart = new Cart();
        cart.setAccount(account);
        cart.setTitle(commodity.getTitle());
        cart.setPrice(commodity.getPrice());
        cart.setLocation(commodity.getLocation());
        cart.setImage(commodity.getImage());
        cart.setGoodsId(commodity.getObjectId());
        cart.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "加入失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                ((CommodityActivity)context).finish();
            }
        });
    }
    public void deleteCartFromBmob(Context context,String objectid,String title, String price,String location,String image) {
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Cart cart = new Cart();
        cart.setObjectId(objectid);
        cart.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e== null){
                    Log.e("deleteCart","success");
                }else {
                    Log.e("deleteCart","删除失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
}
