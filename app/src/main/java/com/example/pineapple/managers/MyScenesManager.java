package com.example.pineapple.managers;

import com.example.pineapple.beans.MyScenes;
import com.example.pineapple.interfaces.GetMyScenesListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyScenesManager {
    public void getMyScenesList(String account,GetMyScenesListener listener){
        BmobQuery<MyScenes> query = new BmobQuery<>();
        query.addWhereEqualTo("account", account);
        // 发起查询请求
        query.findObjects(new FindListener<MyScenes>() {
            @Override
            public void done(List<MyScenes> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    listener.getMyScenesList(list);
                }
            }
        });
    }
}
