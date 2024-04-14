package com.example.pineapple.managers;

import com.example.pineapple.beans.MyCollections;
import com.example.pineapple.interfaces.CollectListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CollectionManager {
    public void getCollectionList(String account,String essayId,CollectListener listener){
        BmobQuery<MyCollections> query = new BmobQuery<>();
        query.addWhereEqualTo("account",account);
        query.addWhereEqualTo("essaysID", essayId);
        // 发起查询请求
        query.findObjects(new FindListener<MyCollections>() {
            @Override
            public void done(List<MyCollections> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                    listener.collectEssay(list);
                }
            }
        });
    }
}
