package com.example.pineapple.interfaces;

import android.view.View;

import com.example.pineapple.beans.MyUsers;

import java.util.List;

public interface CheckUserListener {

    void checkSignAccount(int i , List<MyUsers> list, View view, String account, String pw);

}
