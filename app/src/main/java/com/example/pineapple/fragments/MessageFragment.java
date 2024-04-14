package com.example.pineapple.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.pineapple.R;
import com.example.pineapple.activities.FriendsActivity;
import com.example.pineapple.activities.SendMessageActivity;
import com.example.pineapple.adapters.MyMesSAdapter;
import com.example.pineapple.beans.MyMesS;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    private Button my_friends;
    private ListView lv_messageLists;
    private SharedPreferences sp;
    private String account ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
        account = sp.getString("account", "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        click();
        showMessageList();
        return view;
    }

    private void initView(View view) {
        my_friends = (Button) view.findViewById(R.id.my_friends);
        lv_messageLists = (ListView) view.findViewById(R.id.lv_messageLists);
    }

    public void click(){
        my_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMessageList() {
        BmobQuery<MyMesS> query = new BmobQuery<>();
        query.addWhereEqualTo("sendaccount", account);
        // 发起查询请求
        query.findObjects(new FindListener<MyMesS>() {
            @Override
            public void done(List<MyMesS> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    Collections.reverse(list);//倒叙反传列表
                    MyMesSAdapter myMesSAdapter = new MyMesSAdapter(getContext(), list);
                    lv_messageLists.setAdapter(myMesSAdapter);
                    showSend(list);
                }
            }
        });
    }
    public void showSend(List<MyMesS> myMesSList){
        lv_messageLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyMesS myMesS = myMesSList.get(i);
                String MessageID = myMesS.getMessageID();
                Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                intent.putExtra("messageID", MessageID);
                startActivity(intent);
            }
        });
    }
}