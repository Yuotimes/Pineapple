package com.example.pineapple.shoppingbeans;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Commodity extends BmobObject implements Serializable {
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private int id;
    private String image;
    private String title;
    private String location;
    private String description;
    private String price;
    private int number; //数量

    private String phone;
    private String address;
    //下单的商品信息
    private String goodsJson;
    //下单的商品信息对应的数据集合
    private List<Commodity> goodsList;
    private String account;

    private int stock; //库存

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGoodsJson() {
        return goodsJson;
    }

    public void setGoodsJson(String goodsJson) {
        this.goodsJson = goodsJson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Commodity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Commodity> goodsList) {
        this.goodsList = goodsList;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "CommodityInfo{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ",price='" + price + '\'' +

                '}';
    }



}
