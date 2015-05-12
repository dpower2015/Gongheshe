package com.gongheshe.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zy.zh.xListView.XListView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.ShopDetailAdapter;
import com.gongheshe.model.TypeClassMod;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ShopDetail extends BaseFragment {
	private View view;
	private BaseActivity baseActivity;
	private GridView gridview;
	private ShopDetailAdapter adapter;
	protected String tag = "ShopDetail";
	private StringBuffer url = new StringBuffer(GhhConst.HomeSecondActivity);
	private int firstClassId = 1;
	private int secondClassId = 1;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.shop_detail, container, false);
		baseActivity = (BaseActivity) getActivity();
		gridview = (GridView) view.findViewById(R.id.gridview);
		adapter = new ShopDetailAdapter(getActivity());
		gridview.setAdapter(adapter);
		view.findViewById(R.id.ibtn_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						baseActivity.onBackPressed();
					}
				});
		requestWebServer();
		return view;
	}
	

	/**
	 * @author ZhengZhiying
	 * @param firstClassId
	 * @param secondClassId
	 * @return 
	 */
	public ShopDetail setIntentData(int firstClassId,int secondClassId) {
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
					JSONArray arr ;
					arr = jsonObject.getJSONObject("third").getJSONArray("data");
					Gson gson ;
					gson = new Gson();
					TypeClassMod data;
					for(int i=0;i<arr.length();i++){
						data = gson.fromJson(arr.get(i).toString(), TypeClassMod.class);
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

}
