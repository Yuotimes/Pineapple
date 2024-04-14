package com.example.pineapple.beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PicCompress {
    public String compressAndEncodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos); // 压缩图片质量
        byte[] imageByteArray = baos.toByteArray();
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }
    public Bitmap decodeAndDecompressBase64ToImage(String base64String) {
        byte[] imageByteArray = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return bitmap;
    }
}
