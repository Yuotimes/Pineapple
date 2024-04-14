package com.example.pineapple.ticketutils.dbaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.shoppingbeans.Commodity;


public class CommoditySqlOP {
    private final MySqliteHelper commoditySqlOP;

    public CommoditySqlOP(Context context) {
        commoditySqlOP = new MySqliteHelper(context);
    }

    public long insertCommodity(Commodity info) {
        SQLiteDatabase db = commoditySqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", info.getTitle());
        values.put("price", info.getPrice());
        values.put("image",info.getImage());
        values.put("location",info.getLocation());
        values.put("goodsJson",info.getGoodsJson());
        values.put("phone",info.getPhone());
        values.put("address",info.getAddress());
        long result = db.insert("commodity", null, values);
        db.close();
        return result;
    }

    public long cartInsertCommodity(String title,String price,String location,String image){
        SQLiteDatabase db = commoditySqlOP.getWritableDatabase();
        db.delete("cart","title = ? ",new String[]{title});
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("price",price);
        values.put("image",image);
        values.put("location",location);
        long result = db.insert("commodity",null,values);
        db.close();
        return result;
    }

    public long deleteCommodity(String title,String price,String location,String image){
        SQLiteDatabase db = commoditySqlOP.getWritableDatabase();
        int result = db.delete("commodity", "title = ?", new String[]{title});
        db.close();
        return result;
    }
}
