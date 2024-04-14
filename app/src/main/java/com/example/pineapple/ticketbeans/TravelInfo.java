package com.example.pineapple.ticketbeans;

public class TravelInfo {

    private int id;
    private String objectid;

    public TravelInfo() {

    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    private String title;
    private String time;
    private String image;
    private String number;
    private String price;
    private String total_price;
    private String account;

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

    public String getTotal_price(){return  total_price;}
    public void setTotal_price(String total_price){this.price = price;}



    private boolean isSelected;

    public boolean isSelected(){return isSelected;}
    public void setSelected(boolean selected){
        isSelected = selected;
    }

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
}
