package com.gongheshe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	
	private BaseFragment parent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	//
	public BaseFragment getParentFragmentX(){
		return parent;
	}

	public BaseFragment setParent(BaseFragment parent) {
		this.parent = parent;
		return this;
	}
	
	
}
