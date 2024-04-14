package com.example.pineapple.interfaces;

import com.example.pineapple.beans.MyMessage;

import java.util.List;

public interface GetMyMessageListener {
    void getMyMessageList(List<MyMessage> myMessages);
}
