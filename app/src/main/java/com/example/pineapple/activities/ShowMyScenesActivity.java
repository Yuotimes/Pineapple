package com.example.pineapple.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.adapters.MyScenesAdapter;
import com.example.pineapple.beans.MyScenes;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.interfaces.GetMyScenesListener;
import com.example.pineapple.managers.MyScenesManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ShowMyScenesActivity extends AppCompatActivity {

    private TextView my_scenes_nickname;
    private ListView my_lv;
    private String account;
    private SharedPreferences sp;
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_scunes);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        initView();
        showMyScenesList();
    }

    public void initView(){
        my_scenes_nickname = (TextView) findViewById(R.id.my_scenes_nickname);
        my_lv = (ListView) findViewById(R.id.my_scenes_lv);
        tip = (TextView) findViewById(R.id.my_scenes_tip);
        account = sp.getString("account", "");
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
                        my_scenes_nickname.setText(nickname);
                    }
                } else {
                    // 查询失败，处理异常
                    Toast.makeText(ShowMyScenesActivity.this, "查询失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void showMyScenesList(){

        MyScenesManager manager = new MyScenesManager();
        manager.getMyScenesList(account, new GetMyScenesListener() {
            @Override
            public void getMyScenesList(List<MyScenes> myScenes) {
                int i = myScenes.size();
                if ( i == 0){
                    tip.setVisibility(View.VISIBLE);
                }
                MyScenesAdapter myScenesAdapter = new MyScenesAdapter(ShowMyScenesActivity.this, myScenes);
                my_lv.setAdapter(myScenesAdapter);
                deleteMyScenes(myScenes);
            }
        });
//        BmobQuery<MyScenes> query = new BmobQuery<>();
//        // 设置查询条件：account 列数据为 account
//        query.addWhereEqualTo("account", account);
//        // 发起查询请求
//        query.findObjects(new FindListener<MyScenes>() {
//            @Override
//            public void done(List<MyScenes> list, BmobException e) {
//                if (e == null) {
//                    // 查询成功，处理查询结果
//                    int i = list.size();
//                    if ( i == 0){
//                        tip.setVisibility(View.VISIBLE);
//                    }
//                    MyScenesAdapter myScenesAdapter = new MyScenesAdapter(ShowMyScenesActivity.this, list);
//                    my_lv.setAdapter(myScenesAdapter);
//                    deleteMyScenes(list);
//                } else {
//                    // 查询失败，处理异常
//                    Toast.makeText(ShowMyScenesActivity.this, "查询失败，请重试", Toast.LENGTH_SHORT).show();
//                    Log.e("MainActivity", "Error: " + e.getMessage());
//                }
//            }
//        });
    }

    public void deleteMyScenes(List<MyScenes> myScenesList){
        my_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyScenes myScenes = myScenesList.get(i);
                String objectId = myScenes.getObjectId();
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowMyScenesActivity.this);
                builder.setMessage("确定要删除该条购买记录吗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyScenes myScene = new MyScenes();
                        myScene.setObjectId(objectId);
                        myScene.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ShowMyScenesActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    showMyScenesList();
                                }else {
                                    Toast.makeText(ShowMyScenesActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).setCancelable(false).show();

            }
        });

    }


}