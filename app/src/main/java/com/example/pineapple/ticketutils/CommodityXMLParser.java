package com.example.pineapple.ticketutils;

import android.util.Xml;

import com.example.pineapple.shoppingbeans.Commodity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommodityXMLParser {
    public static List<Commodity> parse(InputStream in) {
        List<Commodity> commodityList = new ArrayList<>();
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(in, "utf-8");
            int type = xpp.getEventType();
            Commodity commodity = new Commodity();
            while (type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if(xpp.getName().equals("item")) {
                            commodity = new Commodity();
                        }else if(xpp.getName().equals("title")){
                            commodity.setTitle(xpp.nextText());
                        }
                        else if(xpp.getName().equals("image")){
                            commodity.setImage(xpp.nextText());
                        }
                        else if(xpp.getName().equals("location")){
                            commodity.setLocation(xpp.nextText());
                        }
                        else if(xpp.getName().equals("price")){
                            commodity.setPrice(xpp.nextText());
                        }
                        else if(xpp.getName().equals("description")){
                            commodity.setDescription(xpp.nextText());
                        }


                        break;


                    case XmlPullParser.END_TAG:
                        if(xpp.getName().equals("item"))
                            commodityList.add(commodity);
                        break;
                }
                type = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return commodityList;
    }

}