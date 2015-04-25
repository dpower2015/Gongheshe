package com.gongheshe.adapter;

import org.solo.waterfall.WaterfallSmartView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.googheshe.entity.HotSelEntity;

public class HotSelAdapter extends ArrayAdapter<HotSelEntity>{
	
	private LayoutInflater inflater;
	private Context mContext;
	private WaterfallSmartView mWaterfallView;
	public HotSelAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		inflater = LayoutInflater.from(context);
		mContext=context;
		
	}
	public void setWaterfallView(WaterfallSmartView view){
		
		mWaterfallView =view;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Holder mHolder;
		if(convertView == null){
				mHolder = new Holder();
				convertView = inflater.inflate(R.layout.photo_item, null);
				
				mHolder.good_icon = (ImageView)convertView.findViewById(R.id.good_icon);
				mHolder.title=(TextView)(convertView).findViewById(R.id.title);
				if(position==0){
					
					mHolder.title.setVisibility(View.VISIBLE);
				}
				mHolder.good_icon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				convertView.setTag(mHolder);
		}else{
			mHolder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	
	private class Holder{
		ImageView  good_icon;
		TextView good_title;
		TextView title;
	}
	
	public void add(HotSelEntity object,int weight,int height) {
		// TODO Auto-generated method stub
		super.add(object);
		mWaterfallView.addItem(object, weight, height);
	}
	
}
