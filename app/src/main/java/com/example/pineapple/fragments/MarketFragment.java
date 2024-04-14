package com.example.pineapple.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pineapple.HomeActivity;
import com.example.pineapple.R;
import com.example.pineapple.TicketsActivity;
import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketmanagers.SqlManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketFragment extends Fragment {


    private String account = null;
    private Button bt_show_tickets;
    private SharedPreferences sp;
    private SharedPreferences up;
    private Button bt_show_market;
    private String pw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
        up = getContext().getSharedPreferences("UP", getContext().MODE_PRIVATE);
        account = sp.getString("account", "");
        pw = sp.getString("pw", "");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View  view= inflater.inflate(R.layout.fragment_market, container, false);
         inItView(view);
        return view;

    }

    public void inItView(View view){
        bt_show_tickets = (Button) view.findViewById(R.id.bt_show_tickets);
        bt_show_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            UserInfo userInfo = object.get(0);
                            String pwd = userInfo.getPwd();
                            SharedPreferences.Editor edit1 = up.edit();
                            edit1.putString("account",account);
                            edit1.putString("pw",pwd);
                            edit1.putString("uid", object.get(0).getObjectId());
                            edit1.commit();
                            SqlManager manager = new SqlManager();
                            //与云端做用户数据同步，加入本地数据库被清了，还能同步到云端的数据
                            if (manager.checkUserInfoInDB(getActivity(), account)) {
                                manager.updateUserInfo(getContext(), userInfo);
                            } else {
                                manager.insertUserInfo(getContext(), userInfo);
                            }

                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                Intent intent = new Intent(getContext(), TicketsActivity.class);
                startActivity(intent);
            }
        });

        bt_show_market = (Button) view.findViewById(R.id.bt_show_market);
        bt_show_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            UserInfo userInfo = object.get(0);
                            String pwd = userInfo.getPwd();
                            SharedPreferences.Editor edit1 = up.edit();
                            edit1.putString("account",account);
                            edit1.putString("pw",pwd);
                            edit1.putString("uid", object.get(0).getObjectId());
                            edit1.commit();
                            SqlManager manager = new SqlManager();
                            //与云端做用户数据同步，加入本地数据库被清了，还能同步到云端的数据
                            if (manager.checkUserInfoInDB(getActivity(), account)) {
                                manager.updateUserInfo(getContext(), userInfo);
                            } else {
                                manager.insertUserInfo(getContext(), userInfo);
                            }

                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}