package com.gongheshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.ProjectDataMod;

public class MyProjectListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	public ProjectDataMod data = new ProjectDataMod();
	private OnItemClick onItemClick;

	public interface OnItemClick {
		public void onItemClick(int position);

		public void onItemDeleteClick(int position);
	}

	public MyProjectListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	@Override
	public int getCount() {
		if (data.data == null) {
			return 0;
		}
		return data.data.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_project_list,
					parent, false);
			holder = new ViewHolder();
			holder.txt_title = (TextView) convertView
					.findViewById(R.id.txt_title);
			holder.img_del = (ImageView) convertView.findViewById(R.id.img_del);
			holder.img_del.setImageResource(R.drawable.delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_title.setText(data.data.get(position).prjName);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onItemClick.onItemClick(position);
			}
		});
		holder.img_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onItemClick.onItemDeleteClick(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView txt_title;
		ImageView img_del;
	}

}
