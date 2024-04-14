package com.example.pineapple.ticketactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.TrainInfo;
import com.example.pineapple.ticketmanagers.BmobManager;

public class TrainActivity extends AppCompatActivity {
    private TextView tv_traintitle;

    private String title;
    private String time;
    private String image;
    private String number;
    private String price;
    private TextView tv_traintime;
    private TextView tv_trainnumber;
    private ImageView iv_train;
    private TextView tv_trainprice;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        initView();

    }
    public void initView(){

        tv_traintitle = (TextView) findViewById(R.id.tv_traintitle);
        tv_traintime = (TextView) findViewById(R.id.tv_traintime);
        iv_train = (ImageView) findViewById(R.id.iv_train);
        tv_trainnumber = (TextView) findViewById(R.id.tv_trainnumber);
        tv_trainprice = (TextView) findViewById(R.id.tv_trainprice);
        Intent intent = getIntent();
        TrainInfo trainInfo = (TrainInfo) intent.getSerializableExtra("trainlist");
        title = trainInfo.getTitle();
        time = trainInfo.getTime();
        image = trainInfo.getImage();
        System.out.println("-------"+image);
        number = trainInfo.getNumber();
        price = trainInfo.getPrice();
        Log.d("TrainActivity", "Image URL: " + trainInfo.getImage());
        Glide.with(this).load(trainInfo.getImage()).into(iv_train);
        System.out.println(iv_train);

        tv_traintitle.setText(title);
        tv_trainnumber.setText(number);
        tv_trainprice.setText(price);
        tv_traintime.setText(time);


    }
    public void booking(View view){
        BmobManager bmobManager = new BmobManager();
        bmobManager.saveTrain2Bmob(this,title,time,image,number,price);
//        SqlManager manager = new SqlManager();
//        long l = manager.saveFlight2DB(this, title, time, image, number, price);
        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
//        if (l == -1){
//            Toast.makeText(this, "购买失败", Toast.LENGTH_SHORT).show();
//        }
        finish();

    }
}
