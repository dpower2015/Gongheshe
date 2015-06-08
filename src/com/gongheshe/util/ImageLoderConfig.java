package com.gongheshe.util;

import android.graphics.Bitmap;

import com.example.gongheshe.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;


public class ImageLoderConfig {

	private static DisplayImageOptions options;
	
	public static DisplayImageOptions getOptions(){
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.color.white)
        .showImageForEmptyUri(R.color.white)
        .showImageOnFail(R.color.white)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		return options;
	}
	
	public static DisplayImageOptions getDetailOptions(){
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.cmpy_bg)
        .showImageForEmptyUri(R.drawable.cmpy_bg)
        .showImageOnFail(R.drawable.cmpy_bg)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		return options;
	}
	
	public static DisplayImageOptions getNeedDetailOptions(){
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.img_on_fail)
        .showImageForEmptyUri(R.drawable.img_on_fail)
        .showImageOnFail(R.drawable.img_on_fail)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		return options;
	}
	
	public static DisplayImageOptions getBusinessOptions(){
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.business_icon)
        .showImageForEmptyUri(R.drawable.business_icon)
        .showImageOnFail(R.drawable.business_icon)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		return options;
	}
	public static DisplayImageOptions getUserInfoOptions(){
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.head)
        .showImageForEmptyUri(R.drawable.head)
        .showImageOnFail(R.drawable.head)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
		return options;
	}
}
