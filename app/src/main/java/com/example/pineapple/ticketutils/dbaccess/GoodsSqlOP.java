package com.example.pineapple.ticketutils.dbaccess;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.shoppingbeans.Commodity;

import java.util.ArrayList;
import java.util.List;


public class GoodsSqlOP {
    private final MySqliteHelper goodsSqlOP;

    public GoodsSqlOP(Context context) {
        goodsSqlOP = new MySqliteHelper(context);
    }

    public long insertGoods(Commodity info) {
        SQLiteDatabase db = goodsSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", info.getTitle());
        values.put("price", info.getPrice());
        values.put("image",info.getImage());
        values.put("location",info.getLocation());
        values.put("description",info.getDescription());
        values.put("number",info.getNumber());
        long result = db.insert("goods", null, values);
        db.close();
        return result;
    }

    public long updateGoods(Commodity info) {
        SQLiteDatabase db = goodsSqlOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", info.getTitle());
        values.put("price", info.getPrice());
        values.put("image",info.getImage());
        values.put("location",info.getLocation());
        values.put("description",info.getDescription());
        values.put("number",info.getNumber());
        long result = db.update("goods",  values,"_id=?", new String[]{String.valueOf(info.getId())});
        db.close();
        return result;
    }

    //查询所有商品
    @SuppressLint("Range")
    public List<Commodity> queryGoods() {
        //实例化数据库对象
        SQLiteDatabase db = goodsSqlOP.getWritableDatabase();

        List<Commodity> commodityList = new ArrayList<>();
        //将封装好的一行数据保存到数据库的CommodityInfo_TABLE表中
        Cursor cursor =db.rawQuery("select * from goods order by _id desc ", null);
        while (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String price=cursor.getString(cursor.getColumnIndex("price"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String location=cursor.getString(cursor.getColumnIndex("location"));
            String description=cursor.getString(cursor.getColumnIndex("description"));
            int number=cursor.getInt(cursor.getColumnIndex("number"));

            Commodity commodity = new Commodity();
            commodity.setId(id);
            commodity.setTitle(title);
            commodity.setPrice(price);
            commodity.setImage(image);
            commodity.setLocation(location);
            commodity.setDescription(description);
            commodity.setNumber(number);

            commodityList.add(commodity);
        }
        db.close();

        return commodityList;
    }

    //查询所有商品
    @SuppressLint("Range")
    public Commodity queryGoodsByTitle(String titleS) {
        //实例化数据库对象
        SQLiteDatabase db = goodsSqlOP.getWritableDatabase();

        Commodity commodity = null;
        //将封装好的一行数据保存到数据库的CommodityInfo_TABLE表中
        Cursor cursor =db.rawQuery("select * from goods where title=? ", new String[]{titleS});
        while (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String price=cursor.getString(cursor.getColumnIndex("price"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String location=cursor.getString(cursor.getColumnIndex("location"));
            String description=cursor.getString(cursor.getColumnIndex("description"));
            int number=cursor.getInt(cursor.getColumnIndex("number"));

            commodity = new Commodity();
            commodity.setId(id);
            commodity.setTitle(title);
            commodity.setPrice(price);
            commodity.setImage(image);
            commodity.setLocation(location);
            commodity.setDescription(description);
            commodity.setNumber(number);
        }
        db.close();

        return commodity;
    }

    public long deleteGoods(int id){
        SQLiteDatabase db = goodsSqlOP.getWritableDatabase();
        int result = db.delete("goods", "_id=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
