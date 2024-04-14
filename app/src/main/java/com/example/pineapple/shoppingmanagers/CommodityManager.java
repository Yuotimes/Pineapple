package com.example.pineapple.shoppingmanagers;

import android.content.Context;

import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.ticketproperties.AppProperties;
import com.example.pineapple.ticketsourceprovider.AssetsStreamOP;
import com.example.pineapple.ticketutils.CommodityXMLParser;
import com.example.pineapple.ticketutils.bmobaccess.CommodityBmobOP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommodityManager {
    public void saveCommodity2Bmob(Context context,String title,String price,String location,String image,String phone,String address,String goodsJson, CommodityBmobOP.OnQueryCompleteListener listener){
        CommodityBmobOP commodityBmobOP = new CommodityBmobOP();
        commodityBmobOP.insertCommodity2Bmob(context,title,price,location,image,phone,address,goodsJson, listener);
    }
    public void deleteCommodityBmob(Context context,String objectid,String title,String price,String location,String image,String phone,String address,String goodsJson){
        CommodityBmobOP commodityBmobOP = new CommodityBmobOP();
        commodityBmobOP.deleteCommodity(context,objectid,title,price,location,image,phone,address,goodsJson);
    }
    public List<Commodity> getCommodity(Context context){
        List<Commodity> commodityList = new ArrayList<>();
        try {
            InputStream in = new AssetsStreamOP(context).getInputStream(AppProperties.ASSETS_COMMODITY_NAME);
            commodityList = CommodityXMLParser.parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commodityList;
    }
}
