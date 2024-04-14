package com.example.pineapple.ticketmanagers;

import android.app.Activity;
import android.content.Context;

import com.example.pineapple.ticketbeans.UserInfo;
import com.example.pineapple.ticketutils.ChangeUserInfo;
import com.example.pineapple.ticketutils.InitDB;
import com.example.pineapple.ticketutils.dbaccess.CheckUserInfo;
import com.example.pineapple.ticketutils.dbaccess.CreditSqlOP;
import com.example.pineapple.ticketutils.dbaccess.FlightSqlOP;
import com.example.pineapple.ticketutils.dbaccess.MallSqlOP;
import com.example.pineapple.ticketutils.dbaccess.UserInfoSqlOP;

public class SqlManager {

    public long saveFlight2DB(Context context,String title,String time,String image,String number,String price){
        FlightSqlOP flightSqlOP = new FlightSqlOP(context);
        long result =flightSqlOP.insertFlight(context,title,time,image,number,price);
        return result;
    }

    public long deleteCreditDB(Context context,int id){
        CreditSqlOP creditSqlOP = new CreditSqlOP(context);
        long result = creditSqlOP.deleteCredit(id);
        return result;
    }
    public long deleteFlightDB(Context context,int id){
        FlightSqlOP flightSqlOP = new FlightSqlOP(context);
        long result = flightSqlOP.deleteFlight(id);
        return result;
    }
    public long flight2CreditDB(Context context,String total_price,String title,String time,String image,String number,String price ){
        FlightSqlOP flightSqlOP = new FlightSqlOP(context);
        long result = flightSqlOP.flightInsertCredit(context,total_price,title, time, image, number, price);
        return result;
    }
    public long saveMall2DB(Context context,String title,String price,String location,String image){
        MallSqlOP mallSqlOP = new MallSqlOP(context);
        long result = mallSqlOP.insertMall(context,title,price,location,image);
        return result;
    }
    public int initDB( Context context){
        InitDB initDB = new InitDB();
        int i = initDB.initData(context);
        return i;

    }
    public boolean checkUserInfoInDB(Context context,String account ,String pw){
        CheckUserInfo checkUser = new CheckUserInfo();
        boolean b = checkUser.checkUserInfo(context, account, pw);
        return b;
    }
    public boolean checkUserInfoInDB(Activity context,String account){
        UserInfoSqlOP userInfoSqlOP = new UserInfoSqlOP(context);
        return userInfoSqlOP.queryUser(context, account)!=null;
    }
    public long saveUserInfo2DB(Context context, String username,String account, String sure_password) {
        UserInfoSqlOP userInfoSqlOP = new UserInfoSqlOP(context);
        long result = userInfoSqlOP.insertUserInfo(username,account, sure_password);
        return result;
    }
    public void changePW(Context context,String account,String pw){
        ChangeUserInfo changeUserInfo = new ChangeUserInfo();
        changeUserInfo.changeUserPW(context,account,pw);
    }

    public long insertUserInfo(Context context, UserInfo info) {
        UserInfoSqlOP userInfoSqlOP = new UserInfoSqlOP(context);
        long result = userInfoSqlOP.insertUserInfo(info);
        return result;
    }

    public long updateUserInfo(Context context, UserInfo info) {
        UserInfoSqlOP userInfoSqlOP = new UserInfoSqlOP(context);
        long result = userInfoSqlOP.updateUserInfo(info);
        return result;
    }

    public UserInfo getLoginUser(Activity context) {
        UserInfoSqlOP userInfoSqlOP = new UserInfoSqlOP(context);
        return userInfoSqlOP.getLoginUser(context);
    }
}
