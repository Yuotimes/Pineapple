package com.example.pineapple.ticketutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.ticketbeans.TravelInfo;
import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class QueryFlightDB {


    @SuppressLint("Range")
    public static List<TravelInfo> queryFlightDB(Context context){
        SharedPreferences sp=context.getSharedPreferences("UP",0);
        String accountS = sp.getString("account", "");
        List<TravelInfo> travelInfoList = new ArrayList<>();
        MySqliteHelper helper = new MySqliteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from flight where account=?", new String[]{accountS});
        if (cursor !=null){
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    TravelInfo travelInfo = new TravelInfo();
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    String account = cursor.getString(cursor.getColumnIndex("account"));

                    travelInfo.setId(id);
                    travelInfo.setTitle(title);
                    travelInfo.setTime(time);
                    travelInfo.setImage(image);
                    travelInfo.setNumber(number);
                    travelInfo.setPrice(price);
                    travelInfo.setAccount(account);
                    travelInfoList.add(travelInfo);

                }
            }
            cursor.close();

        }
        db.close();
        return travelInfoList;
    }

}
