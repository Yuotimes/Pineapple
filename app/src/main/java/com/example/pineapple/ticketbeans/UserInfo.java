package com.example.pineapple.ticketbeans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/*用户表信息实体*/
public class UserInfo extends BmobObject implements Serializable {
    private int id;
    private String account;
    private String pwd;
    private String username;
    private String point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
