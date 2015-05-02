package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gongheshe.R;

/**
 * @author ZhengZhiying<br>
 * @function 棣栭〉鑿滃崟瑙嗗浘---鎴愬搧鏈ㄤ綔
 */
public class HomeWoodFragment extends BaseFragment implements OnClickListener {

	private View view;
	private View layout_toshow;
	private View layout_menu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_wood, container, false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);
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
			break;
		}
	}
}
