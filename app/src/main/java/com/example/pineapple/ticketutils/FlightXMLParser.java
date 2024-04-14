package com.example.pineapple.ticketutils;

import android.util.Xml;

import com.example.pineapple.ticketbeans.BusInfo;
import com.example.pineapple.ticketbeans.PlaneInfo;
import com.example.pineapple.ticketbeans.TrainInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FlightXMLParser {



    public static List<PlaneInfo> parsePlaneInfo(InputStream in) {
        List<PlaneInfo> planeInfoList = new ArrayList<>();
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(in, "utf-8");
            int type = xpp.getEventType();
            PlaneInfo planeInfo = null;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("item")) {
                            planeInfo = new PlaneInfo();
                        } else if (xpp.getName().equals("title")) {
                            planeInfo.setTitle(xpp.nextText());
                        } else if (xpp.getName().equals("time")) {
                            planeInfo.setTime(xpp.nextText());
                        } else if (xpp.getName().equals("image")) {
                            planeInfo.setImage(xpp.nextText());
                        } else if (xpp.getName().equals("number")) {
                            planeInfo.setNumber(xpp.nextText());
                        } else if (xpp.getName().equals("price")) {
                            planeInfo.setPrice(xpp.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("item")) {
                            planeInfoList.add(planeInfo);
                        }
                        break;
                }
                type = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return planeInfoList;
    }

    public static List<TrainInfo> parseTrainInfo(InputStream in) {
        List<TrainInfo> trainInfoList = new ArrayList<>();
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(in, "utf-8");
            int type = xpp.getEventType();
            TrainInfo trainInfo = null;
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("item")) {
                            trainInfo = new TrainInfo();
                        } else if (xpp.getName().equals("title")) {
                            trainInfo.setTitle(xpp.nextText());
                        } else if (xpp.getName().equals("time")) {
                            trainInfo.setTime(xpp.nextText());
                        } else if (xpp.getName().equals("image")) {
                            trainInfo.setImage(xpp.nextText());
                        } else if (xpp.getName().equals("number")) {
                            trainInfo.setNumber(xpp.nextText());
                        } else if (xpp.getName().equals("price")) {
                            trainInfo.setPrice(xpp.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("item")) {
                            trainInfoList.add(trainInfo);
                        }
                        break;
                }
                type = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return trainInfoList;
    }

    public static List<BusInfo> parseBusInfo(InputStream in) {
        List<BusInfo> busInfoList = new ArrayList<>();
        XmlPullParser xpp = Xml.newPullParser();
        try {
            xpp.setInput(in, "utf-8");
            int type = xpp.getEventType();
            BusInfo busInfo = new BusInfo();
            while (type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if(xpp.getName().equals("item")) {
                            busInfo = new BusInfo();
                        }else if(xpp.getName().equals("title")){
                            busInfo.setTitle(xpp.nextText());
                        }
                        else if(xpp.getName().equals("time")){
                            busInfo.setTime(xpp.nextText());
                        }
                        else if(xpp.getName().equals("image")){
                            busInfo.setImage(xpp.nextText());
                        }
                        else if(xpp.getName().equals("number")){
                            busInfo.setNumber(xpp.nextText());
                        }
                        else if(xpp.getName().equals("price")){
                            busInfo.setPrice(xpp.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(xpp.getName().equals("item"))
                           busInfoList.add(busInfo);
                        break;
                }
                type = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return busInfoList;
    }



}