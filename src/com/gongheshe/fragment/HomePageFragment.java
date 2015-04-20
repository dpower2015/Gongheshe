package com.gongheshe.fragment;

import com.example.gongheshe.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ZhengZhiying<br>
 * @function  首页
 */
public class HomePageFragment extends BaseFragment {
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_homepage, container, false);
		return view;
	}
}
