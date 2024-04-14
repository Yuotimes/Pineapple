package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class MyUsers extends BmobObject {
    private String account;
    private String password;
    private String nickname;
    private String head;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
