package com.example.pineapple.smartimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.pineapple.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * Description：支持网络加载的ImageView 
 * 扩展功能：自定义加载中、加载失败的图片，缓存图片数据，加快图片读取速度，避免内存溢出。
 * 
 */
public class SmartImageView extends androidx.appcompat.widget.AppCompatImageView {

	//使用线程池管理网络请求
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);

	//图片下载的异步任务类
	private SmartImageTask currentTask;

	//加载中图片，布局配置
	private Drawable mLoadingDrawable;
	//加载失败图片，布局配置
	private Drawable mFailDrawable;
//继承自控件，一定有三个构造方法
	public SmartImageView(Context context) {
		this(context, null);
	}
    //第一个方法，把控件写出来，第一个方法；有textview，长宽高不用在xml里写（反射），直接写代码就行了；
	public SmartImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	//第二个构造函数，有属性就调用第二个，有一些长宽高
	public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,                        /*拿出属性*/
				R.styleable.SmartImageView, defStyle, 0);
		mLoadingDrawable = a.getDrawable(R.styleable.SmartImageView_onLoading);
		mFailDrawable = a.getDrawable(R.styleable.SmartImageView_onFail);
		a.recycle();/*让它好用*/
	}
     //写style用第三个构造方法，有一些细致的风格，要有样式
	/**
	 * @param url
	 * 			设置加载的图片地址
	 */
	public void setImageUrl(String url) {
		setImage(new WebImage(url), null);
	}

	/**
	 * 
	 * @param url
	 * 			设置加载的图片地址
	 * @param completeListener
	 * 			图片加载完成的回调
	 */
	public void setImageUrl(String url, SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), completeListener);/*一个成功一个失败*/
	}
	
	private void setImage(final SmartImage image, final SmartImageTask.OnCompleteListener completeListener) {
		// 设置加载中图片
		if (mLoadingDrawable != null) {
			setImageDrawable(mLoadingDrawable);/*父类的方法。显示白色加载图片*/
		}

		// 如果此View的加载任务已经开始，取消
		if (currentTask != null) {
			currentTask.cancel();/*执行完cancel，刚才的线程不要了*/
			currentTask = null;
		}

		// 新建新的任务
		currentTask = new SmartImageTask(getContext(), image);
		currentTask.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {/*外部传进来一个实例*/
					@Override
					public void onComplete(Bitmap bitmap) {
						if (bitmap != null) {
							//加载成功，设置图片
							setImageBitmap(bitmap);/*imageview的方法,方法前没有实例。要么是父类继承来，要么是自己定义的*/
							//设置成功的回调
							if (completeListener != null) {/*主要用来给用户一个更多的选择，成功一个功能，失败一个功能*/
								completeListener.onSuccess(bitmap);/*给用户选择*/
							}
						} else {
							// 设置失败图片
							if (mFailDrawable != null) {
								setImageDrawable(mFailDrawable);
							}
							//设置失败的回调
							if (completeListener != null) {
								completeListener.onFail();
							}
						}
					}
				});

		// 把任务加入线程池
		threadPool.execute(currentTask);/*线程池去执行，让他开始运行，执行线程*/
	}

	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}

}
