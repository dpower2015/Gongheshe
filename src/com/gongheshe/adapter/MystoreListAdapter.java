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
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.AnimateFirstDisplayListener;
import com.gongheshe.util.ImageLoderConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MystoreListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context mContext;
	private ArrayList<ProductMod> hotsetList;
	private ImageLoader imgLoader = ImageLoader.getInstance();
	public MystoreListAdapter(Context context){
		
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	public void setMystoreList(ArrayList<ProductMod> lists){
		if(hotsetList!=null){
			hotsetList.clear();
		}
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
	public void update(int id){
		
		
		System.out.println("###MystoreList update..");
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final  Holder holder;
		//if(convertView == null){
		if(true){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.photo_item, null);
			holder.goods_icon=(ImageView)convertView.findViewById(R.id.good_icon);
			holder.goods_dollor=(TextView)convertView.findViewById(R.id.goods_dollor);
			holder.goods_title=(TextView)convertView.findViewById(R.id.goods_title);
			holder.goods_yuan=(TextView)convertView.findViewById(R.id.goods_yuan);
			convertView.setTag(holder);
			// #杩欓噷鏄庢樉瑕佷娇鐢ㄥ浘鐗囧簱浜嗐�傚湪缃戜笂闅忎究down浜涘浘鐗囧惂
			imgLoader.displayImage(hotsetList.get(position).androidNote2ImagesMinUrl, // 涓嬭浇鍦板潃
					holder.goods_icon, // ImageView鎺т欢瀹炰緥
					ImageLoderConfig.getOptions(), // 閰嶇疆淇℃伅
					AnimateFirstDisplayListener.getIns()// 鍔犺浇鍔ㄧ敾
					);
			
			holder.goods_dollor.setText(hotsetList.get(position).minprice);
			holder.goods_yuan.setText("￥"+hotsetList.get(position).martPrice);
			holder.goods_title.setText(hotsetList.get(position).name);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		return convertView;
	}
	private class Holder{
		ImageView  goods_icon;
		TextView goods_title;
		TextView goods_dollor;
		TextView goods_yuan;
		
	}
}
