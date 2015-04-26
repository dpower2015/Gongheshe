package com.gongheshe.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void replaceFragment(Fragment fragment, boolean isAddToBackStack){}
	
	public void reSetView(){}
	
	public String theLarge="";
	
	public boolean isSearch=false;
}
