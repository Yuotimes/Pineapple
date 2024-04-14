package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;

public class MyMessage extends BmobObject {
    private String sendname;
    private String sendaccount;
    private String sendhead;
    private String messageID;
    private String sendtext;
    public String getSendname() {
        return sendname;
    }
    public void setSendname(String sendname) {
        this.sendname = sendname;
    }
    public String getSendaccount() {
        return sendaccount;
    }
    public void setSendaccount(String sendaccount) {
        this.sendaccount = sendaccount;
    }
    public String getSendhead() {
        return sendhead;
    }
    public void setSendhead(String sendhead) {
        this.sendhead = sendhead;
    }
    public String getSendtext() {
        return sendtext;
    }
    public void setSendtext(String sendtext) {
        this.sendtext = sendtext;
    }
    public String getMessageID() {
        return messageID;
    }
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
