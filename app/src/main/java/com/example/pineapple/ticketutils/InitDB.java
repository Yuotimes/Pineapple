package com.example.pineapple.ticketutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;

public class InitDB {
    public int initData(Context context){
        MySqliteHelper helper = new MySqliteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userinfo",  new String[]{"username","account", "pw"}, null, null, null, null, null);
        int i = -1;
        if(cursor != null && cursor.getCount()>0){
            while (cursor.moveToNext())
            {
                i++;
            }
        }

        cursor.close();
        db.close();
        return i;

    }

}
