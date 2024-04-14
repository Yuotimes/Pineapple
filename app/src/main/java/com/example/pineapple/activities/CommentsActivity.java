package com.example.pineapple.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pineapple.R;
import com.example.pineapple.adapters.CommentsAdapter;
import com.example.pineapple.adapters.EssaysAdapter;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyCollections;
import com.example.pineapple.beans.MyComments;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.beans.PicCompress;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CommentsActivity extends AppCompatActivity {
    private String essayId;
    private SharedPreferences sp;
    private String account;
    private ListView lv_comments;
    private TextView comments_title;
    private EditText ed_comments;
    private ImageButton bt_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        essayId = getIntent().getStringExtra("essayId");
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);
        account = sp.getString("account", "");
        inItView();
        showCommentsList();
        writeComments();
    }

    public void inItView(){
        lv_comments = (ListView) findViewById(R.id.lv_comments);
        comments_title = (TextView) findViewById(R.id.comments_title);
        ed_comments = (EditText) findViewById(R.id.ed_comments);
        bt_comment = (ImageButton) findViewById(R.id.bt_comment);
        BmobQuery<Essays> query = new BmobQuery<>();
        // 设置查询条件：account 列数据为 account
        query.addWhereEqualTo("objectId", essayId);
        // 发起查询请求
        query.findObjects(new FindListener<Essays>() {
            @Override
            public void done(List<Essays> list, BmobException e) {
                if (e == null) {
                    if(list.size()==1){
                        Essays essay = list.get(0);
                        String title = essay.getTitle();
                        comments_title.setText(title);
                    }
                } else {
                    // 查询失败，处理异常
                }
            }
        });
    }

    public void writeComments(){
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comments_text = ed_comments.getText().toString().trim();
                if (comments_text.isEmpty()) {
                    Snackbar.make(view, "请输入评论内容！", Snackbar.LENGTH_LONG).show();
                } else {
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
                                if (i == 1) {
                                    MyUsers user = list.get(0);
                                    String name = user.getNickname();
                                    String head = user.getHead();
                                    MyComments myComments = new MyComments();
                                    myComments.setAccount(account);
                                    myComments.setEssaysID(essayId);
                                    myComments.setCommentstext(comments_text);
                                    myComments.setUsername(name);
                                    myComments.setUserhead(head);
                                    myComments.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String objectId, BmobException e) {
                                            if (e == null) {
                                                showCommentsList();
                                                ed_comments.setText("");
                                                // 隐藏输入法键盘
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(ed_comments.getWindowToken(), 0);
                                                Toast.makeText(CommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(CommentsActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void showCommentsList(){
        BmobQuery<MyComments> query = new BmobQuery<>();
        query.addWhereEqualTo("essaysID", essayId);
        // 发起查询请求
        query.findObjects(new FindListener<MyComments>() {
            @Override
            public void done(List<MyComments> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    Collections.reverse(list);
                    CommentsAdapter commentsAdapter = new CommentsAdapter(CommentsActivity.this,list);
                    lv_comments.setAdapter(commentsAdapter);
                }
            }
        });
    }
}