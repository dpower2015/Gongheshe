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
import com.nostra13.universalimageloader.core.ImageLoader;

public class BussinessGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<BrandMainListMod> mods;
	private List<BrandMainListModx2> datas;
	private ImageLoader imgLoader;
	private OnBussItemClickListener listener;
	private boolean isEmptyRight = false;

	public interface OnBussItemClickListener {
		public void onBussItemClick(int position);
	}

	public void setOnBussItemClickListener(OnBussItemClickListener listener) {
		this.listener = listener;
	}

	public BussinessGridViewAdapter(Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(context);
		imgLoader = ImageLoader.getInstance();
		datas = new ArrayList<BrandMainListModx2>();
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

	public List<BrandMainListMod> getListData() {
		return mods;
	}

	public void cleanDatas() {
		datas.clear();
	}

	public void appendMods(List<BrandMainListMod> mods) {
		this.mods = mods;
		int i = 0;
		BrandMainListModx2 data;
		while (i < mods.size()) {
			if (isEmptyRight) {
				datas.get(datas.size() - 1).left = mods.get(i);
				i++;
				isEmptyRight = false;
				continue;
			}
			data = new BrandMainListModx2();
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

	public List<BrandMainListMod> getMods() {
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
			convertView = inflater.inflate(R.layout.bussiness_gridview, parent,
					false);
			holder.imgLeft = (ImageView) convertView
					.findViewById(R.id.img_left);
			holder.imgRight = (ImageView) convertView
					.findViewById(R.id.img_right);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String uri;

		uri = datas.get(position).left.middleImages;// topImages;
		imgLoader.displayImage(uri, holder.imgLeft);

		if (datas.get(position).right != null) {
			holder.imgRight.setVisibility(View.VISIBLE);
			uri = datas.get(position).right.middleImages;// topImages;
			imgLoader.displayImage(uri, holder.imgRight);
			holder.imgRight.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onBussItemClick(position * 2 + 1);
				}
			});
		} else {
			holder.imgRight.setVisibility(View.INVISIBLE);
		}

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
	}

	class BrandMainListModx2 {
		public BrandMainListMod left;
		public BrandMainListMod right;
	}

}
