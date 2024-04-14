package com.example.pineapple.interfaces;

import com.example.pineapple.beans.Essays;

import java.util.List;

public interface GetEssayListener {
    void getEssayList(List<Essays> essays);
}
