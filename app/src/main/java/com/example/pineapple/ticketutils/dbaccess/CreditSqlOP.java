package com.example.pineapple.ticketutils.dbaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CreditSqlOP {
    private final MySqliteHelper creditSqlOP;
    public CreditSqlOP(Context context){
         creditSqlOP = new MySqliteHelper(context);

    }
    public long insertCredit(String total_price){
        SQLiteDatabase db = creditSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("total_price",total_price);
        long result = db.insert("credit",null,values);
        db.close();
        return result;
    }

    public long updateCredit(String number){
        SQLiteDatabase db = creditSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number",number);
        long result = db.update("credit", values, "total_price = ?", new String[]{});
        db.close();
        return result;

    }

    public long deleteCredit(int id){
        SQLiteDatabase db = creditSqlOP.getWritableDatabase();
        long reslut = db.delete("credit", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return reslut;
    }
}
