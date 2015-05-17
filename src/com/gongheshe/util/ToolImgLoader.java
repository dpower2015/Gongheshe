package com.gongheshe.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author ZhengZhiying<br>
 * @function 
 */
public class ToolImgLoader {

	private static ToolImgLoader toolImgLoader;
	private ImageLoader imgLoader = ImageLoader.getInstance();

	private ToolImgLoader() {

	}

	public void show(String url, ImageView imgView) {
		imgLoader.displayImage(url, imgView, ImageLoderConfig.getOptions(),
				AnimateFirstDisplayListener.getIns());
	}

	public static ToolImgLoader get() {
		if (toolImgLoader == null) {
			toolImgLoader = new ToolImgLoader();
		}
		return toolImgLoader;
	}
}
