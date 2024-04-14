package com.example.pineapple.ticketutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pineapple.ticketbeans.Flight;
import com.example.pineapple.ticketbeans.TravelInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class QueryBmobFlight {

    @SuppressLint("Range")
    public interface OnQueryCompleteListener {
        void onQueryComplete(List<TravelInfo> creditInfoList, Exception e);
    }

    @SuppressLint("Range")
    public static List<TravelInfo> queryFlightBmob(OnQueryCompleteListener listener,  Context context) {
        SharedPreferences sp = context.getSharedPreferences("UP", 0);
        String account = sp.getString("account", "");
        BmobQuery<Flight> query = new BmobQuery<>("Flight");
        List<TravelInfo> travelInfoList = new ArrayList<>();
        query.addWhereEqualTo("account", account);

        // 执行查询操作
        query.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> flightList, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    // 查询成功，flightList 是查询到的 Flight 对象列表
                    for (Flight flight : flightList) {
                        // 处理每个 Flight 对象
                        TravelInfo travelInfo = new TravelInfo();
                        String account = flight.getAccount();
                        String title = flight.getTitle();
                        String time = flight.getTime();
                        String image = flight.getImage();
                        String number = flight.getNumber();
                        String price = flight.getPrice();
                        // 其他属性的获取方法类似

                        travelInfo.setTitle(title);
                        travelInfo.setTime(time);
                        travelInfo.setImage(image);
                        travelInfo.setNumber(number);
                        travelInfo.setPrice(price);
                        travelInfo.setAccount(account);
                        travelInfo.setObjectid(flight.getObjectId());
                        travelInfoList.add(travelInfo);
                        // 在这里进行你的业务逻辑处理
                    }
                    // 调用回调接口，将查询结果传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(travelInfoList, null);
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
        return travelInfoList;
    }

}
