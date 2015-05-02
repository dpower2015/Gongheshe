package com.gongheshe.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.gongheshe.context.SZApplication;

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
	
	//#因为映射需要在设置视图之后所以重写是最好的选择
		@Override
		public void setContentView(int layoutResID) {
			super.setContentView(layoutResID);
			getApplicationSZ().injectView(this);
		}

		@Override
		public void setContentView(View view) {
			super.setContentView(view);
			getApplicationSZ().injectView(this);
		}

		public SZApplication getApplicationSZ() {
			return (SZApplication) getApplication();
		}
}
