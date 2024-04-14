package com.example.pineapple.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.pineapple.R;
import com.example.pineapple.activities.EssayActivity;
import com.example.pineapple.adapters.EssaysAdapter;
import com.example.pineapple.adapters.MyCollectionsAdapter;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.beans.MyCollections;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionsFragment extends Fragment {

    private ListView lv_collections;
    private SwipeRefreshLayout collections_refresh;
    private SharedPreferences sp;
    private String account;

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
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        initView(view);
        showCollectionsList();
        return view;
    }

    private void initView(View view) {
        lv_collections = (ListView) view.findViewById(R.id.lv_collections);
        collections_refresh = (SwipeRefreshLayout) view.findViewById(R.id.collections_refresh);
        collections_refresh.setColorSchemeColors(Color.parseColor("#ff0000"),Color.parseColor("#00ff00"));
        collections_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showCollectionsList();
            }
        });
    }

    public void showCollectionsList(){
        new Thread(){
            @Override
            public void run(){
                BmobQuery<MyCollections> query = new BmobQuery<>();
                query.addWhereEqualTo("account", account);
                // 发起查询请求
                query.findObjects(new FindListener<MyCollections>() {
                    @Override
                    public void done(List<MyCollections> list, BmobException e) {
                        if (e == null) {
                            // 查询成功，处理查询结果
                            Collections.reverse(list);
                            MyCollectionsAdapter myCollectionsAdapter = new MyCollectionsAdapter(getContext(),list);
                            lv_collections.setAdapter(myCollectionsAdapter);
                            collections_refresh.setRefreshing(false);
                            showEssay(list);
                        }
                    }
                });
            }
        }.start();
    }

    public void showEssay(List<MyCollections> myCollectionsList){
        lv_collections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MyCollections myCollection = myCollectionsList.get(position);
                String essay_id = myCollection.getEssaysID();
                Intent intent = new Intent(getActivity(), EssayActivity.class);
                intent.putExtra("essayId", essay_id);
                startActivity(intent);
            }
        });
    }
}