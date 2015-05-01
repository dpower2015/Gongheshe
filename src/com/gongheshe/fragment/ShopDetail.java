package com.gongheshe.fragment;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;


public class ShopDetail  extends BaseFragment{
	private View view;
	private BaseActivity baseActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.shop_detail, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baseActivity.onBackPressed();
			}
		});
		return view;
	}

	
	
}
