package com.gongheshe.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.javabean.CityMod;

/**
 * @author ZhengZhiying<br>
 * @function The city list in PopupWindow
 */
public class CityListPopWindow {

	private Context context;
	private View viewPop;
	private ListView listView;
	private CityListAdapter adapter;
	private PopupWindow popupWindow;
	public List<CityMod> citys = new ArrayList<CityMod>();
	private static CityListPopWindow cityListPopWindow;
	private List<OnItemClickListener> onItemClickListener = new ArrayList<OnItemClickListener>();

	public static CityListPopWindow getIns(Context context) {
		if (cityListPopWindow == null) {
			cityListPopWindow = new CityListPopWindow(context);
		}
		return cityListPopWindow;
	}

	private CityListPopWindow(Context context) {
		this.context = context;
		viewPop = LayoutInflater.from(context).inflate(
				R.layout.popwin_citylist, null, false);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		popupWindow = new PopupWindow(viewPop, LayoutParams.MATCH_PARENT,
		/* LayoutParams.MATCH_PARENT */height - 200);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new PaintDrawable());
		// popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		listView = (ListView) viewPop.findViewById(R.id.listview_citys);
		adapter = new CityListAdapter();
		listView.setAdapter(adapter);
		setListViewListener();
	}

	private void setListViewListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for (int i = 0; i < onItemClickListener.size(); i++) {
					onItemClickListener.get(i).onItemClick(parent, view,
							position, id);
				}
			}
		});
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		if (!onItemClickListener.contains(l)) {
			onItemClickListener.add(l);
		}
	}

	private class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return citys.size();
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
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_citylist, parent, false);
				holder = new ViewHolder();
				holder.txt_cityname = (TextView) convertView
						.findViewById(R.id.txt_cityname);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt_cityname.setText(citys.get(position).name);
			return convertView;
		}

		class ViewHolder {
			TextView txt_cityname;
		}
	}

	public void show(View v) {
		popupWindow.showAsDropDown(v, 0, 0);
		notifyDataSetChanged();
	}

	public void dismiss() {
		popupWindow.dismiss();
	}

	public boolean isShowing() {
		return popupWindow.isShowing();
	}

	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

}
