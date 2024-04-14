package com.example.pineapple.shoppingmanagers;

import android.content.Context;

import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.ticketutils.bmobaccess.CartBmobOP;

public class CartManager {
    public void saveCart2Bmob(Context context, Commodity commodity){
        CartBmobOP cartBmobOP = new CartBmobOP();
        cartBmobOP.insertCart2Bmob(context, commodity);
    }
    public void deleteCartBmob(Context context,String objectid,String title,String price,String location,String image){
        CartBmobOP cartBmobOP = new CartBmobOP();
        cartBmobOP.deleteCartFromBmob(context,objectid,title,price,location,image);
    }
}
