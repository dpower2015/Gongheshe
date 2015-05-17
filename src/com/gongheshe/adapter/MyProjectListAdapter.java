package com.gongheshe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.gongheshe.R;
import com.gongheshe.javabean.ProjectDataMod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyProjectListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	public ProjectDataMod data = new ProjectDataMod();

	public MyProjectListAdapter(Context context) {
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView txt_title;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_project_list,
					parent, false);
			txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			convertView.setTag(txt_title);
		} else {
			txt_title = (TextView) convertView.getTag();
		}
		txt_title.setText(data.data.get(position).prjName + "\t"
				+ data.data.get(position).createTime);
		return convertView;
	}

}
