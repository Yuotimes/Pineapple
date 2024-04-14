package com.example.pineapple.interfaces;

import android.view.View;

import com.example.pineapple.beans.MyUsers;

import java.util.List;

public interface CheckNameListener {
    void checkName(int i , List<MyUsers> list, View view, String account);
    void queryIDAndDL(int i , List<MyUsers> list);
}
