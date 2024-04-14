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
import com.example.pineapple.ticketbeans.PlaneInfo;
import com.example.pineapple.ticketmanagers.BmobManager;

public class PlaneActivity extends AppCompatActivity {

    private TextView tv_planetitle;
    private ImageView iv_flight;
    private TextView tv_flightnumber;
    private TextView tv_flightprice;
    private TextView tv_flighttime;

    private String title;
    private String time;
    private String image;
    private String number;
    private String price;
    private TextView tv_planetime;
    private TextView tv_planenumber;
    private ImageView iv_plane;
    private TextView tv_planeprice;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);
        initView();

    }
    public void initView(){

        tv_planetitle = (TextView) findViewById(R.id.tv_planetitle);
        tv_planetime = (TextView) findViewById(R.id.tv_planetime);
        iv_plane = (ImageView) findViewById(R.id.iv_plane);
        tv_planenumber = (TextView) findViewById(R.id.tv_planenumber);
        tv_planeprice = (TextView) findViewById(R.id.tv_planeprice);
        Intent intent = getIntent();
        PlaneInfo planeInfo = (PlaneInfo) intent.getSerializableExtra("planelist");
        title = planeInfo.getTitle();
        time = planeInfo.getTime();
        image = planeInfo.getImage();
        number = planeInfo.getNumber();
        price = planeInfo.getPrice();
        Glide.with(this).load(planeInfo.getImage()).into(iv_plane);
        tv_planetitle.setText(title);
        tv_planenumber.setText(number);
        tv_planeprice.setText("￥  "+price);
        tv_planetime.setText(time);


    }
    public void booking(View view){
        BmobManager bmobManager = new BmobManager();
        bmobManager.savePlane2Bmob(this,title,time,image,number,price);
//        SqlManager manager = new SqlManager();
//        long l = manager.saveFlight2DB(this, title, time, image, number, price);
        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
//        if (l == -1){
//            Toast.makeText(this, "购买失败", Toast.LENGTH_SHORT).show();
//        }
        finish();

    }

}
