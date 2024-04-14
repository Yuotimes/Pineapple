package com.example.pineapple.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pineapple.MainActivity;
import com.example.pineapple.R;
import com.example.pineapple.activities.ChangeMeActivity;
import com.example.pineapple.interfaces.CheckNameListener;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyCollections;
import com.example.pineapple.beans.MyComments;
import com.example.pineapple.beans.MyFriends;
import com.example.pineapple.beans.MyMesS;
import com.example.pineapple.beans.MyMessage;
import com.example.pineapple.beans.MyScenes;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.beans.PicCompress;
import com.example.pineapple.managers.DeleteDraftsManager;
import com.example.pineapple.ticketbeans.UserInfo;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements CheckNameListener {
    private TextView tv_me_account;
    private Button me_change_userInfo;
    private Button me_logout;
    private Button me_delete;

    private String account = null;
    private ImageView me_bitmap;
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file= new File("/data/data/"+getContext().getPackageName().toString()+"/shared_prefs","login.xml");
        if(file.exists()) {
            sp = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
            account = sp.getString("account", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        logOut();
        deleteMe();
        changeME();
        return view;
    }

    private void initView (View view) {
        me_bitmap = (ImageView) view.findViewById(R.id.me_bitmap);
        tv_me_account = (TextView) view.findViewById(R.id.me_username);
        me_change_userInfo = (Button) view.findViewById(R.id.me_change_userInfo);
        me_logout = (Button) view.findViewById(R.id.me_logout);
        me_delete = (Button) view.findViewById(R.id.me_delete);
//        new Thread(){
//            @Override
//            public void run(){
                BmobQuery<MyUsers> query = new BmobQuery<>();
                // 设置查询条件：account 列数据为 account
                query.addWhereEqualTo("account", account);
                // 发起查询请求
                query.findObjects(new FindListener<MyUsers>() {
                    @Override
                    public void done(List<MyUsers> list, BmobException e) {
                        if (e == null) {
                            // 查询成功，处理查询结果
                            checkName(list.size(),list,view,account);
                        } else {
                            // 查询失败，处理异常
                            Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                            Log.e("MainActivity", "Error: " + e.getMessage());
                        }
                    }
                });
//            }
//        }.start();
    }

    public void logOut(){
        me_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定要登出应用吗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file= new File("/data/data/"+getContext().getPackageName().toString()+"/shared_prefs","login.xml");
                        if(file.exists()) {
                            file.delete();
                        }
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getContext(), "下次见", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }).setCancelable(false).show();
            }
        });
    }

    public void deleteMe(){
        me_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定要删除该账号吗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        DeleteMeManager manager = new DeleteMeManager();
//                        manager.deleteMeAccount(view, account);
                        BmobQuery<MyUsers> query = new BmobQuery<>();
                        // 设置查询条件：account 列数据为 account
                        query.addWhereEqualTo("account", account);
                        // 发起查询请求
                        query.findObjects(new FindListener<MyUsers>() {
                            @Override
                            public void done(List<MyUsers> list, BmobException e) {
                                if (e == null) {
                                    // 查询成功，处理查询结果
                                    BmobQuery<Essays> queryessay = new BmobQuery<>();
                                    queryessay.addWhereEqualTo("account",account);
                                    queryessay.findObjects(new FindListener<Essays>() {
                                        @Override
                                        public void done(List<Essays> list, BmobException e) {
                                            if (e == null) {
                                                for (Essays essays:list) {
                                                    String objectId = essays.getObjectId();
                                                    Essays essays1 = new Essays();
                                                    essays1.setObjectId(objectId);
                                                    essays1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyScenes> queryMyScenes = new BmobQuery<>();
                                    queryMyScenes.addWhereEqualTo("account",account);
                                    queryMyScenes.findObjects(new FindListener<MyScenes>() {
                                        @Override
                                        public void done(List<MyScenes> list, BmobException e) {
                                            if (e == null) {
                                                for (MyScenes myScenes:list) {
                                                    String objectId = myScenes.getObjectId();
                                                    MyScenes myScenes1 = new MyScenes();
                                                    myScenes1.setObjectId(objectId);
                                                    myScenes1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyCollections> queryMyCollections = new BmobQuery<>();
                                    queryMyCollections.addWhereEqualTo("account",account);
                                    queryMyCollections.findObjects(new FindListener<MyCollections>() {
                                        @Override
                                        public void done(List<MyCollections> list, BmobException e) {
                                            if (e == null) {
                                                for (MyCollections myCollections:list) {
                                                    String objectId = myCollections.getObjectId();
                                                    MyCollections myCollections1 = new MyCollections();
                                                    myCollections1.setObjectId(objectId);
                                                    myCollections1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyComments> queryMyComments = new BmobQuery<>();
                                    queryMyComments.addWhereEqualTo("account",account);
                                    queryMyComments.findObjects(new FindListener<MyComments>() {
                                        @Override
                                        public void done(List<MyComments> list, BmobException e) {
                                            if (e == null) {
                                                for (MyComments myComments:list) {
                                                    String objectId = myComments.getObjectId();
                                                    MyComments myComments1 = new MyComments();
                                                    myComments1.setObjectId(objectId);
                                                    myComments1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyFriends> queryMyFriends = new BmobQuery<>();
                                    queryMyFriends.addWhereEqualTo("account",account);
                                    queryMyFriends.findObjects(new FindListener<MyFriends>() {
                                        @Override
                                        public void done(List<MyFriends> list, BmobException e) {
                                            if (e == null) {
                                                for (MyFriends myFriends:list) {
                                                    String objectId = myFriends.getObjectId();
                                                    MyFriends myFriends1 = new MyFriends();
                                                    myFriends1.setObjectId(objectId);
                                                    myFriends1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyFriends> queryMyFriends2 = new BmobQuery<>();
                                    queryMyFriends2.addWhereEqualTo("friendsaccount",account);
                                    queryMyFriends2.findObjects(new FindListener<MyFriends>() {
                                        @Override
                                        public void done(List<MyFriends> list, BmobException e) {
                                            if (e == null) {
                                                for (MyFriends myFriends:list) {
                                                    String objectId = myFriends.getObjectId();
                                                    MyFriends myFriends1 = new MyFriends();
                                                    myFriends1.setObjectId(objectId);
                                                    myFriends1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyMesS> queryMyMess = new BmobQuery<>();
                                    queryMyMess.addWhereEqualTo("sendaccount",account);
                                    queryMyMess.findObjects(new FindListener<MyMesS>() {
                                        @Override
                                        public void done(List<MyMesS> list, BmobException e) {
                                            if (e == null) {
                                                for (MyMesS myMesS:list) {
                                                    String messageID = myMesS.getMessageID();
                                                    BmobQuery<MyMessage> queryMyMessage = new BmobQuery<>();
                                                    queryMyMessage.addWhereEqualTo("messageID",messageID);
                                                    queryMyMessage.findObjects(new FindListener<MyMessage>() {
                                                        @Override
                                                        public void done(List<MyMessage> list, BmobException e) {
                                                            if(e == null){
                                                                for(MyMessage myMessage:list){
                                                                    String objectId = myMessage.getObjectId();
                                                                    MyMessage myMessage1 = new MyMessage();
                                                                    myMessage1.setObjectId(objectId);
                                                                    myMessage1.delete(new UpdateListener() {
                                                                        @Override
                                                                        public void done(BmobException e) {
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    });
                                                    String objectId = myMesS.getObjectId();
                                                    MyMesS myMesS1 = new MyMesS();
                                                    myMesS1.setObjectId(objectId);
                                                    myMesS1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<MyMesS> queryMyMess2 = new BmobQuery<>();
                                    queryMyMess2.addWhereEqualTo("receiveaccount",account);
                                    queryMyMess2.findObjects(new FindListener<MyMesS>() {
                                        @Override
                                        public void done(List<MyMesS> list, BmobException e) {
                                            if (e == null) {
                                                for (MyMesS myMesS:list) {
                                                    String messageID = myMesS.getMessageID();
                                                    BmobQuery<MyMessage> queryMyMessage = new BmobQuery<>();
                                                    queryMyMessage.addWhereEqualTo("messageID",messageID);
                                                    queryMyMessage.findObjects(new FindListener<MyMessage>() {
                                                        @Override
                                                        public void done(List<MyMessage> list, BmobException e) {
                                                            if(e == null){
                                                                for(MyMessage myMessage:list){
                                                                    String objectId = myMessage.getObjectId();
                                                                    MyMessage myMessage1 = new MyMessage();
                                                                    myMessage1.setObjectId(objectId);
                                                                    myMessage1.delete(new UpdateListener() {
                                                                        @Override
                                                                        public void done(BmobException e) {
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    });
                                                    String objectId = myMesS.getObjectId();
                                                    MyMesS myMesS1 = new MyMesS();
                                                    myMesS1.setObjectId(objectId);
                                                    myMesS1.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    BmobQuery<UserInfo> queryUserInfo = new BmobQuery<>();
                                    queryUserInfo.addWhereEqualTo("account",account);
                                    queryUserInfo.findObjects(new FindListener<UserInfo>() {
                                        @Override
                                        public void done(List<UserInfo> list, BmobException e) {
                                            UserInfo userInfo = list.get(0);
                                            String objectID = userInfo.getObjectId();
                                            UserInfo userInfo1 = new UserInfo();
                                            userInfo1.setObjectId(objectID);
                                            userInfo1.delete(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {

                                                }
                                            });
                                        }
                                    });
                                    DeleteDraftsManager deleteDraftsManager = new DeleteDraftsManager();
                                    deleteDraftsManager.deleteDrafts(getContext(),account);
                                    queryIDAndDL(list.size(), list);
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
    }

    public void changeME(){
        me_change_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeMeActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void checkName(int i, List<MyUsers> list, View view, String account) {
        if(i==1){
            MyUsers user = list.get(0);
            String nickname = user.getNickname();
            String head = user.getHead();
            if (head != null){
                PicCompress picCompress = new PicCompress();
                Bitmap bitmap = picCompress.decodeAndDecompressBase64ToImage(head);
                me_bitmap.setImageBitmap(bitmap);
            }
            tv_me_account.setText(nickname);
        }
    }

    @Override
    public void queryIDAndDL(int i, List<MyUsers> list) {
        if (i == 1) {
            MyUsers user = list.get(0);
            String myID = user.getObjectId();
            MyUsers users = new MyUsers();
            users.setObjectId(myID);
            users.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        File file= new File("/data/data/"+getContext().getPackageName().toString()+"/shared_prefs","login.xml");
                        if(file.exists()) {
                            file.delete();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), "下次见", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                        }
                    }else {
                        Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}