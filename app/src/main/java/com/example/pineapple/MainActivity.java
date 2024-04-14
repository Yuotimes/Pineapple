package com.example.pineapple;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.activities.CreateAccountActivity;
import com.example.pineapple.activities.MyHomeActivity;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.interfaces.CheckUserListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements CheckUserListener {
    private SharedPreferences sp;
    private EditText et_account;
    private EditText et_pw;
    private Button bt_signIn;
    private Button bt_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        File file= new File("/data/data/"+this.getPackageName().toString()+"/shared_prefs","login.xml");
        if(file.exists()) {
            String again = sp.getString("again", "");
            if (again == null){
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("again","2");
                edit.commit();
            } else if (again.equals("2")) {
                Intent intent = new Intent(MainActivity.this, MyHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        initView();
        create();
        signIn();
    }

    private void initView(){
        et_account = (EditText) findViewById(R.id.et_account);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        bt_account = (Button) findViewById(R.id.bt_account);
        bt_signIn = (Button) findViewById(R.id.bt_signIn);

    }

    public void create() {
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(){
        bt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = et_account.getText().toString().trim();
                String pw = et_pw.getText().toString().trim();
                if (account.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
                }
                BmobQuery<MyUsers> query = new BmobQuery<>();
                // 设置查询条件：account 列数据为 account
                query.addWhereEqualTo("account", account);
                // 发起查询请求
                query.findObjects(new FindListener<MyUsers>() {
                    @Override
                    public void done(List<MyUsers> list, BmobException e) {
                        if (e == null) {
                            // 查询成功，处理查询结果
                            checkSignAccount(list.size(),list,view,account,pw);
                        } else {
                            // 查询失败，处理异常
                            Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                            Log.e("MainActivity", "Error: " + e.getMessage());
                        }
                    }
                });

            }
        });
    }

    @Override
    public void checkSignAccount(int i, List<MyUsers> list,View view,String account,String pw) {
        if(i == 1){
           MyUsers user = list.get(0);
           String db_pw = user.getPassword();
           if (pw.equals(db_pw)){
               saveUserInfo(account,pw);
               Intent intent = new Intent(MainActivity.this, MyHomeActivity.class);
               startActivity(intent);
               finish();
           }else{
               Snackbar.make(view, "密码错误，请重试", Snackbar.LENGTH_LONG).show();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("账户不存在，检查账户信息或创建账户").setNeutralButton("检查", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setPositiveButton("创建", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
            }).setCancelable(false).show();
        }

    }

    private void saveUserInfo(String account, String pw){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("account",account);
        edit.putString("pw",pw);
        edit.putString("again","2");
        edit.commit();
    }
}