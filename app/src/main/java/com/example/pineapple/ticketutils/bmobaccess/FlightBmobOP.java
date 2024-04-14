package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.pineapple.ticketactivities.BusActivity;
import com.example.pineapple.ticketactivities.PlaneActivity;
import com.example.pineapple.ticketactivities.TrainActivity;
import com.example.pineapple.ticketbeans.BusInfo;
import com.example.pineapple.ticketbeans.Flight;
import com.example.pineapple.ticketbeans.PlaneInfo;
import com.example.pineapple.ticketbeans.TrainInfo;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class FlightBmobOP {
    private Context context;

    public FlightBmobOP(Context context){} {
        this.context = context;
    }
        public void insertPlane2Bmob(Context context,String title,String time,String image,String number,String price) {
            SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
            String account = up.getString("account", "");
            PlaneInfo planeInfo = new PlaneInfo();
            planeInfo.setAccount(account);
            planeInfo.setTitle(title);
            planeInfo.setTime(time);
            planeInfo.setImage(image);
            planeInfo.setNumber(number);
            planeInfo.setPrice(price);

            Flight flight = convertPlaneInfoToFlight(planeInfo);

            flight.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "购买失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ((PlaneActivity) context).finish();
                }
            });
        }

        private Flight convertPlaneInfoToFlight(PlaneInfo planeInfo) {
            Flight flight = new Flight();
            flight.setTitle(planeInfo.getTitle());
            flight.setTime(planeInfo.getTime());
            flight.setImage(planeInfo.getImage());
            flight.setNumber(planeInfo.getNumber());
            flight.setPrice(planeInfo.getPrice());
            flight.setAccount(planeInfo.getAccount());
            return flight;
        }

    public void insertBus2Bmob(Context context, String title, String time, String image, String number, String price) {
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        BusInfo busInfo = new BusInfo();
        busInfo.setAccount(account);
        System.out.println("-----"+account);
        busInfo.setTitle(title);
        busInfo.setTime(time);
        busInfo.setImage(image);
        busInfo.setNumber(number);
        busInfo.setPrice(price);

        Flight flight = convertBusInfoToFlight(busInfo);

        flight.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "购买失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                ((BusActivity) context).finish();
            }
        });
    }
    private Flight convertBusInfoToFlight(BusInfo busInfo){
        Flight flight = new Flight();
        flight.setTitle(busInfo.getTitle());
        flight.setTime(busInfo.getTime());
        flight.setImage(busInfo.getImage());
        flight.setNumber(busInfo.getNumber());
        flight.setPrice(busInfo.getPrice());
        flight.setAccount(busInfo.getAccount());
        return flight;
    }
    public void insertTrain2Bmob(Context context, String title, String time, String image, String number, String price) {
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        TrainInfo trainInfo = new TrainInfo();
        trainInfo.setAccount(account);
        trainInfo.setTitle(title);
        trainInfo.setTime(time);
        trainInfo.setImage(image);
        trainInfo.setNumber(number);
        trainInfo.setPrice(price);

        Flight flight = convertTrainInfoToFlight(trainInfo);

        flight.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "购买失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                ((TrainActivity) context).finish();
            }
        });
    }
    private Flight convertTrainInfoToFlight(TrainInfo trainInfo){
        Flight flight = new Flight();
        flight.setTitle(trainInfo.getTitle());
        flight.setTime(trainInfo.getTime());
        flight.setImage(trainInfo.getImage());
        flight.setNumber(trainInfo.getNumber());
        flight.setPrice(trainInfo.getPrice());
        flight.setAccount(trainInfo.getAccount());
        return flight;
    }


        // Existing code...

    public void deleteFlightBmob(Context context, String objectid, String title, String time, String image, String number, String price) {
        Flight flight=new Flight();
        flight.setObjectId(objectid);
        flight.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("deleteFlight：","===删除成功===");
                }else{
                    Log.e("deleteFlight：","删除失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
//            SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
//            String account = up.getString("account", "");
//
//            // 创建BmobQuery对象
//            BmobQuery<Flight> query = new BmobQuery<>();
//
//            // 设置查询条件，这里以账号和其他相关信息为例
//            query.addWhereEqualTo("account", account);
//            query.addWhereEqualTo("title", title);
//            query.addWhereEqualTo("time", time);
//            query.addWhereEqualTo("image", image);
//            query.addWhereEqualTo("number", number);
//            query.addWhereEqualTo("price", price);
//
//            // 执行查询操作
//            query.findObjects(new FindListener<Flight>() {
//                @Override
//                public void done(List<Flight> list, BmobException e) {
//                    if (e == null) {
//                        if (list.size() > 0) {
//                            Flight flight = list.get(0);
//
//                            // 执行删除操作
//                            flight.delete(new UpdateListener() {
//                                @Override
//                                public void done(BmobException e) {
//                                    if (e == null) {
//                                        // 删除成功
//                                        System.out.println("删除成功");
//                                    } else {
//                                        // 删除失败
//                                        System.out.println("删除失败：" + e.getMessage());
//                                    }
//                                }
//                            });
//                        } else {
//                            // 如果没有找到匹配的Flight对象
//                            System.out.println("未找到匹配的Flight对象");
//                        }
//                    } else {
//                        // 查询失败
//                        System.out.println("查询失败：" + e.getMessage());
//                    }
//                }
//            });
    }
//


}
