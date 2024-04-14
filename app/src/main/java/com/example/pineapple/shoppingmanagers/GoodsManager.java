package com.example.pineapple.shoppingmanagers;

import android.content.Context;
import android.util.Log;

import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.ticketutils.bmobaccess.GoodsBmobOP;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class GoodsManager {
    public void saveGoods2Bmob(Context context, String title, String price, String image, String location, String description, int number){
        GoodsBmobOP goodsBmobOP = new GoodsBmobOP();
        goodsBmobOP.insertGoods2Bmob(context,title,price,image,location,description,number);
    }
    public void queryGoodsBmob(GoodsBmobOP.OnQueryCompleteListener listener){
        GoodsBmobOP goodsBmobOP = new GoodsBmobOP();
        goodsBmobOP.queryGoodsBmob(listener);
    }
    public void updateGoodsBmob(Commodity info){
        Goods goods = new Goods();
        goods.setNumber(info.getNumber());
        goods.update(info.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("更新库存:","===更新成功===");
                }else{
                    Log.e("更新库存:","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
