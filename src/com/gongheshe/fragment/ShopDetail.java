package com.gongheshe.fragment;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import zy.zh.xListView.XListView;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.ShopDetailAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;


public class ShopDetail  extends BaseFragment{
	private View view;
	private BaseActivity baseActivity;
	private XListView xListView;
	private ShopDetailAdapter adapter;
	protected String tag = "ShopDetail";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.shop_detail, container, false);
		baseActivity=(BaseActivity)getActivity();
		xListView = (XListView) view.findViewById(R.id.xListView);
//		xListView.setAdapter(adapter);
		view.findViewById(R.id.ibtn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baseActivity.onBackPressed();
			}
		});
		requestWebServer();
		return view;
	}
	private void requestWebServer() {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
//		AsyncHttpResponseHandler
		httpClient.get("http://www.baidu.com", getResponseHandler());
	}

	private AsyncHttpResponseHandler  getResponseHandler(){
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				Log.i(tag, new String(response));
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
			}
		};
		return handler;
	}
	
}
