package com.example.pineapple.shoppingmanagers;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.pineapple.ticketutils.ImageCacheOP;

public class BitmapFromURLManager {
    public Bitmap getBitmapFromURLManager(Context context,String path){
        Bitmap bitmap = new ImageCacheOP().getBitmapFromURL(context, path);
        return bitmap;

    }

}
