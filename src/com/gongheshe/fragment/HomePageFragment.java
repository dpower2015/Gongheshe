package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;

import com.example.gongheshe.R;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.javabean.CityMod;
import com.gongheshe.model.TypeClassMod;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author ZhengZhiying<br>
 * @function 首页
 */
public class HomePageFragment extends BaseFragment implements
		View.OnClickListener {

	protected static final String tag = "HomePageFragment";
	private static View view;
	private ScrollView sv_home;
	private CityListPopWindow cityListPopWindow;
	private Button bt_showCity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.fragment_homepage, container,
					false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}
		sv_home = (ScrollView) view.findViewById(R.id.sv_home);
		bt_showCity = (Button) view.findViewById(R.id.bt_to_show_citys);
		view.findViewById(R.id.bt_to_show_citys).setOnClickListener(this);
		cityListPopWindow = CityListPopWindow.getIns(getActivity());
		// view = inflater.inflate(R.layout.fragment_homepage, container,
		// false);
		requestWebServer();
//		setListenerListView();
		return view;
	}

	private void setListenerListView() {
		cityListPopWindow.setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(isVisible()){
							cityListPopWindow.dismiss();
							bt_showCity.setText(cityListPopWindow.citys
									.get(position).name);
						}
						
					}
				});
	}

	public void scrollToBottom() {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				sv_home.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_to_show_citys:
			if (cityListPopWindow.isShowing()) {
				cityListPopWindow.dismiss();
			} else {
				cityListPopWindow.show(v);
			}
			break;

		default:
			break;
		}
	}

	private void requestWebServer() {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		String url = GhhConst.getcitys;
		httpClient.get(url.toString(), getResponseHandler());
	}

	private AsyncHttpResponseHandler getResponseHandler() {
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				Log.i(tag, new String(response));
				try {
					JSONObject jsonObject;
					jsonObject = new JSONObject(new String(response));
					JSONArray arr;
					arr = jsonObject.getJSONObject("citys")
							.getJSONArray("data");
					Gson gson;
					gson = new Gson();
					CityMod data;
					cityListPopWindow.citys.clear();
					for (int i = 0; i < arr.length(); i++) {
						data = gson.fromJson(arr.get(i).toString(),
								CityMod.class);
						cityListPopWindow.citys.add(data);
					}
					if (cityListPopWindow.isShowing()) {
						cityListPopWindow.notifyDataSetChanged();
					}
					// adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
			}
		};
		return handler;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setListenerListView();
	}

}
