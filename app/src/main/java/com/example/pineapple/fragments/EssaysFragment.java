package com.example.pineapple.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pineapple.R;
import com.example.pineapple.activities.EssayActivity;
import com.example.pineapple.activities.MyEssaysActivity;
import com.example.pineapple.activities.ScenesActivity;
import com.example.pineapple.adapters.EssaysAdapter;
import com.example.pineapple.beans.Essays;
import com.example.pineapple.interfaces.GetEssayListener;
import com.example.pineapple.managers.EssayManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EssaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EssaysFragment extends Fragment {


    private ImageButton bt_travel_date;

    private ListView lv_essays;
    private SwipeRefreshLayout swipe_my_refresh;
    private Button my_essays;
    private EssayManager essayManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_essays, container, false);
        initView(view);

        showEssaysList();
        click();
        return view;

    }

    private void initView(View view) {
        my_essays = (Button) view.findViewById(R.id.my_essays);
        lv_essays = (ListView) view.findViewById(R.id.lv_essays);
        bt_travel_date = (ImageButton) view.findViewById(R.id.bt_travel_date);
        swipe_my_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipe_my_refresh.setColorSchemeColors(Color.parseColor("#ff0000"),Color.parseColor("#00ff00"));
        essayManager = new EssayManager();
        swipe_my_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showEssaysList();
            }
        });
    }

    public void click(){
        bt_travel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , ScenesActivity.class);
                startActivity(intent);
            }
        });
        my_essays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , MyEssaysActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showEssaysList(){
        essayManager.getEssayList(new GetEssayListener() {
            @Override
            public void getEssayList(List<Essays> essays) {
                EssaysAdapter essaysAdapter = new EssaysAdapter(getContext(),essays);
                lv_essays.setAdapter(essaysAdapter);
                swipe_my_refresh.setRefreshing(false);
                showEssay(essays);
            }
        });
    }

    public void showEssay(List<Essays> essaysList){
        lv_essays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Essays essay = essaysList.get(i);
                String essay_id = essay.getObjectId();
                Intent intent = new Intent(getActivity(), EssayActivity.class);
                intent.putExtra("essayId", essay_id);
                startActivity(intent);
            }
        });

    }


}