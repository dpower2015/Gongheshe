package com.gongheshe.fragment;

import com.example.gongheshe.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * @author ZhengZhiying<br>
 * @function  首页
 */
public class HomePageFragment extends BaseFragment {
	
	private static View view;
	private ScrollView sv_home;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.fragment_homepage, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	    sv_home = (ScrollView) view.findViewById(R.id.sv_home);
		
		
		//view = inflater.inflate(R.layout.fragment_homepage, container, false);
		return view;
	}
	public void scrollToBottom(){
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				sv_home.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
	
}
