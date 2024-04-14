package com.example.pineapple.ticketutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;

public class ChangeUserInfo {
    public void changeUserPW(Context context, String account, String pw){
        MySqliteHelper helper = new MySqliteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("pw",pw);
        db.update("userinfo",values,"account = ?",new String[]{account});
    }
}
