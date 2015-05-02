package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;

/**
 * @author ZhengZhiying<br>
 * @function 棣栭〉鑿滃崟瑙嗗浘---闈㈡潗绫�
 */
public class HomeFaceFragment extends BaseFragment  implements OnClickListener{
	
	private View view;
	private View layout_toshow;
	private View layout_menu;
	private BaseActivity baseActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_face_material, container, false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);
		setOnClickListenerTo(R.id.strone);
		setOnClickListenerTo(R.id.tile);
		setOnClickListenerTo(R.id.wallpaper);
		setOnClickListenerTo(R.id.hard_soft_package);
		setOnClickListenerTo(R.id.paint);
		setOnClickListenerTo(R.id.gyp_line);
		setOnClickListenerTo(R.id.glasses);
		setOnClickListenerTo(R.id.special_material);
		baseActivity=(BaseActivity)getActivity();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_toshow:
			// 鏄剧ず瀛愯彍鍗�
			if(layout_menu.getVisibility() == View.VISIBLE){
				layout_menu.setVisibility(View.GONE);
				layout_toshow.setBackgroundResource(R.color.white);
			}else{
				layout_toshow.setBackgroundResource(R.drawable.menu_bg);
				layout_menu.setVisibility(View.VISIBLE);
			}
			break;
		default:
			ShopDetail shopDetail =new ShopDetail();
			baseActivity.replaceFragment(shopDetail, true);
			break;
		}
	}
	private void setOnClickListenerTo(int resId) {
		view.findViewById(resId).setOnClickListener(this);
	}
}
