package com.gongheshe.fragment;

import zy.zh.xListView.XListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.MybooksListAdapter;

public class MybooksFragment extends BaseFragment{
	private View view;
	private BaseActivity baseActivity;
	private XListView xListView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mybooks, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baseActivity.onBackPressed();
			}
		});
		xListView=(XListView)view.findViewById(R.id.xListView);
		xListView.setAdapter(new MybooksListAdapter(baseActivity));
		
		return view;
	}

}
