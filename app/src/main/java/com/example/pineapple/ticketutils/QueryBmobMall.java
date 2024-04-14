package com.example.pineapple.ticketutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pineapple.ticketbeans.Mall;
import com.example.pineapple.ticketbeans.MallInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class QueryBmobMall {

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<MallInfo> creditInfoList, Exception e);
    }

    @SuppressLint("Range")
    public static List<MallInfo> queryMallBmob(OnQueryCompleteListener listener, Context context) {
        SharedPreferences sp = context.getSharedPreferences("UP", 0);
        String account = sp.getString("account", "");
        BmobQuery<Mall> query = new BmobQuery<>("Mall");
        List<MallInfo> mallInfoList = new ArrayList<>();
        query.addWhereEqualTo("account", account);


        // 执行查询操作
        query.findObjects(new FindListener<Mall>() {
            @Override
            public void done(List<Mall> mallList, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    // 查询成功，flightList 是查询到的 Flight 对象列表
                    for (Mall mall : mallList) {
                        // 处理每个 Flight 对象
                        MallInfo mallInfo = new MallInfo();
                        String account = mall.getAccount();
                        String title = mall.getTitle();
                        String location = mall.getLocation();
                        String description = mall.getDescription();
                        String image = mall.getImage();
                        String price = mall.getPrice();
                        // 其他属性的获取方法类似

                        mallInfo.setTitle(title);
                        mallInfo.setDescription(description);
                        mallInfo.setLocation(location);

                        mallInfo.setImage(image);

                        mallInfo.setPrice(price);
                        mallInfo.setAccount(account);
                        mallInfoList.add(mallInfo);
                        System.out.println("-----"+mallInfoList);

                        // 在这里进行你的业务逻辑处理
                        // 调用回调接口，将查询结果传递给调用者
                        if (listener != null) {
                            listener.onQueryComplete(mallInfoList, null);
                        }
                    }
                } else {
                    // 查询失败，调用回调接口，将异常信息传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(null, e);
                    }
                    // 查询失败，输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }
            }
        });
        return mallInfoList;
    }
}
