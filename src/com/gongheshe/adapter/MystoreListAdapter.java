package com.gongheshe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.AnimateFirstDisplayListener;
import com.gongheshe.util.ImageLoderConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MystoreListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context mContext;
	private ArrayList<ProductMod> mystoreList;
	private ImageLoader imgLoader = ImageLoader.getInstance();
	private onClickListener  itemclickListener;
	private int count;
	public MystoreListAdapter(Context context){
		
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	public void setItemclickListener(onClickListener listener){
		
		
		this.itemclickListener=listener;
		
	}
	public void setMystoreList(ArrayList<ProductMod> lists){
		if(mystoreList!=null){
			mystoreList.clear();
		}
		mystoreList =lists;
		 if((mystoreList.size()%2)!=0){
			 
			 count=mystoreList.size()/2+1;
		 }else count=mystoreList.size()/2;
		 
	}
	public  ArrayList<ProductMod> getMystoreList(){
		
		
			return mystoreList;
	}
	public void addList(ArrayList<ProductMod> lists){
		if(mystoreList!=null){
			mystoreList.addAll(lists);
		}else mystoreList =lists;
		
		if((mystoreList.size()%2)!=0){
			 
			 count=mystoreList.size()/2+1;
		 }else count=mystoreList.size()/2;
	}
	public void clearList(){
		
		if(mystoreList!=null){
			mystoreList.clear();
		}
		count=0;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
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
		final  int curPosition=position*2;
		boolean flag=false;
		if(convertView == null){
			holder=new Holder();
			convertView = inflater.inflate(R.layout.productlist_item, null);
			holder.product_icon=(ImageView)convertView.findViewById(R.id.product_icon);
			holder.product_dollor=(TextView)convertView.findViewById(R.id.product_dollor);
			holder.product_title=(TextView)convertView.findViewById(R.id.product_title);
			holder.product_yuan=(TextView)convertView.findViewById(R.id.product_yuan);
			holder.rightView=(RelativeLayout)convertView.findViewById(R.id.rightView);
			holder.leftView=(RelativeLayout)convertView.findViewById(R.id.leftView);
			holder.product_icon1=(ImageView)convertView.findViewById(R.id.product_icon1);
			holder.product_dollor1=(TextView)convertView.findViewById(R.id.product_dollor1);
			holder.product_title1=(TextView)convertView.findViewById(R.id.product_title1);
			holder.product_yuan1=(TextView)convertView.findViewById(R.id.product_yuan1);
			convertView.setTag(holder);
			
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		
		holder.product_dollor.setText(mystoreList.get(position*2).clickNum);
		holder.product_yuan.setText("￥"+mystoreList.get(position*2).minprice);
		holder.product_title.setText(mystoreList.get(position*2).name);
		imgLoader.displayImage(mystoreList.get(position*2).androidNote2ImagesMinUrl, // 涓嬭浇鍦板潃
				holder.product_icon, // ImageView鎺т欢瀹炰緥
				ImageLoderConfig.getOptions(), // 閰嶇疆淇℃伅
				AnimateFirstDisplayListener.getIns()// 鍔犺浇鍔ㄧ敾
				);
		
		holder.leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				itemclickListener.onClick(curPosition);
			}
		});
		if(position<(count-1)){
			
			flag=true;
			
		}else {
			if((mystoreList.size()%2)==0){
				flag=true;
				//holder.rightView.setVisibility(View.VISIBLE);
			}else {
				flag=false;
				holder.rightView.setVisibility(View.INVISIBLE);
				
			}
		}
		if(flag){
			holder.product_dollor1.setText(mystoreList.get(position*2+1).clickNum);
			holder.product_yuan1.setText("￥"+mystoreList.get(position*2+1).minprice);
			holder.product_title1.setText(mystoreList.get(position*2+1).name);
			imgLoader.displayImage(mystoreList.get(position*2+1).androidNote2ImagesMinUrl, // 涓嬭浇鍦板潃
					holder.product_icon1, // ImageView鎺т欢瀹炰緥
					ImageLoderConfig.getOptions(), // 閰嶇疆淇℃伅
					AnimateFirstDisplayListener.getIns()// 鍔犺浇鍔ㄧ敾
				);
			holder.rightView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					itemclickListener.onClick(curPosition+1);
				}
			});
		}
		return convertView;
	}
	private class Holder{
		ImageView product_icon;
		TextView  product_title;
		TextView  product_dollor;
		TextView  product_yuan;
		RelativeLayout leftView;
		RelativeLayout rightView;
		ImageView product_icon1;
		TextView  product_title1;
		TextView  product_dollor1;
		TextView  product_yuan1;
	}
	public interface onClickListener {
		public void onClick(int  index);
	}
}
