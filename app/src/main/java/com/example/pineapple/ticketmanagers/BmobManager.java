package com.example.pineapple.ticketmanagers;

import android.content.Context;

import com.example.pineapple.ticketutils.bmobaccess.CreditBmobOP;
import com.example.pineapple.ticketutils.bmobaccess.FlightBmobOP;
import com.example.pineapple.ticketutils.bmobaccess.MallBmobOP;

public class BmobManager {

    public void savePlane2Bmob(Context context,String title,String time,String image,String number,String price){
        FlightBmobOP flightBmobOP = new FlightBmobOP(context);
        flightBmobOP.insertPlane2Bmob(context,title,time,image,number,price);

    }
    public void saveBus2Bmob(Context context,String title,String time,String image,String number,String price){
        FlightBmobOP flightBmobOP = new FlightBmobOP(context);
        flightBmobOP.insertBus2Bmob(context,title,time,image,number,price);
    }
    public void saveTrain2Bmob(Context context,String title,String time,String image,String number,String price){
        FlightBmobOP flightBmobOP = new FlightBmobOP(context);
        flightBmobOP.insertTrain2Bmob(context,title,time,image,number,price);
    }
    public void flight2CreditBmob(Context context,String objectid,String title,String time,String image,String number,String price){
        CreditBmobOP creditBmobOP = new CreditBmobOP();
        creditBmobOP.deleteFlightAndAddToCredit(context,objectid,title,time,image,number,price);
    }
    public void deleteFlightBmob(Context context,String objectid, String title,String time,String image,String number,String price){
        FlightBmobOP flightBmobOP = new FlightBmobOP(context);
        flightBmobOP.deleteFlightBmob(context,objectid, title,time,image,number,price);
    }
    public void saveMall2Bomb(Context context,String title,String image,String price,String location,String description){
        MallBmobOP mallBmobOP = new MallBmobOP();
        mallBmobOP.insertMall2Bmob(context,title,image,price,location,description);
    }
    public void deleteCreditBmob(Context context,String objectid, String title,String time,String image,String number,String price){
        CreditBmobOP creditBmobOP = new CreditBmobOP();
        creditBmobOP.deleteCreditBmob(context, objectid, title,time,image,number,price);
    }


}
