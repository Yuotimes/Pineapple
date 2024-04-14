package com.example.pineapple.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
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

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.beans.PicCompress;
import com.example.pineapple.interfaces.UserListener;
import com.example.pineapple.ticketbeans.UserInfo;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateAccountActivity extends AppCompatActivity  implements UserListener{
    private static final int REQUEST_CODE_PHOTO_PICKER = 1;
    private EditText cr_account;
    private EditText cr_sure_password;
    private EditText cr_password;
    private ImageView cr_photo;
    private Button cr_upload;
    private EditText cr_nickname;
    private Button cr_sign;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        initView();
        createAccount();
        upLod();

    }

    private void initView(){
        cr_photo = (ImageView) findViewById(R.id.cr_bitmap);
        cr_nickname = (EditText) findViewById(R.id.cr_nickname);
        cr_account = (EditText) findViewById(R.id.cr_account);
        cr_password = (EditText) findViewById(R.id.cr_password);
        cr_sure_password = (EditText) findViewById(R.id.cr_sure_password);
        cr_upload = (Button) findViewById(R.id.cr_upload);
        cr_sign = (Button) findViewById(R.id.cr_sign);
        cr_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cr_sure_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }
    private void createAccount(){
        cr_sign.setOnClickListener(new View.OnClickListener() {

            private String head;

            @Override
            public void onClick(View view) {
                String account = cr_account.getText().toString().trim();
                String nickname = cr_nickname.getText().toString().trim();
                String password = cr_password.getText().toString().trim();
                String sure_pw = cr_sure_password.getText().toString().trim();
                // 获取 ImageView 控件的 Drawable 对象
                Drawable drawable = cr_photo.getDrawable();
                // 将 Drawable 对象转换为 Bitmap 对象
                Bitmap bitmap = null;
                if (drawable instanceof BitmapDrawable) {
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                } else if (drawable instanceof VectorDrawable || drawable instanceof androidx.vectordrawable.graphics.drawable.VectorDrawableCompat) {
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                }
                // 确保成功获取到 Bitmap 对象
                if (bitmap != null) {
                    // 在这里可以对获取到的 Bitmap 对象进行处理
                    PicCompress picCompress = new PicCompress();
                    head = picCompress.compressAndEncodeImageToBase64(bitmap);
                }

                if (drawable == null){
                    Toast.makeText(CreateAccountActivity.this, "请选择用户头像", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (account.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "请输入完整用户信息", Toast.LENGTH_SHORT).show();
                    return;

                } else if (!password.equals(sure_pw)) {
                    Toast.makeText(CreateAccountActivity.this, "两次输入的用户密码不一致，请检查", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setTitle("请检查").setMessage("确定你的账户是:  " + cr_account.getText().toString() + "                                  确定你的密码是:  " + cr_sure_password.getText().toString()).setNeutralButton("修改信息", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 创建查询对象
                            BmobQuery<MyUsers> query = new BmobQuery<>();
                            // 设置查询条件：account 列数据为 account
                            query.addWhereEqualTo("account", account);
                            // 发起查询请求
                            query.findObjects(new FindListener<MyUsers>() {
                                @Override
                                public void done(List<MyUsers> list, BmobException e) {
                                    if (e == null) {
                                        // 查询成功，处理查询结果
                                        onQuerySuccess(list.size(),view,account,password,nickname,head);
                                    } else {
                                        // 查询失败，处理异常
                                        Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                                        Log.e("MainActivity", "Error: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }).setCancelable(false).show();
                }
            }
        });
    }

    private void signUp(final View view,String account,String password,String nickname,String head) {
        MyUsers myUsers = new MyUsers();
        myUsers.setAccount(account);
        myUsers.setPassword(password);
        myUsers.setNickname(nickname);
        myUsers.setHead(head);
        myUsers.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "注册失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(nickname);
        userInfo.setAccount(account);
        userInfo.setPwd(password);
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }
    @Override
    public void onQuerySuccess(int i,View view,String ac,String pw,String name,String head) {
        if (i==0){
            CreateAccountActivity c = new CreateAccountActivity();
            c.signUp(view,ac,pw,name,head);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("account",ac);
            edit.putString("pw",pw);
            edit.putString("again","2");
            edit.commit();

            Intent intent = new Intent(CreateAccountActivity.this, MyHomeActivity.class);
            startActivity(intent);
            finishAffinity();
        }else {
            Snackbar.make(view, "账号已存在，请重设账号", Snackbar.LENGTH_LONG).show();
        }
    }


    public void upLod(){
        cr_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKER);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO_PICKER  && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(cr_photo);
        }
    }

}