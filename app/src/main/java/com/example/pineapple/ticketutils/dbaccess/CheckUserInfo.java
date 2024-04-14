package com.example.pineapple.ticketutils.dbaccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class CheckUserInfo {
    public  boolean checkUserInfo(Context context, String username, String pw){
        MySqliteHelper helper = new MySqliteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userinfo", new String[]{"account", "pw"}, null, null, null, null, null);
        if(cursor != null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                String db_account = cursor.getString(0);
                String db_pw = cursor.getString(1);
                if (db_account.equals(username)){
                    if (db_pw.equals(pw)) {

                        return true;
                    }
                    else{
                        Toast.makeText(context, "Wrong password pls checked again", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            if (!cursor.moveToNext()){
                Toast.makeText(context, "Not correct account", Toast.LENGTH_SHORT).show();
            }
        }


        cursor.close();
        db.close();
        return false;
    }

}
