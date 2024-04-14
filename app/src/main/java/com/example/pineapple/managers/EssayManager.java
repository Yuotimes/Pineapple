package com.example.pineapple.managers;

import com.example.pineapple.beans.Essays;
import com.example.pineapple.interfaces.GetEssayListener;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class EssayManager {
    public void getEssayList(GetEssayListener listener){
        BmobQuery<Essays> query = new BmobQuery<>();
        // 发起查询请求
        query.findObjects(new FindListener<Essays>() {
            @Override
            public void done(List<Essays> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果guan
                    Collections.reverse(list);
                    listener.getEssayList(list);
                }
            }
        });
    }
}
