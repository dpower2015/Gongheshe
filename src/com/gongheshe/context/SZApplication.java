package com.gongheshe.context;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;

import com.baidu.frontia.FrontiaApplication;
import com.example.gongheshe.R;
import com.gongheshe.dialog.ForSureDlg;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.injectView.InjectView;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * 
 * @author ZhengZhiying
 * 
 * @Funtion the application for shizai-project
 * 
 * 
 */
public class SZApplication extends FrontiaApplication{

	/** ImageLoader使用的通用配置 */
	private DisplayImageOptions imgOption;
	private Builder builder;

	@Override
	public void onCreate() {
		super.onCreate();
		ShareSave.get().initSharePre(this);
		LoadingDlg.init(getApplicationContext());
		ForSureDlg.init(getApplicationContext());
		configDisplayImgOptionRound();
		
		//JPushInterface.setDebugMode(false); 	//设置开启日志,发布时请关闭日志
        //JPushInterface.init(this);     		// 初始化 JPush
	}

	/**
	 * 初始化图片缓存基本配置
	 */
	private void configDisplayImgOption() {
		// imgOption
		builder = new DisplayImageOptions.Builder()
				// 图片正在加载
				.showImageOnLoading(R.drawable.ic_empty)
				// 图片地址不正确
				.showImageForEmptyUri(R.drawable.ic_empty)
				// 图片下载(加载)失败
				.showImageOnFail(R.drawable.ic_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// 设置圆角,原先是20
				.displayer(new RoundedBitmapDisplayer(1));// .build();
		imgOption = builder.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		startService(new Intent(getApplicationContext(), SZService.class));
	}

	private void configDisplayImgOptionRound() {
		// builder.displayer(displayer)
		imgOption = new DisplayImageOptions.Builder()
				// 图片正在加载
				.showImageOnLoading(R.drawable.ic_empty)
				// 图片地址不正确
				.showImageForEmptyUri(R.drawable.ic_empty)
				// 图片下载(加载)失败
				.showImageOnFail(R.drawable.ic_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// 设置圆角,原先是20
				.displayer(new RoundedBitmapDisplayer(1)).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		LoggerSZ.i("SZApplication", "=======初始化==ImageLoader============");
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 实例化@InjectView 注解的成员
	 * 
	 * @param activity
	 */
	public void injectView(Activity activity) {
		// 反射机制,得到Activity中的所有定义的字段
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				// 方法返回true，如果指定类型的注解存在于此元素上
				if (field.isAnnotationPresent(InjectView.class)) {
					Log.i("Field", field.toString());
					// 获得该成员的annotation
					InjectView mInjectView = field
							.getAnnotation(InjectView.class);
					int viewId = mInjectView.id(); // 获得该注解的id
					View view = activity.findViewById(viewId);
					LoggerSZ.i("Field", String.valueOf(viewId));
					LoggerSZ.i("Field", view.getClass().toString());

					try {
						// 设置类的私有成员变量可以被访问
						field.setAccessible(true);
						// field.set(object,value)等价object.value=value
						field.set(activity, view);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					LoggerSZ.i("Field", "该字段没有被注解");
			}
		}
	}

	/**
	 * 获取图片缓存的配置信息
	 * 
	 * @return
	 */
	public DisplayImageOptions getImgOption() {
		if (imgOption == null) {
			configDisplayImgOption();
		}
		return imgOption;
	}
	
	public DisplayImageOptions getOptions() {
		DisplayImageOptions imgOption;
		Builder builder;
		builder = new DisplayImageOptions.Builder()
				// 图片正在加载
				.showImageOnLoading(R.drawable.ic_empty)
				// 图片地址不正确
				.showImageForEmptyUri(R.drawable.ic_empty)
				// 图片下载(加载)失败
				.showImageOnFail(R.drawable.ic_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// 设置圆角,原先是20
				.displayer(new RoundedBitmapDisplayer(0));// .build();
		imgOption = builder.build();
		return imgOption;
	}

	public int getVersion() {
		int version = 0;
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			version = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return version;
	}
	
}
