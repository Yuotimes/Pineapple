package com.example.pineapple.ticketactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.BusInfo;
import com.example.pineapple.ticketmanagers.BmobManager;

public class BusActivity extends AppCompatActivity {

    private TextView tv_bustitle;

    private String title;
    private String time;
    private String image;
    private String number;
    private String price;
    private TextView tv_bustime;
    private TextView tv_busnumber;
    private ImageView iv_bus;
    private TextView tv_busprice;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        initView();

    }
    public void initView(){

        tv_bustitle = (TextView) findViewById(R.id.tv_bustitle);
        tv_bustime = (TextView) findViewById(R.id.tv_bustime);
        iv_bus = (ImageView) findViewById(R.id.iv_bus);
        tv_busnumber = (TextView) findViewById(R.id.tv_busnumber);
        tv_busprice = (TextView) findViewById(R.id.tv_busprice);
        Intent intent = getIntent();
        BusInfo busInfo = (BusInfo) intent.getSerializableExtra("buslist");
        title = busInfo.getTitle();
        time = busInfo.getTime();
        image = busInfo.getImage();
        number = busInfo.getNumber();
        price = busInfo.getPrice();
        Glide.with(this).load(busInfo.getImage()).into(iv_bus);
        tv_bustitle.setText(title);
        tv_busnumber.setText(number);
        tv_busprice.setText(price);
        tv_bustime.setText(time);


    }
    public void booking(View view){
        BmobManager bmobManager = new BmobManager();
        bmobManager.saveBus2Bmob(this,title,time,image,number,price);
//        SqlManager manager = new SqlManager();
//        long l = manager.saveFlight2DB(this, title, time, image, number, price);
        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
//        if (l == -1){
//            Toast.makeText(this, "购买失败", Toast.LENGTH_SHORT).show();
//        }
        finish();

    }

}




