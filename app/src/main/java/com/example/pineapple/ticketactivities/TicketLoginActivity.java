package com.example.pineapple.ticketactivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.TicketsActivity;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.SqlManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TicketLoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private EditText et_account;
    private EditText et_pw;
    public CheckBox cb_rm;
    private Button bt_signIn;
    private SharedPreferences up;
    private Button bt_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        up = getSharedPreferences("UP", Context.MODE_PRIVATE);

        initView();
        initData();

    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_pw = (EditText) findViewById(R.id.et_pw);
        cb_rm = (CheckBox) findViewById(R.id.cb_rm);
        bt_signIn = (Button) findViewById(R.id.bt_signIn);
        bt_account = (Button) findViewById(R.id.bt_account);
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketLoginActivity.this, TicketCreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData() {
        SqlManager manager = new SqlManager();
        int i = manager.initDB(this);
        if (i == -1) {
            manager.saveUserInfo2DB(this, "username", "account", "password");
        }
    }

    public void sign(View view) {
        String account = et_account.getText().toString().trim();
        String pw = et_pw.getText().toString().trim();
        et_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (account.isEmpty() || pw.isEmpty()) {
            Toast.makeText(this, "Please enter the correct user information", Toast.LENGTH_SHORT).show();
            return;
        }

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("account", account);
        query.addWhereEqualTo("pwd", pw);
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    if (object == null || object.isEmpty()) {
                        Toast.makeText(TicketLoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UserInfo userInfo = object.get(0);
                    SqlManager manager = new SqlManager();
                    //与云端做用户数据同步，加入本地数据库被清了，还能同步到云端的数据
                    if (manager.checkUserInfoInDB(TicketLoginActivity.this, account)) {
                        manager.updateUserInfo(TicketLoginActivity.this, userInfo);
                    } else {
                        manager.insertUserInfo(TicketLoginActivity.this, userInfo);
                    }
//                    boolean b = new SqlManager().checkUserInfoInDB(LoginActivity.this, account, pw);
//                    if (b) {
                        boolean isChecked = cb_rm.isChecked();
                        if (isChecked) {
//                            saveUserInfo(account, pw, userInfo.getObjectId(), isChecked);
                        } else {
//                            saveUserInfo(null, null, userInfo.getObjectId(), isChecked);
                        }

                        Intent intent = new Intent(TicketLoginActivity.this, TicketsActivity.class);

                        startActivity(intent);
                        finish();
//                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

//    private void saveUserInfo(String account, String pw, String uid, boolean isChecked) {
//        SharedPreferences.Editor edit = sp.edit();
//        SharedPreferences.Editor edit2 = up.edit();
//        edit.putString("account", account);
//        edit.putString("pw", pw);
//        edit.putString("uid", uid);
//        edit.putBoolean("isChecked", isChecked);
//        edit2.putString("account", account);
//        edit2.putString("pw", pw);
//        edit2.putString("uid", uid);
//        edit2.putBoolean("isChecked", isChecked);
//        edit.commit();
//        edit2.commit();
//    }

//    private void showUserInfo() {
//        String account = up.getString("account", "");
//        String pw = up.getString("pw", "");
//        boolean isChecked = up.getBoolean("isChecked", false);
//        et_account.setText(account);
//        et_pw.setText(pw);
//        et_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        cb_rm.setChecked(isChecked);
//    }
}


