package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.pineapple.ticketactivities.MallActivity;
import com.example.pineapple.ticketbeans.Mall;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MallBmobOP {
    public void insertMall2Bmob(Context context,String title,String image,String price,String location,String description ){
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Mall mall = new Mall();
        mall.setAccount(account);
        mall.setTitle(title);
        mall.setImage(image);
        mall.setPrice(price);
        mall.setLocation(location);
        mall.setDescription(description);
        mall.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "购买失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                ((MallActivity) context).finish();

            }
        });
    }
}
