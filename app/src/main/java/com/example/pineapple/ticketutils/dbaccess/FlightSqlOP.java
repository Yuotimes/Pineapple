package com.example.pineapple.ticketutils.dbaccess;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class FlightSqlOP {
    private final MySqliteHelper flightSqlOP;

    public FlightSqlOP(Context context) {
         flightSqlOP = new MySqliteHelper(context);
    }
    public long insertFlight(Context context,String title,String time,String image,String number,String price){
        SharedPreferences sp=context.getSharedPreferences("UP",0);
        String account = sp.getString("account", "");
        SQLiteDatabase db = flightSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("time",time);
        values.put("image",image);
        values.put("number",number);
        values.put("price",price);
        values.put("account", account);
        long result = db.insert("flight", null, values);
        db.close();
        return result;
    }

    public long flightInsertCredit(Context context,String total_price, String title, String time, String image, String number, String price) {
        SharedPreferences sp=context.getSharedPreferences("UP",0);
        String account = sp.getString("account", "");
        SQLiteDatabase db = flightSqlOP.getWritableDatabase();
        db.delete("flight", "title = ?", new String[]{title});
        ContentValues values = new ContentValues();
        values.put("total_price", total_price);
        values.put("title", title);
        values.put("time", time);
        values.put("image", image);
        values.put("number", number);
        values.put("price", price);
        values.put("account", account);
        long result = db.insert("credit", null, values);
        db.close();
        return result;
    }


    public long deleteFlight(int id){
        SQLiteDatabase db = flightSqlOP.getWritableDatabase();
        long reslut = db.delete("flight", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return reslut;
    }
}
