package com.example.pineapple.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.beans.MyCollections;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyFriends;
import com.example.pineapple.beans.PicCompress;
import com.example.pineapple.interfaces.CollectListener;
import com.example.pineapple.managers.CollectionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class EssayActivity  extends AppCompatActivity {

    private ImageButton bt_collect;
    private ImageButton bt_comment;
    private Button bt_follow;
    private String essayId;
    private ImageView user_head;
    private TextView user_name;
    private ImageView photo1;
    private TextView title;
    private TextView writing;
    private SharedPreferences sp;
    private String account;
    private TextView collection_number;
    private Button bt_show_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        essayId = getIntent().getStringExtra("essayId");
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);
        account = sp.getString("account", "");
        inItView();
        showEssay();
        showComment();
    }

    public void inItView(){
        bt_collect = (ImageButton) findViewById(R.id.bt_collect);
        bt_comment = (ImageButton) findViewById(R.id.bt_comment);
        bt_show_comments = (Button) findViewById(R.id.bt_show_comments);
        bt_follow = (Button) findViewById(R.id.bt_follow);
        user_head = (ImageView) findViewById(R.id.essay_user_head);
        user_name = (TextView) findViewById(R.id.essay_user_name);
        photo1 = (ImageView) findViewById(R.id.essay_photo1);
        title = (TextView) findViewById(R.id.essay_title);
        writing = (TextView) findViewById(R.id.essay_writing);
        collection_number = (TextView) findViewById(R.id.collection_number);
    }
    public void showEssay(){
        BmobQuery<Essays> query = new BmobQuery<>();
        // 设置查询条件：account 列数据为 account
        query.addWhereEqualTo("objectId", essayId);
        // 发起查询请求
        query.findObjects(new FindListener<Essays>() {
            @Override
            public void done(List<Essays> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    Essays essay = list.get(0);
                    String head = essay.getUserhead();
                    String name = essay.getUsername();
                    String user_account = essay.getAccount();
                    String essay_photo1 = essay.getPhoto1();
                    String essay_title = essay.getTitle();
                    String essay_writing = essay.getWriting();
                    String collectionnumber = essay.getCollectionnumber();
                    PicCompress picCompress = new PicCompress();
                    Bitmap bitmap1 = picCompress.decodeAndDecompressBase64ToImage(essay_photo1);
                    if (head != null){
                        Bitmap bitmap = picCompress.decodeAndDecompressBase64ToImage(head);
                        user_head.setImageBitmap(bitmap);
                    }
                    user_name.setText(name);
                    photo1.setImageBitmap(bitmap1);
                    title.setText(essay_title);
                    writing.setText(essay_writing);
                    collection_number.setText(collectionnumber);
                    if (user_account.equals(account)){
                        bt_follow.setBackgroundResource(R.drawable.others);
                        bt_follow.setText(null);
                        bt_follow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EssayActivity.this);
                                builder.setMessage("确定要删除这篇文章吗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(EssayActivity.this, MyHomeActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                        Essays essay = new Essays();
                                        essay.setObjectId(essayId);
                                        essay.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){
//                                                    Intent intent = new Intent(EssayActivity.this, HomeActivity.class);
//                                                    startActivity(intent);
                                                    Toast.makeText(EssayActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(EssayActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        BmobQuery<MyCollections> query = new BmobQuery<>();
                                        query.addWhereEqualTo("essaysID", essayId);
                                        query.findObjects(new FindListener<MyCollections>() {
                                            @Override
                                            public void done(List<MyCollections> list, BmobException e) {
                                                if (e == null) {
                                                    // 查询成功，处理查询结果
                                                    if(list.size()!=0){
                                                        for (MyCollections myCollections:list){
                                                            String collectionsObjectId = myCollections.getObjectId();
                                                            MyCollections myCollections1 = new MyCollections();
                                                            myCollections1.setObjectId(collectionsObjectId);
                                                            myCollections1.delete(new UpdateListener() {
                                                                @Override
                                                                public void done(BmobException e) {
                                                                    if (e==null){

                                                                    }else {

                                                                    }
                                                                }
                                                            });
                                                        }

                                                    }

                                                } else {
                                                    // 查询失败，处理异常
                                                    Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }
                                }).setCancelable(false).show();
                            }
                        });
                    }else {
                        BmobQuery<MyFriends> query = new BmobQuery<>();
                        query.addWhereEqualTo("account", account);
                        query.addWhereEqualTo("friendsaccount", user_account);
                        query.findObjects(new FindListener<MyFriends>() {
                            @Override
                            public void done(List<MyFriends> list, BmobException e) {
                                if (e == null){
                                    int i = list.size();
                                    if(i == 1){
                                        bt_follow.setText("取关");
                                        unFollowUser();
                                    }else {
                                        bt_follow.setText("+关注");
                                        followUser();
                                    }
                                }
                            }
                        });
                    }
                    CollectionManager manager = new CollectionManager();
                    manager.getCollectionList(account, essayId, new CollectListener() {
                        @Override
                        public void collectEssay(List<MyCollections> collections) {
                            int i = collections.size();
                            if (i==1){
                                bt_collect.setBackgroundResource(R.drawable.collection_log);
                                unCollEssay();

                            }else{
                                bt_collect.setBackgroundResource(R.drawable.collection);
                                collEssay();
                            }
                        }
                    });

//                    BmobQuery<MyCollections> query2 = new BmobQuery<>();
//                    query2.addWhereEqualTo("account", account);
//                    query2.addWhereEqualTo("essaysID", essayId);
//                    query2.findObjects(new FindListener<MyCollections>() {
//                        @Override
//                        public void done(List<MyCollections> list, BmobException e) {
//                            if (e == null) {
//                                // 查询成功，list中包含符合条件的数据
//                                int i = list.size();
//                                if (i==1){
//                                    bt_collect.setBackgroundResource(R.drawable.collection_log);
//                                    unCollEssay();
//
//                                }else{
//                                    bt_collect.setBackgroundResource(R.drawable.collection);
//                                    collEssay();
//
//                                }
//                            } else {
//                                // 查询失败，处理异常
//                            }
//                        }
//                    });

                }
            }
        });

    }

    public void unCollEssay(){
        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EssayActivity.this);
                builder.setTitle("确定要取消收藏吗？");
                builder.setNeutralButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BmobQuery<MyCollections> query = new BmobQuery<>();
                        query.addWhereEqualTo("account", account);
                        query.addWhereEqualTo("essaysID", essayId);
                        query.findObjects(new FindListener<MyCollections>() {
                            @Override
                            public void done(List<MyCollections> list, BmobException e) {
                                if (e == null) {
                                    // 查询成功，list中包含符合条件的数据
                                    for (MyCollections myCollections : list) {
                                        // 删除查询到的数据
                                        myCollections.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    BmobQuery<Essays> query = new BmobQuery<>();
                                                    // 设置查询条件：account 列数据为 account
                                                    query.addWhereEqualTo("objectId", essayId);
                                                    // 发起查询请求
                                                    query.findObjects(new FindListener<Essays>() {
                                                        @Override
                                                        public void done(List<Essays> list, BmobException e) {
                                                            if (e == null) {
                                                                // 查询成功，处理查询结果
                                                                for (Essays essays : list) {
                                                                    String collectionnumber = essays.getCollectionnumber();
                                                                    int oldNumber = Integer.parseInt(collectionnumber);
                                                                    int newNumber = oldNumber-1;
                                                                    String newcollectionnumber = Integer.toString(newNumber);
                                                                    essays.setCollectionnumber(newcollectionnumber);
                                                                    essays.update(new UpdateListener() {
                                                                        @Override
                                                                        public void done(BmobException e) {
                                                                            if (e==null){
                                                                                Snackbar.make(view, "取消收藏成功", Snackbar.LENGTH_LONG).show();
                                                                                showEssay();
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
                                                    // 删除成功
                                                } else {
                                                    // 删除失败，处理异常
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    // 查询失败，处理异常
                                }
                            }
                        });
                    }
                });
                builder.show();
            }
        });
    }

    public void collEssay(){
        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EssayActivity.this);
                builder.setTitle("确定要收藏吗？");
                builder.setNeutralButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BmobQuery<Essays> query = new BmobQuery<>();
                        query.addWhereEqualTo("objectId", essayId); // 设置account列的查询条件
                        query.findObjects(new FindListener<Essays>() {
                            @Override
                            public void done(List<Essays> list, BmobException e) {
                                if (e == null) {
                                    // 查询成功，list中包含符合条件的数据
                                    if (list.size() == 1) {
                                        Essays essay = list.get(0);
                                        String username = essay.getUsername();
                                        String userhead = essay.getUserhead();
                                        String title = essay.getTitle();
                                        String writing = essay.getWriting();
                                        String photo11 = essay.getPhoto1();
                                        String photo2 = essay.getPhoto2();
                                        String photo3 = essay.getPhoto3();
                                        // 处理查询结果
                                        MyCollections myCollections = new MyCollections();
                                        myCollections.setAccount(account);
                                        myCollections.setEssaysID(essayId);
                                        myCollections.setUsername(username);
                                        myCollections.setUserhead(userhead);
                                        myCollections.setTitle(title);
                                        myCollections.setWriting(writing);
                                        myCollections.setPhoto1(photo11);
                                        myCollections.setPhoto2(photo2);
                                        myCollections.setPhoto3(photo3);
                                        myCollections.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String objectId, BmobException e) {
                                                if (e == null) {
                                                    BmobQuery<Essays> query = new BmobQuery<>();
                                                    // 设置查询条件：account 列数据为 account
                                                    query.addWhereEqualTo("objectId", essayId);
                                                    // 发起查询请求
                                                    query.findObjects(new FindListener<Essays>() {
                                                        @Override
                                                        public void done(List<Essays> list, BmobException e) {
                                                            if (e == null) {
                                                                // 查询成功，处理查询结果

                                                                for (Essays essays : list) {
                                                                    String collectionnumber = essays.getCollectionnumber();
                                                                    int oldNumber = Integer.parseInt(collectionnumber);
                                                                    int newNumber = oldNumber+1;
                                                                    String newcollectionnumber = Integer.toString(newNumber);
                                                                    essays.setCollectionnumber(newcollectionnumber);
                                                                    essays.update(new UpdateListener() {
                                                                        @Override
                                                                        public void done(BmobException e) {
                                                                            if (e==null){
                                                                                Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG).show();
                                                                                showEssay();
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
                                                } else {
                                                    Snackbar.make(view, "收藏失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    // 查询失败，处理异常
                                }
                            }
                        });
                    }
                });
                builder.show();
            }
        });
    }

    public void showComment(){
        bt_show_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EssayActivity.this, CommentsActivity.class);
                intent.putExtra("essayId", essayId);
                startActivity(intent);
            }
        });
    }

    public void followUser(){
        bt_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<Essays> query = new BmobQuery<>();
                query.addWhereEqualTo("objectId", essayId); // 设置account列的查询条件
                query.findObjects(new FindListener<Essays>() {
                    @Override
                    public void done(List<Essays> list, BmobException e) {
                        if (e == null) {
                            Essays essay = list.get(0);
                            String username = essay.getUsername();
                            String userhead = essay.getUserhead();
                            String friendsaccount = essay.getAccount();
                            MyFriends myFriends = new MyFriends();
                            myFriends.setAccount(account);
                            myFriends.setFriendsaccount(friendsaccount);
                            myFriends.setFriendsname(username);
                            myFriends.setFriendshead(userhead);
                            myFriends.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(EssayActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                        showEssay();
                                    }

                                }

                            });
                        }
                    }
                });

            }
        });
    }

    public void unFollowUser(){
        bt_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<Essays> query = new BmobQuery<>();
                query.addWhereEqualTo("objectId", essayId); // 设置account列的查询条件
                query.findObjects(new FindListener<Essays>() {
                    @Override
                    public void done(List<Essays> list, BmobException e) {
                        if (e == null) {
                            Essays essay = list.get(0);
                            String friendsaccount = essay.getAccount();
                            BmobQuery<MyFriends> query = new BmobQuery<>();
                            query.addWhereEqualTo("account",account);
                            query.addWhereEqualTo("friendsaccount", friendsaccount); // 设置account列的查询条件
                            query.findObjects(new FindListener<MyFriends>() {
                                @Override
                                public void done(List<MyFriends> list, BmobException e) {
                                    if (e == null){
                                        for (MyFriends myFriends : list) {
                                            myFriends.delete(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null){
                                                        Toast.makeText(EssayActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                                                        showEssay();
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
        });
    }



    

}
