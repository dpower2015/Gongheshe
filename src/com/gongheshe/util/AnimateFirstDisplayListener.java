/**
 * 
 */
/**
 *
 * @author ZhengZhiying
 *
 * @Funtion
 * 
 *
 */
package com.gongheshe.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


/**
 *
 * @author ZhengZhiying
 *
 * @Funtion	ImagerLoader加载出来的动画效果
 * 
 *
 */
public class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

	private static AnimateFirstDisplayListener instance;
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (loadedImage != null) {
			ImageView imageView = (ImageView) view;
			boolean firstDisplay = !displayedImages.contains(imageUri);
			if (firstDisplay) {
				FadeInBitmapDisplayer.animate(imageView, 500);
				displayedImages.add(imageUri);
			}
		}
	}
	
	private AnimateFirstDisplayListener(){
		//##单例
	}
	
	public static AnimateFirstDisplayListener getIns(){
		if(instance == null){
			instance = new AnimateFirstDisplayListener();
		}
		return instance;
	}
	
}