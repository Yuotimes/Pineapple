package com.example.pineapple.ticketutils.bmobaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class GoodsBmobOP {
    public void insertGoods2Bmob(Context context, String title, String price, String image, String location, String description, int number){
        SharedPreferences up = context.getSharedPreferences("UP", Context.MODE_PRIVATE);
        String account = up.getString("account", "");
        Goods goods = new Goods();
        goods.setAccount(account);
        goods.setTitle(title);
        goods.setPrice(price);
        goods.setImage(image);
        goods.setLocation(location);
        goods.setDescription(description);
        goods.setNumber(number);
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "加入失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<Commodity> goodsList, Exception e);
    }

    public void queryGoodsBmob(OnQueryCompleteListener listener) {
        BmobQuery<Goods> query = new BmobQuery<>("Goods");

        // 执行查询操作
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> goods, BmobException e) {
                if (e == null) {
                    List<Commodity> commodityList = new ArrayList<>();
                    for (Goods good:goods) {
                        commodityList.add(Goods.convert(good));
                    }
                    // 调用回调接口，将查询结果传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(commodityList, null);
                    }
                } else {
                    // 查询失败，调用回调接口，将异常信息传递给调用者
                    if (listener != null) {
                        listener.onQueryComplete(null, e);
                    }
                    // 查询失败，输出错误信息
                    Log.e("BmobQuery", "查询失败：" + e.getMessage());
                }
            }
        });
    }
}

