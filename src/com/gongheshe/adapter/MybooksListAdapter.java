package com.gongheshe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.OrderMod;
import com.gongheshe.util.AnimateFirstDisplayListener;
import com.gongheshe.util.ImageLoderConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MybooksListAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<OrderMod> datas;
	private ImageLoader imgLoader = ImageLoader.getInstance();
	private onClickListener  itemclickListener;
	public  MybooksListAdapter(Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}
	public void setItemclickListener(onClickListener listener){
		
		
		this.itemclickListener=listener;
		
	}
	public void setBooksList(ArrayList<OrderMod> datas){
		
		this.datas=datas;
		
	}
	public ArrayList<OrderMod> getBookList(){
		
		
		return  datas;
	}
	public void addLists(ArrayList<OrderMod> datas){
		if(this.datas!=null){
			
			this.datas.addAll(datas);
			
		}else {
			
			this.datas=datas;
			
		}
		
		
	}
	public void clear(){
		
		this.datas.clear();
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(datas!=null){
			
			return datas.size();
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

	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		final int curPosition=position;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView =inflater.inflate(R.layout.myorderlist_item,null);
			viewHolder.title=(TextView)convertView.findViewById(R.id.titile);
			viewHolder.decripte=(TextView)convertView.findViewById(R.id.decript);
			viewHolder.detail=(ImageView)convertView.findViewById(R.id.detail_img);
			viewHolder.icon=(ImageView)convertView.findViewById(R.id.icon);
			viewHolder.orderState=(TextView)convertView.findViewById(R.id.order_state);
			viewHolder.applydrawback=(Button)convertView.findViewById(R.id.apply_drawback);
			viewHolder.confirmAccept=(Button)convertView.findViewById(R.id.confirm_accept);
			viewHolder.ordierTime=(TextView)convertView.findViewById(R.id.order_time);
			convertView.setTag(viewHolder);
			viewHolder.applydrawback.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					itemclickListener.onBackward(curPosition);
				}
			});
			viewHolder.confirmAccept.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
						itemclickListener.onConfirmAccept(curPosition);
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					itemclickListener.onClick(curPosition);
				}
			});
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.title.setText(datas.get(position).products.get(0).name);
		viewHolder.decripte.setText(datas.get(position).products.get(0).description);
		viewHolder.orderState.setText(datas.get(position).statusName);
		viewHolder.ordierTime.setText(datas.get(position).createTime);
		imgLoader.displayImage(datas.get(position).products.get(0).androidNote2ImagesMinUrl, // 涓嬭浇鍦板潃
				viewHolder.icon, // ImageView鎺т欢瀹炰緥
				ImageLoderConfig.getOptions(), // 閰嶇疆淇℃伅
				AnimateFirstDisplayListener.getIns()// 鍔犺浇鍔ㄧ敾
				);
		
		
		
		return convertView;
	}

	class ViewHolder{
		TextView title;
		TextView decripte;
		ImageView detail;
		ImageView icon;
		TextView orderState;
		Button applydrawback;
		Button confirmAccept;
		TextView ordierTime;
	}

	public interface onClickListener {
		public void onClick(int  index);
		public void onBackward(int index);
		public void onConfirmAccept(int index);
	}
		
	
}
