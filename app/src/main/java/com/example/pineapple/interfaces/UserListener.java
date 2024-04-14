package com.example.pineapple.interfaces;

import android.view.View;

import java.util.List;

public interface UserListener {
    void onQuerySuccess(int i , View view,String account,String pw,String name,String head);

}
