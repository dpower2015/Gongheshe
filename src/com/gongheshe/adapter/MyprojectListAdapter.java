package com.gongheshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;

public class MyprojectListAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private Context mContext;
	
	public MyprojectListAdapter(Context context){
		
		
		this.mContext = context;
		inflater = LayoutInflater.from(context);
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final  Holder holder;
		if(convertView == null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.projectlist_item, null);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		
		return convertView;
	}
	private class Holder{
		ImageView icon_project;
		TextView  title_project;
	}
	
	
}
