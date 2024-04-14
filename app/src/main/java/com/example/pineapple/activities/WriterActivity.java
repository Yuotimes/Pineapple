package com.example.pineapple.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pineapple.R;
import com.example.pineapple.beans.Drafts;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.beans.PicCompress;
import com.example.pineapple.managers.CreateDraftsManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class WriterActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PHOTO_PICKER = 1;

//    private Uri uri;
    private ImageView bt_image_1;
    private ImageView bt_image_2;
    private ImageView bt_image_3;
    private Button bt_write;
    private EditText ed_title;
    private EditText ed_essay;
    private ImageButton writer_back;
    private SharedPreferences sp;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);
        initView();
        click();
        write_click();
    }

    public void initView(){
        bt_image_1 = (ImageView) findViewById(R.id.up_image_1);
        bt_image_2 = (ImageView) findViewById(R.id.up_image_2);
        bt_image_3 = (ImageView) findViewById(R.id.up_image_3);
        bt_write = (Button) findViewById(R.id.bt_write);
        writer_back = (ImageButton) findViewById(R.id.writer_back);
        ed_title = (EditText) findViewById(R.id.ed_title);
        ed_essay = (EditText) findViewById(R.id.ed_essay);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        account = sp.getString("account", "");
        Intent intent = getIntent();
        Drafts drafts = (Drafts) intent.getSerializableExtra("list");
        if (drafts!=null){
            if (drafts.getPhoto2() == null){
                Bitmap bitmap1 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto1());
                bt_image_1.setImageBitmap(bitmap1);
                bt_image_2.setImageIcon(null);
                bt_image_3.setImageIcon(null);
            } else if (drafts.getPhoto3()  == null) {
                Bitmap bitmap1 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto1());
                Bitmap bitmap2 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto2());
                bt_image_1.setImageBitmap(bitmap1);
                bt_image_2.setImageBitmap(bitmap2);
                bt_image_3.setImageIcon(null);
            }else {
                Bitmap bitmap1 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto1());
                Bitmap bitmap2 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto2());
                Bitmap bitmap3 = new PicCompress().decodeAndDecompressBase64ToImage(drafts.getPhoto3());
                bt_image_1.setImageBitmap(bitmap1);
                bt_image_2.setImageBitmap(bitmap2);
                bt_image_3.setImageBitmap(bitmap3);
            }
            ed_title.setText(drafts.getTitle());
            ed_essay.setText(drafts.getEssay());
        }
    }

    public void click(){

        bt_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKER);

            }
        });

        writer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void write_click(){

        bt_write.setOnClickListener(new View.OnClickListener() {

            private String image_1;

            @Override
            public void onClick(View view) {
                String title = ed_title.getText().toString().trim();
                String essay = ed_essay.getText().toString().trim();
                Drawable drawable1 = bt_image_1.getDrawable();
                Drawable drawable2 = bt_image_2.getDrawable();
                Drawable drawable3 = bt_image_3.getDrawable();
                if (drawable1 == null){
                    Snackbar.make(view, "请至少上传一张图片！", Snackbar.LENGTH_LONG).show();
                } else if (title.isEmpty()) {
                    Snackbar.make(view, "请输入标题！", Snackbar.LENGTH_LONG).show();
                } else if (essay.isEmpty()) {
                    Snackbar.make(view, "请输入文章内容！", Snackbar.LENGTH_LONG).show();
                }else {
                    String essayImage1 = getImageBitmap(drawable1);
                    String essayImage2 = getImageBitmap(drawable2);
                    String essayImage3 = getImageBitmap(drawable3);
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriterActivity.this);
                    builder.setMessage("是否先保存到本地草稿？").setNeutralButton("取消分布", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setNegativeButton("保存草稿", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
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
                                            String name = user.getNickname();
                                            String head = user.getHead();
                                            CreateDraftsManager manager = new CreateDraftsManager();
                                            long l = manager.createDrafts(WriterActivity.this, account, title, essay, essayImage1, essayImage2, essayImage3, name, head);
                                            Toast.makeText(WriterActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                            if (l == -1){
                                                Toast.makeText(WriterActivity.this, "保存草稿失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });


                        }
                    }).setPositiveButton("直接发布", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
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
                                            String name = user.getNickname();
                                            String head = user.getHead();
                                            saveEssay2Bomb(title,essay,essayImage1,essayImage2,essayImage3,name,head);
                                        }
                                    }
                                }
                            });
                            Intent intent = new Intent(WriterActivity.this, MyHomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }).setCancelable(false).show();
                }
            }
        });
    }
    public String getImageBitmap(Drawable drawable){
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
            String essayImage = picCompress.compressAndEncodeImageToBase64(bitmap);
            return essayImage;
        }else {
            return null;
        }
    }
    public void saveEssay2Bomb(String title,String writing,String photo1,String photo2,String photo3,String name,String head){
        Essays essays = new Essays();
        essays.setAccount(account);
        essays.setTitle(title);
        essays.setWriting(writing);
        essays.setPhoto1(photo1);
        essays.setPhoto2(photo2);
        essays.setPhoto3(photo3);
        essays.setUsername(name);
        essays.setUserhead(head);
        essays.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(WriterActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WriterActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO_PICKER  && resultCode == Activity.RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            if(clipData != null){
                int i = clipData.getItemCount();
                if (i>3){
                    Toast.makeText(this, "最多选择三张图片欧！", Toast.LENGTH_SHORT).show();
                    Uri uri1 = data.getClipData().getItemAt(0).getUri();
                    Glide.with(this).load(uri1).into(bt_image_1);
                    Uri uri2 = data.getClipData().getItemAt(1).getUri();
                    Glide.with(this).load(uri2).into(bt_image_2);
                    Uri uri3 = data.getClipData().getItemAt(2).getUri();
                    Glide.with(this).load(uri3).into(bt_image_3);
                    return;
                }else if (i == 3){
                    Uri uri1 = data.getClipData().getItemAt(0).getUri();
                    Glide.with(this).load(uri1).into(bt_image_1);
                    Uri uri2 = data.getClipData().getItemAt(1).getUri();
                    Glide.with(this).load(uri2).into(bt_image_2);
                    Uri uri3 = data.getClipData().getItemAt(2).getUri();
                    Glide.with(this).load(uri3).into(bt_image_3);
                    return;
                }else if (i == 2){
                    Uri uri1 = data.getClipData().getItemAt(0).getUri();
                    Glide.with(this).load(uri1).into(bt_image_1);
                    Uri uri2 = data.getClipData().getItemAt(1).getUri();
                    Glide.with(this).load(uri2).into(bt_image_2);
                    bt_image_3.setImageIcon(null);
                    return;
                }
            }else  {
                Uri uri = data.getData();
                Glide.with(this).load(uri).into(bt_image_1);
                bt_image_2.setImageIcon(null);
                bt_image_3.setImageIcon(null);

            }

        }
    }
}