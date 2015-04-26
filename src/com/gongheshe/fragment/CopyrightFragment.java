package com.gongheshe.fragment;

import com.example.gongheshe.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CopyrightFragment extends BaseFragment{

	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.copyright_notice, container, false);
		return view;
	}

	
	
}
