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

public class HotSelAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private Context mContext;
	private ArrayList<HotSelMod> hotsetList;
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
		return 10;
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
		final Holder mHolder;
		if(convertView==null){
			
			
			
		}else {
			
			
			
			
		}
		
		if(position==0){
			convertView = inflater.inflate(R.layout.title_item, null);
			
		}else {
			convertView = inflater.inflate(R.layout.photo_item, null);
			
		}
		return convertView;
	}

	
	private class Holder{
		ImageView  good_icon;
		TextView good_title;
	}


	private class holderTitle{
		TextView time;
		TextView day;
		TextView month;
		TextView weekday;
	}
	
}
