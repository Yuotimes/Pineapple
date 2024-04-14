package com.example.pineapple.beans;

import cn.bmob.v3.BmobObject;

public class MyFriends extends BmobObject {
    private String account;
    private String friendsaccount;
    private String friendsname;
    private String friendshead;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
   public String getFriendsaccount(){
        return friendsaccount;
   }
   public void setFriendsaccount(String friendsaccount){
        this.friendsaccount = friendsaccount;
   }
    public String getFriendsname(){
        return friendsname;
    }
    public void setFriendsname(String friendsname){
        this.friendsname = friendsname;
    }
    public String getFriendshead(){
        return friendshead;
    }
    public void setFriendshead(String friendshead){
        this.friendshead = friendshead;
    }
}
