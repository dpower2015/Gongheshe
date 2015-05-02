package com.gongheshe.fragment;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.util.LoggerSZ;
import com.googheshe.entity.GhhConst;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyprojectFragment extends BaseFragment{

	
	private View view;
	private BaseActivity baseActivity;
	private AsyncHttpClient client;
	private final String TAG="CopyrightFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.myproject, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("####back");
				baseActivity.onBackPressed();
			}
		});
		//requestData();
		return view;
	}

	
	private void requestData() {
		client = new AsyncHttpClient();

		client.get(getActivity(), GhhConst.GET_COPYRIGHT_CONTENT, null, null,
				new AsyncHttpResponseHandler() {

					public void onFailure(int statusCode, Header[] headers,
							byte[] response, Throwable e) {
						//xListView.stopLoadMore();
						//xListView.stopRefresh();
						LoggerSZ.e(TAG, "访问失败" + e.toString());
						try {
							LoadingDlg.get().hide();
						} catch (Exception ex) {
							LoggerSZ.e(TAG, "" + ex.toString());
						}
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] response) {
						//xListView.stopLoadMore();
						//xListView.stopRefresh();
						onDataReturn(new String(response));
						try {
							LoadingDlg.get().hide();
						} catch (Exception ex) {
							LoggerSZ.e(TAG, "" + ex.toString());
						}
					}
				});

	}
	protected void onDataReturn(String string) {
		System.out.println("onDataReturn :"+string);
		//adapter.fillDataToView(string);
	}
	
	
}
