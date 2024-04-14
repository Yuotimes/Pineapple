package com.example.pineapple.ticketutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.pineapple.ticketexceptions.ErrorResponseCodeException;
import com.example.pineapple.ticketsourceprovider.HttpStreamOP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageCacheOP {
        public Bitmap getBitmapFromURL(Context context, String url){
            File file = new File(context.getCacheDir(), Base64.encodeToString(url.getBytes(), Base64.DEFAULT));
            if(file.exists() && file.length()>0){

                return BitmapFactory.decodeFile(file.getAbsolutePath());
            }else{
                Bitmap bitmap = null;
                InputStream in = null;
                try {
                    in = new HttpStreamOP().getInputStream(url);
                    if(in != null)
                    {
                        bitmap = BitmapFactory.decodeStream(in);
                        writeImage2Cache(bitmap,file);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ErrorResponseCodeException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
        }

    private void writeImage2Cache(Bitmap bitmap, File file){
            if (bitmap == null || file == null)
                return;
            new Thread(){
                @Override
                public void run() {
                    FileOutputStream fos = null;
                    BufferedOutputStream bos = null;
                    try {
                        fos = new FileOutputStream(file);
                        bos = new BufferedOutputStream(fos, 1024);
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);//压缩文件算法
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try {
                            fos.close();
                            bos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }.start();

        }
    }


