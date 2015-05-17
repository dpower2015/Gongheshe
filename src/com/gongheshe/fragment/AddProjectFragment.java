package com.gongheshe.fragment;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class AddProjectFragment extends BaseFragment implements OnClickListener{

	private View view;
	private BaseActivity baseActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_add_project, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.save_project).setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.ibtn_back){
			baseActivity.onBackPressed();
		}else if(id==R.id.save_project){
			
			
		}
	}

	
	
	
	
}
