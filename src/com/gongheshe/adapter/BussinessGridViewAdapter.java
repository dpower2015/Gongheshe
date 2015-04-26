package com.gongheshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gongheshe.R;

public class BussinessGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	public BussinessGridViewAdapter(Context context) {

		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 12;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
    
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView =inflater.inflate(R.layout.bussiness_gridview,null);
			viewHolder.gridview_item_name = (TextView)convertView.findViewById(R.id.gridview_item_name);

			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.gridview_item_name.setText("test");
		viewHolder.gridview_item_name.setTag(position);
		
		return convertView;
	}
	class ViewHolder{
		
		TextView gridview_item_name;
	}
	

}
