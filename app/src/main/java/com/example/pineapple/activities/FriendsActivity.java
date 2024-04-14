package com.example.pineapple.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pineapple.R;
import com.example.pineapple.adapters.FriendsAdapter;
import com.example.pineapple.beans.MyFriends;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FriendsActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private String account;
    private ListView lv_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);
        account = sp.getString("account", "");
        inItView();
        showFriendsList();
    }

    public void inItView() {
        lv_friends = (ListView) findViewById(R.id.lv_friends);
    }
    public void showFriendsList(){
        BmobQuery<MyFriends> query = new BmobQuery<>();
        query.addWhereEqualTo("account", account);
        // 发起查询请求
        query.findObjects(new FindListener<MyFriends>() {
            @Override
            public void done(List<MyFriends> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    Collections.reverse(list);
                    FriendsAdapter friendsAdapter = new FriendsAdapter(FriendsActivity.this,list);
                    lv_friends.setAdapter(friendsAdapter);
                }
            }
        });
    }
}