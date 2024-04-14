package com.example.pineapple.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.fragments.CollectionsFragment;
import com.example.pineapple.fragments.DraftsFragment;
import com.example.pineapple.fragments.EssaysFragment;
import com.example.pineapple.fragments.MarketFragment;
import com.example.pineapple.fragments.MeFragment;
import com.example.pineapple.fragments.MessageFragment;

public class MyEssaysActivity extends AppCompatActivity {
    private final int FRAGMENT_COLLECTIONS = 1;
    private final int FRAGMENT_DRAFTS = 2;
    private Fragment CollectionsFragment;
    private Fragment DraftsFragment;
    private Button bt_my_collection;
    private Button bt_me_draft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_essays);
        initView();
        initData();
        click();
    }

    public void initView(){
        bt_my_collection = (Button) findViewById(R.id.bt_my_collection);
        bt_me_draft = (Button) findViewById(R.id.bt_me_draft);
    }
    public void initBTN(){
        bt_my_collection.setClickable(true);
        bt_me_draft.setClickable(true);
    }

    private void initData() {
        switchFragment(FRAGMENT_COLLECTIONS);
        changeColor(FRAGMENT_COLLECTIONS);
        bt_my_collection.setClickable(false);
    }

    public void switchFragment(int toFragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = CollectionsFragment;
        switch(toFragment){
            case FRAGMENT_COLLECTIONS:
                if(CollectionsFragment == null)
                    CollectionsFragment = new CollectionsFragment();
                fragment = CollectionsFragment;
                break;

            case FRAGMENT_DRAFTS:
                if(DraftsFragment == null)
                    DraftsFragment = new DraftsFragment();
                fragment = DraftsFragment;
                break;

        }
        transaction.replace(R.id.ll_my_essays,fragment);
        transaction.commit();
    }

    public void click() {

        initBTN();

        bt_my_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_my_collection.setClickable(false);
                switchFragment(FRAGMENT_COLLECTIONS);
                changeColor(FRAGMENT_COLLECTIONS);
                bt_me_draft.setClickable(true);
            }
        });

        bt_me_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_me_draft.setClickable(false);
                switchFragment(FRAGMENT_DRAFTS);
                changeColor(FRAGMENT_DRAFTS);
                bt_my_collection.setClickable(true);
            }
        });
    }

    public void changeColor(int toFragment){
        bt_my_collection.setTextColor(Color.BLACK);
        bt_me_draft.setTextColor(Color.BLACK);
        bt_my_collection.setBackgroundResource(R.drawable.gradual_color_bg);
        bt_me_draft.setBackgroundResource(R.drawable.gradual_color_bg);
        switch (toFragment){
            case FRAGMENT_COLLECTIONS:
                bt_my_collection.setTextColor(Color.parseColor("#FFFFFF"));
                break;

            case FRAGMENT_DRAFTS:
                bt_me_draft.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
}