package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;

public class MyMesS  extends BmobObject {
    private String sendaccount;
    private String receiveaccount;
    private String receivename;
    private String receivehead;
    private String messageID;
    public String getSendaccount() {
        return sendaccount;
    }
    public void setSendaccount(String sendaccount) {
        this.sendaccount = sendaccount;
    }
    public String getReceiveaccount() {
        return receiveaccount;
    }
    public void setReceiveaccount(String receiveaccount) {
        this.receiveaccount = receiveaccount;
    }
    public String getReceivename() {
        return receivename;
    }
    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }
    public String getReceivehead() {
        return receivehead;
    }
    public void setReceivehead(String receivehead) {
        this.receivehead = receivehead;
    }
    public String getMessageID() {
        return messageID;
    }
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
