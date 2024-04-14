package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;

public class MyScenes extends BmobObject {
    private String account;
    private String scene;
    private String date;
    private String ticket;
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getScene() {
        return scene;
    }
    public void setScene(String scene) {
        this.scene = scene;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
