package com.example.pineapple.ticketutils.dbaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CartSqlOP {
    private final MySqliteHelper cartSqlOP;
    public CartSqlOP(Context context){
        cartSqlOP = new MySqliteHelper(context);

    }

    public long insertCart(String title, String price,String location,String image) {
        SQLiteDatabase db = cartSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("price", price);
        values.put("image",image);
        values.put("location",location);
        long result = db.insert("cart", null, values);
        db.close();
        return result;
    }

    public long deleteCart(String title,String price,String location,String image){
        SQLiteDatabase db = cartSqlOP.getWritableDatabase();
        int result = db.delete("cart", "title = ?", new String[]{title});
        db.close();
        return result;
    }
}
