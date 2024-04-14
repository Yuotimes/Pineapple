package com.example.pineapple.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CreateDraftsDBOP {



    private final MySqlite draftsDBOP;

    public CreateDraftsDBOP(Context context){
        draftsDBOP = new MySqlite(context);
    }

    public long insertDraft (String account,String title,String essay, String photo1,String photo2,String photo3,String username,String userhead){

        SQLiteDatabase db = draftsDBOP.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("account",account);
        values.put("title",title);
        values.put("essay",essay);
        values.put("photo1",photo1);
        values.put("photo2",photo2);
        values.put("photo3",photo3);
        values.put("username",username);
        values.put("userhead",userhead);
        long result =  db.insert("drafts", null, values);
        db.close();

        return result;

    }




}
