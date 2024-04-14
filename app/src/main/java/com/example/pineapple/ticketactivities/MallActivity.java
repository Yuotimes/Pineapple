package com.example.pineapple.ticketactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.ticketbeans.MallInfo;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.BmobManager;
import com.example.pineapple.ticketmanagers.SqlManager;

import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MallActivity extends AppCompatActivity {


    private String title;
    private String description;
    private String price;
    private String location;
    private String image;
    private TextView mall_price;
    private TextView mall_title;
    private TextView mall_location;
    private TextView mall_description;
    private ImageView iv_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mall);
        initView();

    }
    public void initView(){
        mall_title = (TextView) findViewById(R.id.mall_title);
        mall_price = (TextView) findViewById(R.id.mall_price);
        mall_location = (TextView) findViewById(R.id.mall_location);
        mall_description = (TextView) findViewById(R.id.mall_description);
        iv_image = (ImageView) findViewById(R.id.iv_image);


        Intent intent = getIntent();
        MallInfo mallInfo = (MallInfo) intent.getSerializableExtra("list");
        title = mallInfo.getTitle();
        price = mallInfo.getPrice();
        location = mallInfo.getLocation();
        description = mallInfo.getDescription();
        image = mallInfo.getImage();
        Glide.with(this).load(mallInfo.getImage()).into(iv_image);
        mall_title.setText(title);
        mall_price.setText(price);
        mall_location.setText(location);
        mall_description.setText(description);

    }

    public void purchase(View view) {
        SqlManager manager = new SqlManager();
        //获取用户积分余额
        UserInfo info = manager.getLoginUser(this);
        if (info != null) {
            double point = Double.parseDouble(info.getPoint());
            if (point < Double.parseDouble(price)) {
                Toast.makeText(this, "积分余额不足，兑换失败",
                Toast.LENGTH_SHORT).show();
                return;
            }
            BmobManager bmobManager = new BmobManager();
            bmobManager.saveMall2Bomb(this,title,image,price,location,description);

            long l = manager.saveMall2DB(this,title,price,location,image);
            if (l == -1) {
                Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "兑换成功", Toast.LENGTH_SHORT).show();
                //更新用户积分余额
                point -= Double.parseDouble(price);
                info.setPoint(String.format(Locale.getDefault(), "%.2f", point));
                manager.updateUserInfo(this, info);

                info.setPoint(info.getPoint());
                SharedPreferences sp = getSharedPreferences("UP", 0);
                String uid = sp.getString("uid", "");
                info.update(uid, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.e("更新积分:","===更新成功===");
                        }else{
                            Log.e("更新积分:","更新失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }

                });
            }
            finish();
        }
    }
}
