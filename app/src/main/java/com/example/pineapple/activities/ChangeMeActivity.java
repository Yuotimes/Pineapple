package com.example.pineapple.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.ticketbeans.UserInfo;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeMeActivity extends AppCompatActivity {
    private TextView ch_account;
    private EditText ch_old_password;
    private EditText ch_password;
    private EditText ch_sure_password;
    private Button ch_btn_sure;
    private SharedPreferences sp;
    private String account;
    private String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_me);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        initView();
        change();
    }

    private void initView(){
        ch_account = (TextView) findViewById(R.id.ch_account);
        ch_old_password = (EditText) findViewById(R.id.ch_old_password);
        ch_password = (EditText) findViewById(R.id.ch_password);
        ch_sure_password = (EditText) findViewById(R.id.ch_sure_password);
        ch_btn_sure = (Button) findViewById(R.id.ch_btn_sure);
        ch_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ch_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ch_sure_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        account = sp.getString("account", "");
        pw = sp.getString("pw", "");

        BmobQuery<MyUsers> query = new BmobQuery<>();
        // 设置查询条件：account 列数据为 account
        query.addWhereEqualTo("account", account);
        // 发起查询请求
        query.findObjects(new FindListener<MyUsers>() {
            @Override
            public void done(List<MyUsers> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    int i = list.size();
                    if(i==1){
                        MyUsers user = list.get(0);
                        String nickname = user.getNickname();
                        ch_account.setText(nickname);
                    }
                } else {
                    // 查询失败，处理异常
                    Toast.makeText(ChangeMeActivity.this, "查询失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void change(){
        ch_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old_password = ch_old_password.getText().toString().trim();
                String nwe_password = ch_password.getText().toString().trim();
                String sure_new_password = ch_sure_password.getText().toString().trim();
                if (old_password.isEmpty() || nwe_password.isEmpty() || sure_new_password.isEmpty()){
                    Toast.makeText(ChangeMeActivity.this, "请输入你的密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!(old_password.equals(pw))){
                    Toast.makeText(ChangeMeActivity.this, "旧密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }else if(nwe_password.equals(pw)){
                    Toast.makeText(ChangeMeActivity.this,"请输入一个不同的新密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!(nwe_password.equals(sure_new_password))){
                    Toast.makeText(ChangeMeActivity.this,"两次新密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("pw",nwe_password);
                    edit.commit();
                    BmobQuery<MyUsers> query = new BmobQuery<>();
                    // 设置查询条件：account 列数据为 account
                    query.addWhereEqualTo("account", account);
                    // 发起查询请求
                    query.findObjects(new FindListener<MyUsers>() {
                        @Override
                        public void done(List<MyUsers> list, BmobException e) {
                            if (e == null) {
                                // 查询成功，处理查询结果
                                for (MyUsers users : list) {
                                    users.setPassword(nwe_password);
                                    users.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                             if (e==null){
                                                 Toast.makeText(ChangeMeActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                                                 finish();
                                             } else {
                                                 Toast.makeText(ChangeMeActivity.this, "更改失败，请重试", Toast.LENGTH_LONG).show();
                                             }
                                        }
                                    });
                                }
                            } else {
                                // 查询失败，处理异常
                                Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    BmobQuery<UserInfo> query2 = new BmobQuery<>();
                    // 设置查询条件：account 列数据为 account
                    query2.addWhereEqualTo("account", account);
                    // 发起查询请求
                    query2.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> list, BmobException e) {
                            if (e == null) {
                                // 查询成功，处理查询结果
                                for (UserInfo userInfo : list) {
                                    userInfo.setPwd(nwe_password);
                                    userInfo.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){

                                            } else {
                                            }
                                        }
                                    });
                                }
                            } else {
                                // 查询失败，处理异常
                                Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


}