package com.example.pineapple.ticketutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class QueryCommodityDB {
    public static List<Commodity> queryCommodityDB(Context context){
    List<Commodity> commodityList = new ArrayList<>();
    MySqliteHelper helper = new MySqliteHelper(context);
    SQLiteDatabase db = helper.getReadableDatabase();
    Cursor cursor = db.query("commodity", new String[]{"title", "price","location","image", "phone","address","goodsJson"}, null, null, null, null, null);
    if (cursor != null) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                Commodity commodity = new Commodity();
                String title = cursor.getString(0);
                String price = cursor.getString(1);
                String location = cursor.getString(2);
                String image = cursor.getString(3);
                String phone = cursor.getString(4);
                String address = cursor.getString(5);
                String goodsJson = cursor.getString(6);

                commodity.setTitle(title);
                commodity.setPrice(price);
                commodity.setLocation(location);
                commodity.setImage(image);
                commodity.setPhone(phone);
                commodity.setAddress(address);
                commodity.setGoodsJson(goodsJson);
                commodity.setGoodsList(new Gson().fromJson(goodsJson, new TypeToken<List<Commodity>>(){}.getType()));
                commodityList.add(commodity);
            }
        }
        cursor.close();
    }
    db.close();
    return commodityList;
}
}
