package com.example.pineapple.beans;

import java.io.Serializable;

public class Drafts implements Serializable {
    private String account;
    private String title;
    private String essay;
    private String photo1;
    private String photo2;
    private String photo3;
    private String username;
    private String userhead;
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getEssay() {
        return essay;
    }
    public void setEssay(String essay) {
        this.essay = essay;
    }
    public String getPhoto1() {
        return photo1;
    }
    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }
    public String getPhoto2() {
        return photo2;
    }
    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }
    public String getPhoto3() {
        return photo3;
    }
    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
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
