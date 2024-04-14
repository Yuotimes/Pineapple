package com.example.pineapple.managers;

import com.example.pineapple.beans.MyMessage;
import com.example.pineapple.interfaces.GetMyMessageListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyMessageManager {
    public void getMyMessageList(String messageID , GetMyMessageListener listener){
        BmobQuery<MyMessage> query = new BmobQuery<>();
        query.addWhereEqualTo("messageID", messageID);
        // 发起查询请求
        query.findObjects(new FindListener<MyMessage>() {
            @Override
            public void done(List<MyMessage> list, BmobException e) {
                if (e == null) {
                    // 查询成功，处理查询结果
                   listener.getMyMessageList(list);
                }
            }
        });

    }
}
