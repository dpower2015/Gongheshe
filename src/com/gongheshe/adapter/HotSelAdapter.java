package com.gongheshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;

public class HotSelAdapter extends ArrayAdapter<String>{
	
	private LayoutInflater inflater;
	private Context mContext;
	public HotSelAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		inflater = LayoutInflater.from(context);
		mContext=context;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Holder mHolder;
		final HolderTitle mHolderTitle;
		
		System.out.println("####000000000000000000:"+position);
		if(convertView == null){
			if(position==0){
				System.out.println("000000000000000000");
				mHolderTitle =new HolderTitle();	
				convertView = inflater.inflate(R.layout.title_item, null);	
			}
			else {
				mHolder = new Holder();
				convertView = inflater.inflate(R.layout.photo_item, null);
				
				mHolder.good_icon = (ImageView)convertView.findViewById(R.id.good_icon);
				mHolder.good_icon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				
				convertView.setTag(mHolder);
			}
		}else{
			if(position==0){
				mHolderTitle=(HolderTitle) convertView.getTag();
			}else {
				mHolder = (Holder) convertView.getTag();
			}
		}
		return convertView;
	}

	private class Holder{
		ImageView  good_icon;
		TextView good_title;
	}
	
	private class HolderTitle{
		
		TextView goods_title;
		
	}
	
	
}
