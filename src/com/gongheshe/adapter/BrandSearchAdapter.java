package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.gongheshe.R;
import com.gongheshe.adapter.BussinessGridViewAdapter.BrandMainListModx2;
import com.gongheshe.adapter.BussinessGridViewAdapter.OnBussItemClickListener;
import com.gongheshe.adapter.BussinessGridViewAdapter.ViewHolder;
import com.gongheshe.javabean.BrandMainListMod;
import com.gongheshe.javabean.BrandSearchMod;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BrandSearchAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<BrandSearchMod> mods;
	private List<BrandSearchModx2> datas;
	private ImageLoader imgLoader;
	private OnBussItemClickListener listener;
	private boolean isEmptyRight = false;

	public interface OnBussItemClickListener {
		public void onBussItemClick(int position);
	}

	public void setOnBussItemClickListener(OnBussItemClickListener listener) {
		this.listener = listener;
	}

	public BrandSearchAdapter(Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(context);
		imgLoader = ImageLoader.getInstance();
		datas = new ArrayList<BrandSearchModx2>();
	}

	@Override
	public int getCount() {
		// if (mods.size() % 2 == 0) {
		// return mods.size() / 2;
		// } else {
		// return mods.size() / 2 + 1;
		// }
		return datas.size();
	}

	public List<BrandSearchMod> getListData() {
		return mods;
	}

	public void cleanDatas() {
		datas.clear();
		isEmptyRight = false;
		notifyDataSetChanged();
	}

	public void appendMods(List<BrandSearchMod> mods) {
		this.mods = mods;
		int i = 0;
		BrandSearchModx2 data;
		while (i < mods.size()) {
			if (isEmptyRight) {
				datas.get(datas.size() - 1).left = mods.get(i);
				i++;
				isEmptyRight = false;
				continue;
			}
			data = new BrandSearchModx2();
			data.left = mods.get(i);
			i++;
			if (i >= mods.size()) {
				datas.add(data);
				isEmptyRight = true;
				break;
			}
			data.right = mods.get(i);
			datas.add(data);
			i++;
		}
	}

	public List<BrandSearchMod> getMods() {
		return mods;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_brand_search, parent,
					false);
			holder.imgLeft = (ImageView) convertView
					.findViewById(R.id.img_left);
			holder.imgRight = (ImageView) convertView
					.findViewById(R.id.img_right);
			holder.txt_price1 = (TextView) convertView.findViewById(R.id.txt_price1);
			holder.txt_price2 = (TextView) convertView.findViewById(R.id.txt_price2);
			holder.txt_num1 = (TextView) convertView.findViewById(R.id.txt_num1);
			holder.txt_num2 = (TextView) convertView.findViewById(R.id.txt_num2);
			holder.layout2 = convertView.findViewById(R.id.layout2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String uri;

		uri = datas.get(position).left.androidNote2ImagesMinUrl;// topImages;
		imgLoader.displayImage(uri, holder.imgLeft);

		if (datas.get(position).right != null) {
			holder.layout2.setVisibility(View.VISIBLE);
			holder.imgRight.setVisibility(View.VISIBLE);
			// middleImages,topImages,iphone56Top,topImages,iphone6plusTop
			// androidNote2Top
			uri = datas.get(position).right.androidNote2ImagesMinUrl;
			imgLoader.displayImage(uri, holder.imgRight);
			holder.txt_price2.setText(datas.get(position).right.minprice+"");
			holder.txt_num2.setText(datas.get(position).right.clickNum+"");
			holder.imgRight.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onBussItemClick(position * 2 + 1);
				}
			});
		} else {
			holder.imgRight.setVisibility(View.INVISIBLE);
			holder.layout2.setVisibility(View.INVISIBLE);
		}
		holder.txt_price1.setText(datas.get(position).left.minprice+"");
		holder.txt_num1.setText(datas.get(position).left.clickNum+"");
		holder.imgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onBussItemClick(position * 2);
			}
		});

		return convertView;
	}

	class ViewHolder {
		ImageView imgLeft;
		ImageView imgRight;
		public TextView txt_price1;
		public TextView txt_num1;
		public TextView txt_price2;
		public TextView txt_num2;
		public View layout2;
	}

	class BrandSearchModx2 {
		public BrandSearchMod left;
		public BrandSearchMod right;
		
	}

}
