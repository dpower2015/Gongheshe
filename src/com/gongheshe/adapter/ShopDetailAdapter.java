package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.ToolImgLoader;

public class ShopDetailAdapter extends BaseAdapter {

	private Context context;
	public List<ProductMod> datas = new ArrayList<ProductMod>();
	private ToolImgLoader imgLoader = ToolImgLoader.get();
	private LayoutInflater inflater;
	public String title;

	public ShopDetailAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return datas.size()+1;
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
		HolderTitle holder_title;
		Holder holder;
		if (position == 0) {
			convertView = inflater.inflate(R.layout.adpter_shop_detail, null,false);
//			convertView = inflater.inflate(R.layout.title_item, null);
			holder_title = new HolderTitle();
			holder_title.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
//			holder_title.month = (TextView) convertView
//					.findViewById(R.id.month);
//			holder_title.time = (TextView) convertView.findViewById(R.id.time);
//			holder_title.weekday = (TextView) convertView
//					.findViewById(R.id.weekday);
//			holder_title.day.setText("20");
//			holder_title.month.setText("/3月");
//			holder_title.weekday.setText("星期一");
//			holder_title.time.setText("23:15:45发布");
			holder_title.textTitle.setText(title);
			convertView.setTag(holder_title);
		} else {
			position--;
			holder = new Holder();
			convertView = inflater.inflate(R.layout.photo_item, null);
			holder.goods_icon = (ImageView) convertView
					.findViewById(R.id.good_icon);
			holder.goods_dollor = (TextView) convertView
					.findViewById(R.id.goods_dollor);
			holder.goods_title = (TextView) convertView
					.findViewById(R.id.goods_title);
			holder.goods_yuan = (TextView) convertView
					.findViewById(R.id.goods_yuan);
			convertView.setTag(holder);
			imgLoader.show(datas.get(position).androidNote2ImagesMinUrl,
					holder.goods_icon);

			holder.goods_dollor.setText(datas.get(position).saleNum+"");
			holder.goods_yuan.setText("￥"+datas.get(position).minprice);
			holder.goods_title.setText(datas.get(position).name);
		}
		return convertView;
	}

	private class Holder {
		ImageView goods_icon;
		TextView goods_title;
		TextView goods_dollor;
		TextView goods_yuan;

	}

	private class HolderTitle {
		TextView textTitle;
//		TextView day;
//		TextView month;
//		TextView weekday;
	}
}
