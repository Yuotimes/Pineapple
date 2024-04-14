package com.example.pineapple.ticketactivities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pineapple.R;
import com.example.pineapple.TicketsActivity;
import com.example.pineapple.ticketfragment.DisplayFragment;
import com.example.pineapple.ticketfragment.MallFragment;

public class CreditActivity extends TicketsActivity {
    private Button btn_mall;
    private Button btn_display;
    private final int FRAGMENT_MALL = 1;
    private final int FRAGMENT_DISPLAY =2;

    private Fragment MallFragment;
    private Fragment DisplayFragment;
    private TextView tv_mall;
    private TextView tv_display;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        initView();
        initData();
        btnclick();
    }
    private void initView(){
        btn_mall = (Button) findViewById(R.id.btn_mall);
        btn_display = (Button) findViewById(R.id.btn_display);
        tv_mall = (TextView) findViewById(R.id.tv_mall);
        tv_display = (TextView) findViewById(R.id.tv_display);
        //让第一个tab选中
//        btn_mall.performClick();
    }
    public void initBTN(){
        btn_mall.setClickable(true);
        btn_display.setClickable(true);
    }
    private void initData() {
        switchFragment(FRAGMENT_MALL);
        changeColor(FRAGMENT_MALL);
        btn_mall.setClickable(false);
    }
    public void btnclick() {


        initBTN();

        btn_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_mall.setClickable(false);
                switchFragment(FRAGMENT_MALL);
                changeColor(FRAGMENT_MALL);
                btn_display.setClickable(true);
            }
        });
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_display.setClickable(false);
                switchFragment(FRAGMENT_DISPLAY);
                changeColor(FRAGMENT_DISPLAY);
                btn_mall.setClickable(true);
            }
        });
//        switch (view.getId()){
//            case R.id.btn_mall:
//                switchFragment(FRAGMENT_MALL);
//                changeColor(FRAGMENT_MALL);
//                break;
//            case R.id.btn_display:
//                switchFragment(FRAGMENT_DISPLAY);
//                changeColor(FRAGMENT_DISPLAY);
//                break;
//        }
    }
    private void switchFragment(int toFragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = MallFragment;
        switch(toFragment){
            case FRAGMENT_MALL:
                if(MallFragment == null)
                    MallFragment = new MallFragment();
                fragment = MallFragment;
                break;

            case FRAGMENT_DISPLAY:
                if(DisplayFragment == null)
                    DisplayFragment = new DisplayFragment();
                fragment = DisplayFragment;
                break;
        }
        transaction.replace(R.id.ll_content,fragment);
        transaction.commitAllowingStateLoss();
    }
    private void changeColor(int toFragment){
        tv_mall.setTextColor(Color.BLACK);
        tv_display.setTextColor(Color.BLACK);

        btn_mall.setBackgroundResource(R.drawable.home);
        btn_display.setBackgroundResource(R.drawable.trip);

        switch(toFragment){
            case FRAGMENT_MALL:
                tv_mall.setTextColor(Color.parseColor("#FCD240"));
                btn_mall.setBackgroundResource(R.drawable.home_log);
                break;

            case FRAGMENT_DISPLAY:
                tv_display.setTextColor(Color.parseColor("#FCD240"));
                btn_display.setBackgroundResource(R.drawable.trip_log);
                break;
        }
    }

}
