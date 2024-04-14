package com.example.pineapple.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pineapple.R;
import com.example.pineapple.fragments.EssaysFragment;
import com.example.pineapple.fragments.MarketFragment;
import com.example.pineapple.fragments.MeFragment;
import com.example.pineapple.fragments.MessageFragment;

public class MyHomeActivity extends AppCompatActivity {

    private final int FRAGMENT_ESSAYS = 1;
    private final int FRAGMENT_MARKET = 2;
    private final int FRAGMENT_MESSAGE = 3;
    private final int FRAGMENT_ME = 4;

    private Fragment EssaysFragment;
    private Fragment MarketFragment;
    private Fragment MessageFragment;
    private Fragment MeFragment;
    private Button bt_travel;
    private Button bt_market;
    private Button bt_message;
    private Button bt_upload;
    private Button bt_me;
    private TextView tv_travel;
    private TextView tv_market;
    private TextView tv_message;
    private TextView tv_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home);

        initView();
        initData();
        click();

    }

    public void initView(){
        bt_travel = (Button) findViewById(R.id.bt_travel);
        bt_market = (Button) findViewById(R.id.bt_market);
        bt_upload = (Button) findViewById(R.id.bt_upload);
        bt_message = (Button) findViewById(R.id.bt_message);
        bt_me = (Button) findViewById(R.id.bt_me);
        tv_travel = (TextView) findViewById(R.id.tv_travel);
        tv_market = (TextView) findViewById(R.id.tv_market);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_me = (TextView) findViewById(R.id.tv_me);
    }

    public void initBTN(){
        bt_travel.setClickable(true);
        bt_market.setClickable(true);
        bt_message.setClickable(true);
        bt_me.setClickable(true);
    }
    private void initData() {
        switchFragment(FRAGMENT_ESSAYS);
        changeColor(FRAGMENT_ESSAYS);
        bt_travel.setClickable(false);
    }

    public void switchFragment(int toFragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = EssaysFragment;
        switch(toFragment){
            case FRAGMENT_ESSAYS:
                if(EssaysFragment == null)
                    EssaysFragment = new EssaysFragment();
                fragment = EssaysFragment;
                break;

            case FRAGMENT_MARKET:
                if(MarketFragment == null)
                    MarketFragment = new MarketFragment();
                fragment = MarketFragment;
                break;

            case FRAGMENT_MESSAGE:
                if(MessageFragment == null)
                    MessageFragment = new MessageFragment();
                fragment = MessageFragment;
                break;

            case FRAGMENT_ME:
                if(MeFragment == null)
                    MeFragment = new MeFragment();
                fragment = MeFragment;
                break;
        }
        transaction.replace(R.id.ll_content,fragment);
        transaction.commit();
    }

    public void click() {

        initBTN();

        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyHomeActivity.this, WriterActivity.class);
                startActivity(intent);
            }
        });



        bt_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_travel.setClickable(false);
                switchFragment(FRAGMENT_ESSAYS);
                changeColor(FRAGMENT_ESSAYS);
                bt_market.setClickable(true);
                bt_message.setClickable(true);
                bt_me.setClickable(true);
            }
        });

        bt_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_market.setClickable(false);
                switchFragment(FRAGMENT_MARKET);
                changeColor(FRAGMENT_MARKET);
                bt_travel.setClickable(true);
                bt_message.setClickable(true);
                bt_me.setClickable(true);
            }
        });

        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_message.setClickable(false);
                switchFragment(FRAGMENT_MESSAGE);
                changeColor(FRAGMENT_MESSAGE);
                bt_travel.setClickable(true);
                bt_market.setClickable(true);
                bt_me.setClickable(true);
            }
        });

        bt_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_me.setClickable(false);
                switchFragment(FRAGMENT_ME);
                changeColor(FRAGMENT_ME);
                bt_travel.setClickable(true);
                bt_market.setClickable(true);
                bt_message.setClickable(true);
            }
        });
    }

    public void changeColor(int toFragment){
        tv_travel.setTextColor(Color.BLACK);
        tv_market.setTextColor(Color.BLACK);
        tv_message.setTextColor(Color.BLACK);
        tv_me.setTextColor(Color.BLACK);
        bt_travel.setBackgroundResource(R.drawable.my_travel);
        bt_market.setBackgroundResource(R.drawable.market);
        bt_message.setBackgroundResource(R.drawable.message);
        bt_me.setBackgroundResource(R.drawable.me);
        switch (toFragment){
            case FRAGMENT_ESSAYS:
                tv_travel.setTextColor(Color.parseColor("#FCD240"));
                bt_travel.setBackgroundResource(R.drawable.travel_log);
                break;

            case FRAGMENT_MARKET:
                tv_market.setTextColor(Color.parseColor("#FCD240"));
                bt_market.setBackgroundResource(R.drawable.market_log);
                break;

            case FRAGMENT_MESSAGE:
                tv_message.setTextColor(Color.parseColor("#FCD240"));
                bt_message.setBackgroundResource(R.drawable.message_log);
                break;

            case FRAGMENT_ME:
                tv_me.setTextColor(Color.parseColor("#FCD240"));
                bt_me.setBackgroundResource(R.drawable.me_log);
                break;
        }




    }

}