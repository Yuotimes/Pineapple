package com.example.pineapple.smartimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebImageCache {
    private static final String DISK_CACHE_PATH = "/web_image_cache/";

    private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;
    private String diskCachePath;
    private boolean diskCacheEnabled = false;
    private ExecutorService writeThread;

    public WebImageCache(Context context) {/*存储首地址，节省空间*/
        // Set up in-memory cache store
        memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

        // Set up disk cache store
        Context appContext = context.getApplicationContext();
        diskCachePath = appContext.getCacheDir().getAbsolutePath() + DISK_CACHE_PATH;
/*包名的绝对路径*/
        File outFile = new File(diskCachePath);/*new的时候文件还没出来*/
        outFile.mkdirs();/*mk创建文件夹*/

        diskCacheEnabled = outFile.exists();/*是否成功，存在返回true，不存在返回false*/

        // Set up threadpool for image fetching tasks
        writeThread = Executors.newSingleThreadExecutor();
    }

    public Bitmap get(final String url) {
        Bitmap bitmap = null;

        // Check for image in memory
        bitmap = getBitmapFromMemory(url);/*从内存拿，软内存，键值对*/

        // Check for image on disk cache
        if(bitmap == null) {
            bitmap = getBitmapFromDisk(url);/*拿不到，从硬盘里拿*/

            // Write bitmap back into memory cache
            if(bitmap != null) {
                cacheBitmapToMemory(url, bitmap);/*如果拿到了，url和key关联一下，从软内存里拿出来*/
            }
        }

        return bitmap;
    }

    public void put(String url, Bitmap bitmap) {
        cacheBitmapToMemory(url, bitmap);
        cacheBitmapToDisk(url, bitmap);
    }

    public void remove(String url) {/*先清缓存再清硬盘*//*remove是单个删除*/
        if(url == null){
            return;
        }

        // Remove from memory cache
        memoryCache.remove(getCacheKey(url));

        // Remove from file cache
        File f = new File(diskCachePath, getCacheKey(url));
        if(f.exists() && f.isFile()) {/*判断是不是文件*/
            f.delete();
        }
    }

    public void clear() {/*全删*//*先清除内存*/
        // Remove everything from memory cache
        memoryCache.clear();

        // Remove everything from file cache
        File cachedFileDir = new File(diskCachePath);
        if(cachedFileDir.exists() && cachedFileDir.isDirectory()) {
            File[] cachedFiles = cachedFileDir.listFiles();
            for(File f : cachedFiles) {/*拿出来一个一个删*/
                if(f.exists() && f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    private void cacheBitmapToMemory(final String url, final Bitmap bitmap) {
        memoryCache.put(getCacheKey(url), new SoftReference<Bitmap>(bitmap));
    }

    private void cacheBitmapToDisk(final String url, final Bitmap bitmap) {
        writeThread.execute(new Runnable() {
            @Override
            public void run() {
                if(diskCacheEnabled) {
                    BufferedOutputStream ostream = null;
                    try {
                        ostream = new BufferedOutputStream(new FileOutputStream(new File(diskCachePath, getCacheKey(url))), 2*1024);
                        bitmap.compress(CompressFormat.PNG, 100, ostream);/*图片压缩*/
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(ostream != null) {
                                ostream.flush();
                                ostream.close();
                            }
                        } catch (IOException e) {}
                    }
                }
            }
        });
    }

    private Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = null;
        SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(url));
        if(softRef != null){
            bitmap = softRef.get();
        }

        return bitmap;
    }

    private Bitmap getBitmapFromDisk(String url) {
        Bitmap bitmap = null;
        if(diskCacheEnabled){
            String filePath = getFilePath(url);
            File file = new File(filePath);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(filePath);/*绝对路径解码*/
            }
        }
        return bitmap;
    }

    private String getFilePath(String url) {
        return diskCachePath + getCacheKey(url);
    }

    private String getCacheKey(String url) {
        if(url == null){
            throw new RuntimeException("Null url passed in");
        } else {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");/*让其唯一*/
        }
    }
}

