package com.example.pineapple.beans;

import android.view.View;

import com.example.pineapple.fragments.MeFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DeleteMe {
    public void deleteMyAccount(View view,String account){
        BmobQuery<MyUsers> query = new BmobQuery<>();
        // 设置查询条件：account 列数据为 account
        query.addWhereEqualTo("account", account);
        // 发起查询请求
        query.findObjects(new FindListener<MyUsers>() {
            @Override
            public void done(List<MyUsers> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    MeFragment meFragment = new MeFragment();
                    meFragment.queryIDAndDL(list.size(),list);
                } else {
                    // 查询失败，处理异常
                    Snackbar.make(view, "查询失败，请重试", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
