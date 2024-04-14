package com.example.pineapple.beans;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DeleteDraftsDB {
    public void deleteDraftsFormDB(Context context,String account){
        MySqlite helper = new MySqlite(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete("drafts","account =  ?",new String[]{account});
        db.close();
    }
}
