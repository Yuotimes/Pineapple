package com.example.pineapple.beans;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

public class BmobApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "deffb45f8b34539d90e3afc57a38013c");
        //同意隐私协议
        SDKInitializer.setAgreePrivacy(this, true);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

}
