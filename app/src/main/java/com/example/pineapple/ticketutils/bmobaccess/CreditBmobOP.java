package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.pineapple.ticketbeans.Credit;
import com.example.pineapple.ticketbeans.Flight;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CreditBmobOP {
    public void deleteFlightAndAddToCredit(Context context,String objectid, String title, String time, String image, String number, String price) {
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");

        Flight flight=new Flight();
        flight.setObjectId(objectid);
        flight.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("deleteFlight：","===删除成功===");
                    // 创建 Credit 对象并设置相应字段
                    Credit credit = new Credit();
                    credit.setTitle(title);
                    credit.setTime(time);
                    credit.setImage(image);
                    credit.setNumber(number);
                    credit.setPrice(price);
                    credit.setAccount(account);

                    // 保存 Credit 数据
                    credit.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(context, "删除成功，并已添加到Credit中", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "保存到Credit失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Log.e("deleteFlight：","删除失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void deleteCreditBmob(Context context, String objectid, String title, String time, String image, String number, String price) {
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");

        Credit credit =new Credit();
        credit.setObjectId(objectid);
        credit.delete(new UpdateListener() {
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

