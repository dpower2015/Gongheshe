package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;

public class MineFragment extends BaseFragment implements OnClickListener{
	
	View view;
	private BaseActivity baseActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		view.findViewById(R.id.copyright_id).setOnClickListener(this);
		view.findViewById(R.id.modify_pw).setOnClickListener(this);
		view.findViewById(R.id.modify_head_img).setOnClickListener(this);
		view.findViewById(R.id.mybooks).setOnClickListener(this);
		view.findViewById(R.id.mystore).setOnClickListener(this);
		baseActivity=(BaseActivity)getActivity();
		return view;
	}
	@Override 
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.copyright_id:
			CopyrightFragment cprightFragment = new CopyrightFragment();
			baseActivity.replaceFragment(cprightFragment, true);
			break;
		case R.id.modify_head_img:
			break;
		case R.id.modify_pw:
			ModifypwFragment modifyFragment = new ModifypwFragment();
			baseActivity.replaceFragment(modifyFragment, true);
			break;
		case R.id.mybooks:
			MybooksFragment mybooksFragment =new MybooksFragment();
			baseActivity.replaceFragment(mybooksFragment, true);
			break;
		case R.id.mystore:
			MystoreFragment mystoreFragment =new MystoreFragment();
			baseActivity.replaceFragment(mystoreFragment, true);
			break;
		default:
			break;
		 
		}
		
	}

}
