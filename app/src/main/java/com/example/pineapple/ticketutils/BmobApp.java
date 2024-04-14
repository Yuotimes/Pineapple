package com.example.pineapple.ticketutils;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class BmobApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "deffb45f8b34539d90e3afc57a38013c");
    }
}