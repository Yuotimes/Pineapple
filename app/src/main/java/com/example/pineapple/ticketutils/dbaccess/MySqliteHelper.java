package com.example.pineapple.ticketutils.dbaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pineapple.ticketproperties.AppProperties;

public class MySqliteHelper extends SQLiteOpenHelper {

    private final String CREATE_FLIGHT_TABLE_STATEMENT = "create table flight (_id INTEGER PRIMARY KEY AUTOINCREMENT,account varchar(20),title varchar(100),time varchar(100),image varchar(100),number varchar(100),price varchar(20))";
    private final String CREATE_CREDIT_TABLE_STATEMENT = "create table credit (_id INTEGER PRIMARY KEY AUTOINCREMENT,account varchar(20),total_price varchar (100),title varchar(100),time varchar(100),image varchar(100),number varchar(100),price varchar(20))";
    private final String CREATE_MALL_TABLE_STATEMENT = "create table mall (_id INTEGER PRIMARY KEY AUTOINCREMENT,account varchar(20), title varchar(100), image varchar(300), location varchar(100),price varchar(100),description text)";
    private final String CREATE_USER_TABLE_STATEMENT = "create table userinfo (_id INTEGER PRIMARY KEY AUTOINCREMENT,username varchar (20),account varchar(20),pw varchar(20), point varchar(20))";
    private final String CREATE_COMMODITY_TABLE_STATEMENT = "create table commodity (_id INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(100), image varchar(300), location varchar(100),price varchar(100),description text," +
            "goodsJson text, phone text, address text)";
    private final String CREATE_CART_TABLE_STATEMENT = "create table cart (_id INTEGER PRIMARY KEY AUTOINCREMENT,title varchar(100), image varchar(300), location varchar(100),price varchar(100),description text)";

    private final String CREATE_GOODS_TABLE_STATEMENT = "create table goods (_id INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(100), image varchar(300), location varchar(100),price varchar(100),description text," +
            "number INTEGER)";

    public MySqliteHelper(@Nullable Context context){
        super(context, AppProperties.FLIGHT_DB_NAME,null,9);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FLIGHT_TABLE_STATEMENT);
        db.execSQL(CREATE_CREDIT_TABLE_STATEMENT);
        db.execSQL(CREATE_MALL_TABLE_STATEMENT);
        db.execSQL(CREATE_USER_TABLE_STATEMENT);
        db.execSQL(CREATE_CART_TABLE_STATEMENT);//创建购物车表
        db.execSQL(CREATE_GOODS_TABLE_STATEMENT); //创建商品表
        db.execSQL(CREATE_COMMODITY_TABLE_STATEMENT);//创建订单表

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
