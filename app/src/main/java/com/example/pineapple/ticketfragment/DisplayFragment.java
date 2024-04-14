package com.example.pineapple.ticketfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.pineapple.ticketadapters.MallAdapter;
import com.example.pineapple.ticketbeans.MallInfo;
import com.example.pineapple.databinding.FragmentDisplayBinding;
import com.example.pineapple.ticketutils.QueryBmobMall;

import java.util.List;

public class DisplayFragment extends Fragment {

    private FragmentDisplayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        QueryBmobMall.queryMallBmob(new QueryBmobMall.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<MallInfo> creditInfoList, Exception e) {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                binding.lvMall.setLayoutManager(layoutManager);
                MallAdapter malladapter = new MallAdapter(getContext(), creditInfoList);
                binding.lvMall.setAdapter(malladapter);
                malladapter.setOnItemClickListener(new MallAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(MallInfo mallInfo,int position){
                        //已兑换的商品点击事件跳转，暂未处理
//                Intent intent = new Intent(getActivity(), MallActivity.class);
//                mallInfo = mallInfoList.get(position);
//                intent.putExtra("list",mallInfo);
//                startActivity(intent);
                    }
                });
            }
        }, getContext());
        return root;
    }

}
