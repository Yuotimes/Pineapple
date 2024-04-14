package com.example.pineapple.ticketutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.ticketbeans.MallInfo;
import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class QueryMallDB {
    @SuppressLint("Range")
    public static List<MallInfo> queryMallDB(Context context) {
        List<MallInfo> mallInfoList = new ArrayList<>();
        MySqliteHelper helper = new MySqliteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        SharedPreferences sp=context.getSharedPreferences("UP",0);
        String accountS = sp.getString("account", "");
        Cursor cursor = db.rawQuery("select * from mall where account=?", new String[]{accountS});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    MallInfo mallInfo = new MallInfo();
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String location = cursor.getString(cursor.getColumnIndex("location"));
                    String price = cursor.getString(cursor.getColumnIndex("price"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String account = cursor.getString(cursor.getColumnIndex("account"));

                    mallInfo.setId(id);
                    mallInfo.setTitle(title);
                    mallInfo.setImage(image);
                    mallInfo.setPrice(price);
                    mallInfo.setLocation(location);
                    mallInfo.setAccount(account);
                    mallInfo.setDescription(description);
                    mallInfoList.add(mallInfo);

                }
            }
            cursor.close();
        }
        db.close();
        return mallInfoList;
    }
}

