package com.example.pineapple.beans;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlite extends SQLiteOpenHelper {

    private final String CREATE_COMMUNITY_ESSAY_STATEMENT = "create table drafts(_id INTEGER PRIMARY KEY AUTOINCREMENT,account varchar(20),title varchar(20),essay varchar(10000),photo1 varchar(100000),photo2 varchar(100000),photo3 varchar(100000),username varchar(30),userhead varchar(100000))";

    public MySqlite(@Nullable Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COMMUNITY_ESSAY_STATEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
