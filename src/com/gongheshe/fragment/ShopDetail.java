package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.ShopDetailAdapter;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.model.TypeClassMod;
import com.gongheshe.util.PullStagGridViewUT;
import com.gongheshe.util.PullStagGridViewUT.OnStagGridViewListener;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ShopDetail extends BaseFragment implements View.OnClickListener {
	private View view;
	private BaseActivity baseActivity;
	private PullToRefreshStaggeredGridView stag_gridview;
	private ShopDetailAdapter adapter;
	protected String tag = "ShopDetail";
	private StringBuffer url = new StringBuffer(GhhConst.HomeSecondActivity);
	private int firstClassId = 1;
	private int secondClassId = 1;

	private CityListPopWindow cityListPopWindow;
	private Button bt_showCity;
	private PullStagGridViewUT stagGridViewUT;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.shop_detail, container, false);
		baseActivity = (BaseActivity) getActivity();
		stag_gridview = (PullToRefreshStaggeredGridView) view
				.findViewById(R.id.stag_gridview);
		stagGridViewUT = new PullStagGridViewUT(getActivity(), stag_gridview);
		adapter = new ShopDetailAdapter(getActivity());
		stagGridViewUT.setAdapter(adapter);
		setListenerStagGridViewUT();
		bt_showCity = (Button) view.findViewById(R.id.bt_to_show_citys);
		bt_showCity.setOnClickListener(this);
		view.findViewById(R.id.ibtn_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						baseActivity.onBackPressed();
					}
				});
		requestWebServer();
		setListenerCityListPopWindow();

		return view;
	}

	private void setListenerStagGridViewUT() {
		stagGridViewUT.setGridViewListener(new OnStagGridViewListener() {

			@Override
			public void onRefreshStart() {
			}

			@Override
			public void onItemClick(int position) {
			}
		});
	}

	private void setListenerCityListPopWindow() {
		cityListPopWindow = CityListPopWindow.getIns(getActivity());
		cityListPopWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isVisible()) {
					cityListPopWindow.dismiss();
					bt_showCity.setText(cityListPopWindow.citys.get(position).name);
				}
			}
		});
	}

	/**
	 * @author ZhengZhiying
	 * @param firstClassId
	 * @param secondClassId
	 * @return
	 */
	public ShopDetail setIntentData(int firstClassId, int secondClassId) {
		this.firstClassId = firstClassId;
		this.secondClassId = secondClassId;
		return this;
	}

	private void requestWebServer() {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		// AsyncHttpResponseHandler
		url.append("?");
		url.append("pagesize=" + 20);
		url.append("&");
		url.append("pagenumber=" + 1);
		url.append("&");
		url.append("firstClassId=" + firstClassId);
		url.append("&");
		url.append("secondClassId=" + secondClassId);
		// httpClient.get("http://www.baidu.com", getResponseHandler());
		Log.i(tag, "访问：" + url);
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
					arr = jsonObject.getJSONObject("third")
							.getJSONArray("data");
					Gson gson;
					gson = new Gson();
					TypeClassMod data;
					for (int i = 0; i < arr.length(); i++) {
						data = gson.fromJson(arr.get(i).toString(),
								TypeClassMod.class);
						adapter.datas.add(data);
					}
					adapter.notifyDataSetChanged();
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

}
