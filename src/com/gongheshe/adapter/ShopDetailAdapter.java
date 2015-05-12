package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.gongheshe.R;
import com.gongheshe.model.TypeClassMod;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShopDetailAdapter extends BaseAdapter {

	private Context context;
	public List<TypeClassMod> datas = new ArrayList<TypeClassMod>();

	public ShopDetailAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_detail, parent, false);
			holder = new ViewHolder();
			holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_content.setText(datas.get(position).toString());
		return convertView;
	}
	
	
	class ViewHolder{
		TextView txt_content;
	}
}
