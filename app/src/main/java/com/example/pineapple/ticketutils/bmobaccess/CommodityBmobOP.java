package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.shoppingbeans.Commodity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommodityBmobOP {

    public interface OnQueryCompleteListener {
        void onQueryComplete(Exception e);
    }

    public void insertCommodity2Bmob(Context context,String title,String price,String location,String image,String phone,String address,String goodsJson, OnQueryCompleteListener listener){
        Log.d("CommodityBmobOP", "Context: " + context);
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        System.out.println("-----"+account);
        Commodity commodity = new Commodity();
        commodity.setAccount(account);
        commodity.setTitle(title);
        commodity.setPrice(price);
        commodity.setLocation(location);
        commodity.setImage(image);
        commodity.setPhone(phone);
        commodity.setAddress(address);
        commodity.setGoodsJson(goodsJson);
        commodity.save(new SaveListener<String>() {
            @Override
            public void done(String string, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                    // 调用回调接口，将查询结果传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(null);
                    }
                } else {
                    Toast.makeText(context, "购买失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // 查询失败，调用回调接口，将异常信息传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(e);
                    }
                    // 查询失败，输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }
            }
        });
    }
    public void deleteCartAndAddToCommodity(Context context,String objectid,String title,String price,String location,String image,String phone,String address,String goodsJson){
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Cart cart = new Cart();
        cart.setObjectId(objectid);
        cart.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Commodity commodity = new Commodity();
                commodity.setAccount(account);
                commodity.setTitle(title);
                commodity.setPrice(price);
                commodity.setLocation(location);
                commodity.setImage(image);
                commodity.setPhone(phone);
                commodity.setAddress(address);
                commodity.setGoodsJson(goodsJson);
                commodity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(context, "删除成功，并已添加到Commodity中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "保存到Credit失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
    public void deleteCommodity(Context context,String objectid,String title,String price,String location,String image,String phone,String address,String goodsJson){
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Commodity commodity = new Commodity();
        commodity.setObjectId(objectid);
        commodity.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("deleteCredit：","===删除成功===");
                }else{
                    Log.e("deleteCredit：","删除失败："+e.getMessage()+","+e.getErrorCode());
                }

            }
        });

    }
}
