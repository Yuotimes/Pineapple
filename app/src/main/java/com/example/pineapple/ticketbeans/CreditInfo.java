package com.example.pineapple.ticketbeans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class CreditInfo extends BmobObject implements Serializable {
    private int id;
    private String title;
    private String time;
    private String image;
    private String number;
    private String price;
    private String total_price;
    private String account;
    private boolean isSelected;

    public String getTotal_price(){
        return total_price;
    }
    public void setTotal_price(String total_price){
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
