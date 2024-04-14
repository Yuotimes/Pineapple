package com.example.pineapple.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TitleOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.pineapple.R;

public class MapActivity_5 extends Activity {
    private MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView_1);
        BaiduMap mBaiduMap = mMapView.getMap();
        //定义标记坐标点
        //第一个参数为纬度，第二个参数为经度
        LatLng point = new LatLng(39.33708, 121.90742);
        // 创建一个 BitmapDescriptor，指定要显示的图标资源
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.locate);
        // 对图标进行缩放
        float scale = 0.1f; // 缩放比例，此为示例，你可以根据需要调整
        int width = icon.getBitmap().getWidth();
        int height = icon.getBitmap().getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(icon.getBitmap(), 0, 0, width, height, matrix, true);
        // 创建一个新的 BitmapDescriptor图标，并使用缩放后的图标在地图上显示
        BitmapDescriptor scaledIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
        TitleOptions titleOptions = new TitleOptions().text("大连东泉温泉").titleFontSize(30).titleFontColor(0xFF000000).titleOffset(0,150);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(scaledIcon).titleOptions(titleOptions);
        // 设置地图中心为标记点位置
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(update);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        // 设置初始的地图缩放级别
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18); // 设置缩放级别，值越大地图显示越精细
        MapStatus mapStatus = builder.build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}