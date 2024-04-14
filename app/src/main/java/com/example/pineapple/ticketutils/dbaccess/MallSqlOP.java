package com.example.pineapple.ticketutils.dbaccess;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class MallSqlOP {
    private final MySqliteHelper mallSqlOP;
    public MallSqlOP(Context context){
         mallSqlOP = new MySqliteHelper(context);
    }

    public long insertMall(Context context,String title,String price,String location,String image){
        SharedPreferences sp=context.getSharedPreferences("UP",0);
        String account = sp.getString("account", "");
        SQLiteDatabase db = mallSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("price", price);
        values.put("image",image);
        values.put("location",location);
        values.put("account", account);
        long result = db.insert("mall",null,values);
        db.close();
        return result;

    }
}
