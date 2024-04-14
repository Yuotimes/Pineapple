package com.example.pineapple.ticketutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pineapple.ticketbeans.Credit;
import com.example.pineapple.ticketbeans.CreditInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class QueryBmobCredit {

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<CreditInfo> creditInfoList, Exception e);
    }

    public static void queryCreditBmob(OnQueryCompleteListener listener, Context context) {
        SharedPreferences sp = context.getSharedPreferences("UP", 0);
        String account = sp.getString("account", "");
        BmobQuery<Credit> query = new BmobQuery<>("Credit");
        List<CreditInfo> travelInfoList = new ArrayList<>();
        query.addWhereEqualTo("account", account);

        // 执行查询操作
        query.findObjects(new FindListener<Credit>() {
            @Override
            public void done(List<Credit> creditList, cn.bmob.v3.exception.BmobException e) {
                if (e == null) {
                    // 查询成功，creditList 是查询到的 Credit 对象列表
                    for (Credit credit : creditList) {
                        // 处理每个 Credit 对象
                        CreditInfo travelInfo = new CreditInfo();
                        travelInfo.setObjectId(credit.getObjectId());
                        travelInfo.setTitle(credit.getTitle());
                        travelInfo.setTime(credit.getTime());
                        travelInfo.setImage(credit.getImage());
                        travelInfo.setNumber(credit.getNumber());
                        travelInfo.setPrice(credit.getPrice());
                        travelInfo.setAccount(credit.getAccount());
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
                    // 输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }
            }
        });
    }
}
