package com.example.pineapple.ticketutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.ticketutils.dbaccess.MySqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class QueryCartDB {
    public static List<Cart> queryCartDB(Context context){
    List<Cart> cartList = new ArrayList<>();
    MySqliteHelper helper = new MySqliteHelper(context);
    SQLiteDatabase db = helper.getReadableDatabase();
    Cursor cursor = db.query("cart", new String[]{"title", "price","location","image"}, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()){
                    Cart cart = new Cart();
                    String title = cursor.getString(0);
                    String price = cursor.getString(1);
                    String location = cursor.getString(2);
                    String image = cursor.getString(3);
                    cart.setTitle(title);
                    cart.setPrice(price);
                    cart.setLocation(location);
                    cart.setImage(image);
                    cartList.add(cart);
                }
            }
            cursor.close();
        }
        db.close();
        return cartList;
    }

}

