package com.gongheshe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.HotSelMod;
import com.gongheshe.util.AnimateFirstDisplayListener;
import com.gongheshe.util.ImageLoderConfig;
import com.googheshe.entity.GhhConst;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotSelAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private Context mContext;
	private ArrayList<HotSelMod> hotsetList;
	private ImageLoader imgLoader = ImageLoader.getInstance();
	public HotSelAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		mContext=context;
	}
	public void setHotSetList(ArrayList<HotSelMod> lists){
		hotsetList =lists;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(hotsetList!=null){
			
			return hotsetList.size();
			
		}
		return 0;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderTitle holder_title;
		Holder holder;
		if(true){
			
			if(position==0){
				
				 holder_title=new HolderTitle();
				convertView = inflater.inflate(R.layout.title_item, null);
				holder_title.day=(TextView)convertView.findViewById(R.id.day);
				holder_title.month=(TextView)convertView.findViewById(R.id.month);
				holder_title.time=(TextView)convertView.findViewById(R.id.time);
				holder_title.weekday=(TextView)convertView.findViewById(R.id.weekday);
				convertView.setTag(holder_title);
				
			}else {
				holder=new Holder();
				convertView = inflater.inflate(R.layout.photo_item, null);
				holder.goods_icon=(ImageView)convertView.findViewById(R.id.good_icon);
				holder.goods_dollor=(TextView)convertView.findViewById(R.id.goods_dollor);
				holder.goods_title=(TextView)convertView.findViewById(R.id.goods_title);
				holder.goods_yuan=(TextView)convertView.findViewById(R.id.goods_yuan);
				convertView.setTag(holder);
				// #这里明显要使用图片库了。在网上随便down些图片吧
				imgLoader.displayImage(hotsetList.get(position).androidNote2ImagesMinUrl, // 下载地址
						holder.goods_icon, // ImageView控件实例
						ImageLoderConfig.getOptions(), // 配置信息
						AnimateFirstDisplayListener.getIns()// 加载动画
						);
				
				holder.goods_dollor.setText(hotsetList.get(position).martPrice);
				holder.goods_yuan.setText(hotsetList.get(position).minprice);
				holder.goods_title.setText(hotsetList.get(position).name);
			}
			
		}else {
			if(position==0){
				
				holder_title=(HolderTitle)convertView.getTag();
				
			}else {
				
				holder=(Holder)convertView.getTag();
				imgLoader.displayImage(GhhConst.headPicUrl
						+ hotsetList.get(position).androidNote2ImagesMinUrl, // 下载地址
						holder.goods_icon, // ImageView控件实例
						ImageLoderConfig.getOptions(), // 配置信息
						AnimateFirstDisplayListener.getIns()// 加载动画
						);
			}
		}
		return convertView;
	}

	
	private class Holder{
		ImageView  goods_icon;
		TextView goods_title;
		TextView goods_dollor;
		TextView goods_yuan;
		
	}


	private class HolderTitle{
		TextView time;
		TextView day;
		TextView month;
		TextView weekday;
	}
	
}
