package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;

public class MyComments extends BmobObject {
    private String account;
    private String essaysID;

    private String commentstext;
    private String username;
    private String userhead;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getEssaysID() {
        return essaysID;
    }
    public void setEssaysID(String essaysID) {
        this.essaysID = essaysID;
    }
    public String getCommentstext() {
        return commentstext;
    }
    public void setCommentstext(String commentstext) {
        this.commentstext = commentstext;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserhead() {
        return userhead;
    }
    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }
}
