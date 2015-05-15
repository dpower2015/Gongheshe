package com.gongheshe.dialog;

import java.util.Arrays;
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
import com.gongheshe.adapter.MystoreListAdapter;

public class CateListPopWindow {

	
	private Context context;
	private View viewPop;
	private ListView listView;
	private CityListAdapter adapter;
	private PopupWindow popupWindow;
	public  List<String> cates =Arrays.asList("结构类","机电类","卫浴类","成品木作","面材类","照明材料","陈设材料");
	private static CateListPopWindow cateListPopWindow;
	private MystoreListAdapter mystoreListAdapter;
	//private List<OnItemClickListener> onItemClickListener = new ArrayList<OnItemClickListener>();

	public static CateListPopWindow getIns(Context context) {
		if (cateListPopWindow == null) {
			cateListPopWindow = new CateListPopWindow(context);
		}
		return cateListPopWindow;
	}

	private CateListPopWindow(Context context) {
		this.context = context;
		viewPop = LayoutInflater.from(context).inflate(
				R.layout.popwin_catelist, null, false);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		popupWindow = new PopupWindow(viewPop, LayoutParams.MATCH_PARENT,height - 200);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new PaintDrawable());
		// popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		listView = (ListView) viewPop.findViewById(R.id.listview_cates);
		adapter = new CityListAdapter();
		listView.setAdapter(adapter);
		setListViewListener();
	}
	public void setStoreListAdapter(MystoreListAdapter adapter){
		
		this.mystoreListAdapter=adapter;
		 
	}
	
	
	private void setListViewListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mystoreListAdapter.update(position);
				//for (int i = 0; i < onItemClickListener.size(); i++) {
				//	onItemClickListener.get(i).onItemClick(parent, view,	position, id);
				//}
			}
		});
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		//if (!onItemClickListener.contains(l)) {
		//	onItemClickListener.add(l);
		//}
	}

	private class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return cates.size();
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
			holder.txt_cityname.setText(cates.get(position));
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
