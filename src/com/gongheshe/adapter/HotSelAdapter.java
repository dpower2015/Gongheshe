package com.gongheshe.adapter;

import org.solo.waterfall.WaterfallSmartView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
		System.out.println("######position :"+position);
		//if(convertView == null){
			int flag=((HotSelEntity)getItem(position)).flag;
			if(flag==1){
				System.out.println("in.........");
				convertView = inflater.inflate(R.layout.title_item, null);
				
			}else {
				
				mHolder = new Holder();
				convertView = inflater.inflate(R.layout.photo_item, null);
				mHolder.good_icon = (ImageView)convertView.findViewById(R.id.good_icon);
				mHolder.good_icon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
			}
				
				
//				if(flag==1){
//					int height1,width1,height2,width2;
//					 height1=((RelativeLayout)convertView.findViewById(R.id.photo_item)).getMeasuredHeight();
//					 width1=((RelativeLayout)convertView.findViewById(R.id.photo_item)).getMeasuredWidth();
//					 height2=((LinearLayout)convertView.findViewById(R.id.title_bar)).getMeasuredHeight();
//					 width2= ((LinearLayout)convertView.findViewById(R.id.title_bar)).getMeasuredWidth();
//					 System.out.println("photo_item:"+height1+":"+width1);
//					 System.out.println("title_bar:"+height2+":"+width2);
//					
//					
//					 System.out.println("######flag==1");
//					 convertView.findViewById(R.id.photo_item).setVisibility(View.GONE);
//					 convertView.findViewById(R.id.title_bar).setVisibility(View.VISIBLE);
//					 mHolder.good_icon.setVisibility(View.GONE);
//					 
//				}
				
				
				//convertView.setTag(mHolder);
		//}else{
			//mHolder = (Holder) convertView.getTag();
	//	}
		return convertView;
	}

	
	private class Holder{
		ImageView  good_icon;
		TextView good_title;
		//LinearLayout title_bar;
	}
	
	public void add(HotSelEntity object,int weight,int height) {
		// TODO Auto-generated method stub
		super.add(object);
		mWaterfallView.addItem(object, weight, height);
	}
	
}
