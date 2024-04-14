package com.example.pineapple.shoppingbeans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class CartInfo extends BmobObject implements Serializable {

    private String title;
    private String price;
    private String location;
    private String image;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private boolean isSelected;

    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean selected){
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "CommodityInfo{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ",price='" + price + '\'' +

                '}';
    }




}
