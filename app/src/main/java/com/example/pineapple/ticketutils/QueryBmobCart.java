package com.example.pineapple.ticketutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pineapple.shoppingbeans.Cart;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QueryBmobCart {

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<Cart> cartList, Exception e);
    }

    @SuppressLint("Range")
    public static void queryCartBmob(final OnQueryCompleteListener listener, Context context) {
        SharedPreferences sp = context.getSharedPreferences("UP", 0);
        String account = sp.getString("account", "");
        BmobQuery<Cart> query = new BmobQuery<>("Cart");
        query.addWhereEqualTo("account", account);

        query.findObjects(new FindListener<Cart>() {
            @Override
            public void done(List<Cart> cartList, BmobException e) {
                if (e == null) {
                    // 调用回调接口，将结果传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(cartList, null);
                    }
                } else {
                    // 查询失败，调用回调接口，将异常信息传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(null, e);
                    }
                    // 输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }
            }
        });
    }
}
