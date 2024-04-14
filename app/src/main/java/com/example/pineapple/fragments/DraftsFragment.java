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
import android.widget.ListView;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.activities.EssayActivity;
import com.example.pineapple.activities.WriterActivity;
import com.example.pineapple.adapters.DraftAdapter;
import com.example.pineapple.beans.Drafts;
import com.example.pineapple.beans.MyCollections;
import com.example.pineapple.managers.QueryDraftsManager;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DraftsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DraftsFragment extends Fragment {

    private ListView drafts_lv;
    private SwipeRefreshLayout drafts_refresh;
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
        View view = inflater.inflate(R.layout.fragment_brafts, container, false);
        initView(view);
        showDraftsList();
        return view;
    }
    public void initView(View view) {
        drafts_lv = (ListView) view.findViewById(R.id.drafts_lv);
        drafts_refresh = (SwipeRefreshLayout) view.findViewById(R.id.drafts_refresh);
        drafts_refresh.setColorSchemeColors(Color.parseColor("#ff0000"),Color.parseColor("#00ff00"));
        drafts_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDraftsList();
            }
        });
    }

    public void showDraftsList(){
        QueryDraftsManager manager = new QueryDraftsManager();
        List<Drafts> draftsList = manager.queryDraftsDB(getContext(), account);
        Collections.reverse(draftsList);
        DraftAdapter adapter = new DraftAdapter(getContext(), draftsList);
        drafts_lv.setAdapter(adapter);
        drafts_refresh.setRefreshing(false);
        showDraft(draftsList);
    }

    public void showDraft(List<Drafts> draftsList){
        drafts_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Drafts drafts = draftsList.get(position);
                Intent intent = new Intent(getActivity(), WriterActivity.class);
                intent.putExtra("list", drafts);
                startActivity(intent);
            }
        });

    }
}