package com.example.pineapple.shoppingactivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.ShoppingActivity;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.SqlManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ShoppingCreateAccountActivity extends AppCompatActivity {


    private final int REQUEST_CODE_PHOTO_PICKER = 1;
    private EditText cr_account;
    private EditText cr_sure_password;
    private EditText cr_password;
    private ImageView cr_photo;
    private Button cr_upload;
    private EditText cr_username;
    private SharedPreferences sp;
    private Uri uri;
    private String photo;
    private SharedPreferences up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        up = getSharedPreferences("UP", Context.MODE_PRIVATE);

        initView();

    }

    private void initView(){
        cr_username = (EditText) findViewById(R.id.cr_username);
        cr_account = (EditText) findViewById(R.id.cr_account);
        cr_sure_password = (EditText) findViewById(R.id.cr_sure_password);
        cr_password = (EditText) findViewById(R.id.cr_password);
        cr_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cr_sure_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void check(View view) {
        String username = cr_username.getText().toString().trim();
        String account = cr_account.getText().toString().trim();
        String password = cr_password.getText().toString().trim();
        String sure_password = cr_sure_password.getText().toString().trim();

        if (account.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Please enter your information ",
            Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(sure_password)) {
            Toast.makeText(this, "wrong sure password",
            Toast.LENGTH_SHORT).show();
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Careful check").setMessage("Sure your account is :  "
            + this.cr_account.getText().toString() + " Sure your password is:   "
            + cr_sure_password.getText().toString()).setPositiveButton("check again",
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setNeutralButton("Let`s Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                    //查询playerName叫“比目”的数据
                    query.addWhereEqualTo("account", account);
                    //执行查询方法
                    query.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> object, BmobException e) {
                            if(e==null){
                                if (object != null && !object.isEmpty()) { //云端已经注册该account，进行提示
                                    Toast.makeText(ShoppingCreateAccountActivity.this, "账号已注册", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUsername(username);
                                userInfo.setAccount(account);
                                userInfo.setPwd(password);
                                userInfo.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            // 用户信息保存成功
                                            Toast.makeText(ShoppingCreateAccountActivity.this, "User information saved to Bmob", Toast.LENGTH_SHORT).show();
                                            BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                                            //查询playerName叫“比目”的数据
                                            query.addWhereEqualTo("account", account);
                                            query.addWhereEqualTo("pwd", password);
                                            //执行查询方法
                                            query.findObjects(new FindListener<UserInfo>() {
                                                @Override
                                                public void done(List<UserInfo> object, BmobException e) {
                                                    if(e==null && object!=null && !object.isEmpty()){
                                                        SqlManager manager = new SqlManager();
                                                        long result = manager.saveUserInfo2DB(ShoppingCreateAccountActivity.this, username,account, sure_password);
                                                        if (result == -1){
                                                            Toast.makeText(ShoppingCreateAccountActivity.this, "Recreate!!Please try another account", Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        SharedPreferences.Editor edit = sp.edit();
                                                        SharedPreferences.Editor edit1 = up.edit();
                                                        edit.putString("account",account);
                                                        System.out.println("----"+account);
                                                        edit.putString("pw",sure_password);
                                                        edit.putString("uid", object.get(0).getObjectId());
                                                        edit.putBoolean("isChecked",true);
                                                        edit1.putString("account",account);
                                                        edit1.putString("pw",sure_password);
                                                        edit1.putString("uid", object.get(0).getObjectId());
                                                        edit1.putBoolean("isChecked",true);

                                                        edit.commit();
                                                        edit1.commit();
                                                        Intent intent = new Intent(ShoppingCreateAccountActivity.this, ShoppingActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        if (e!=null)
                                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });
                                        } else {
                                            // 处理保存失败的情况
                                            Toast.makeText(ShoppingCreateAccountActivity.this, "Failed to save user information to Bmob", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
            }).setCancelable(false).show();

        }
    }
}
