package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.gongheshe.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BrandSecondVpageAdapter extends PagerAdapter {

	private Context context;
	private List<ImageView> imgs = new ArrayList<ImageView>();
	private List<String> urls = new ArrayList<String>();
	private ImageLoader imgLoader;

	public BrandSecondVpageAdapter(Context context) {
		super();
		this.context = context;
		ImageView img;
		img = new ImageView(context);
		img.setScaleType(ScaleType.CENTER_CROP);
		img.setImageResource(R.color.white);
		imgs.add(img);
		img = new ImageView(context);
		img.setScaleType(ScaleType.CENTER_CROP);
		img.setImageResource(R.color.white);
		imgs.add(img);
		img = new ImageView(context);
		img.setScaleType(ScaleType.CENTER_CROP);
		img.setImageResource(R.color.white);
		imgs.add(img);
		imgLoader = ImageLoader.getInstance();
	}

	public void setUrlList(List<String> urls) {
		for (int i = 0; i < urls.size(); i++) {
			imgLoader.displayImage(urls.get(i), imgs.get(i));
		}
	}

	// @Override
	// public int getCount() {
	// return 3;
	// }
	//
	// @Override
	// public boolean isViewFromObject(View view, Object object) {
	// return false;
	// }
	//
	// @Override
	// public Object instantiateItem(ViewGroup container, int position) {
	// ImageView img = new ImageView(context);
	// img.setImageResource(R.drawable.test_user_image);
	// return img;
	// }

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(imgs.get(position));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.size();
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(imgs.get(position));
		return imgs.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
