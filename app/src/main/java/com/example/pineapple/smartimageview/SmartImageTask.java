package com.example.pineapple.smartimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class SmartImageTask implements Runnable {/*先看属性和方法*/
	
    private static final int BITMAP_READY = 0;

    private boolean cancelled = false;
    private OnCompleteHandler onCompleteHandler;
    private SmartImage image;   /*接口，webimage实现了它*/
    private Context context;

    public static class OnCompleteHandler extends Handler { /*就是handler*/
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap)msg.obj;/*图片传给主线程*/
            onComplete(bitmap);
        }

        public void onComplete(Bitmap bitmap){}; /*没有实现，希望这个类的实例去重写*/
    }

    public interface OnCompleteListener {  /*完成之后的监听时间，失败成功*/
    	void onSuccess(Bitmap bitmap);
    	void onFail();
    }

    public SmartImageTask(Context context, SmartImage image) {
        this.image = image;
        this.context = context;
    }

    @Override
    public void run() {
        if(image != null) {
            complete(image.getBitmap(context));
            context = null;/*让内存不要溢出*/
        }
    }

    public void setOnCompleteHandler(OnCompleteHandler handler){/*和定义在内部没关系，是通过这个方法把主线程定义的handler传给子线程*/
        this.onCompleteHandler = handler;/*设置handler*/
    }

    public void cancel() {  /*当前线程发回来的任何值都不要了*/
        cancelled = true;
    }/*告诉主线程不要了*/

    public void complete(Bitmap bitmap){
        if(onCompleteHandler != null && !cancelled) {
            onCompleteHandler.sendMessage(onCompleteHandler.obtainMessage(BITMAP_READY, bitmap));
        }
    }
}