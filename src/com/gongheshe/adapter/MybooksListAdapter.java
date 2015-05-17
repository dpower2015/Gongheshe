package com.gongheshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gongheshe.R;

public class MybooksListAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater inflater;
	
	public  MybooksListAdapter(Context context) {
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
		final ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView =inflater.inflate(R.layout.myorderlist_item,null);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		return convertView;
	}

	class ViewHolder{
		
		TextView title;
	}


	
}
