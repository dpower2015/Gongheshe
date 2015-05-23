package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.BrandMainListMod;
import com.gongheshe.javabean.BrandSecondMod;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author ZhengZhiying<br>
 * @function use in BrandSecondFragment.java
 */
public class BrandSecondListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<BrandSecondMod> datas;
	private List<BrandSecondMod2> datas2;
	private ImageLoader imgLoader;
	private OnBussItemClickListener listener;
	private boolean isEmptyRight = false;
	private BrandMainListMod brandMainListMod;

	public interface OnBussItemClickListener {
		public void onBussItemClick(int position);
	}

	public void setOnBussItemClickListener(OnBussItemClickListener listener) {
		this.listener = listener;
	}

	public BrandSecondListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		datas = new ArrayList<BrandSecondMod>();
		datas2 = new ArrayList<BrandSecondMod2>();
		imgLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return datas2.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void seBrandMainListMod(BrandMainListMod brandMainListMod) {
		this.brandMainListMod = brandMainListMod;
	}

	public void cleanListData() {
		datas.clear();
		datas2.clear();
		isEmptyRight = false;
		notifyDataSetChanged();
	}

	public void appendData(List<BrandSecondMod> datas) {
		this.datas = datas;
		int i = 0;
		BrandSecondMod2 data;
		while (i < datas.size()) {
			data = new BrandSecondMod2();
			if (isEmptyRight) {
				datas2.get(datas2.size() - 1).mod2 = datas.get(i);
				i++;
				isEmptyRight = false;
				continue;
			}
			data.mod1 = datas.get(i);
			i++;
			if (i >= datas.size()) {
				this.datas2.add(data);
				isEmptyRight = true;
				break;
			}
			data.mod2 = datas.get(i);
			datas2.add(data);
			i++;
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final int index = position - 1;
		if (convertView == null) {
			int resource;
			resource = R.layout.adapter_brandsecondlist;
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.initView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.layout_content.setVisibility(View.GONE);
			holder.img_title.setVisibility(View.VISIBLE);
			imgLoader.displayImage(//brandMainListMod.androidNote3Top,
					brandMainListMod.middleImages,
					holder.img_title);
		} else {
			holder.layout_content.setVisibility(View.VISIBLE);
			holder.img_title.setVisibility(View.GONE);
			String url;
			url = datas2.get(index).mod1.androidNote2ImagesMinUrl;
			imgLoader.displayImage(url, holder.img_left);
			holder.txt_num1.setText(datas2.get(index).mod1.saleNum);
			holder.txt_price1.setText("￥"+datas2.get(index).mod1.minprice);
			if (null != datas2.get(index).mod2) {
				holder.layout2.setVisibility(View.VISIBLE);
				url = datas2.get(index).mod2.androidNote2ImagesMinUrl;
				imgLoader.displayImage(url, holder.img_right);
				holder.txt_num2.setText(datas2.get(index).mod2.saleNum);
				holder.txt_price2.setText("￥"+datas2.get(index).mod2.minprice);
				holder.img_right.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						listener.onBussItemClick(index * 2 + 1);
					}
				});
			} else {
				holder.layout2.setVisibility(View.INVISIBLE);
			}
			holder.img_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onBussItemClick(index * 2);
				}
			});
		}

		return convertView;
	}

	class ViewHolder {
		ImageView img_title;
		ImageView img_left;
		ImageView img_right;
		TextView txt_num1;
		TextView txt_price1;
		TextView txt_num2;
		TextView txt_price2;
		View layout_content;
		View layout1;
		View layout2;

		public void initView(View v) {
			img_title = (ImageView) v.findViewById(R.id.img_title);
			img_left = (ImageView) v.findViewById(R.id.img_left);
			img_right = (ImageView) v.findViewById(R.id.img_right);
			txt_num1 = (TextView) v.findViewById(R.id.txt_num1);
			txt_price1 = (TextView) v.findViewById(R.id.txt_price1);
			txt_num2 = (TextView) v.findViewById(R.id.txt_num2);
			txt_price2 = (TextView) v.findViewById(R.id.txt_price2);
			layout_content = v.findViewById(R.id.layout_content);
			layout1 = v.findViewById(R.id.layout1);
			layout2 = v.findViewById(R.id.layout2);
		}
	}

	class BrandSecondMod2 {
		public BrandSecondMod mod1;
		public BrandSecondMod mod2;
	}

}
