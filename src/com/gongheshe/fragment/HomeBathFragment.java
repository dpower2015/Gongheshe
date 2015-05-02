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
 * @function 棣栭〉鑿滃崟瑙嗗浘---鍗荡绫�
 */
public class HomeBathFragment extends BaseFragment implements OnClickListener {

	private View view;
	private View layout_toshow;
	private View layout_menu;
	private BaseActivity baseActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_bath, container, false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);
		baseActivity=(BaseActivity)getActivity();
		
		view.findViewById(R.id.pedestal_toilet).setOnClickListener(this);
		view.findViewById(R.id.face_pot).setOnClickListener(this);
		view.findViewById(R.id.bathtab).setOnClickListener(this);
		view.findViewById(R.id.sprinkler).setOnClickListener(this);
		view.findViewById(R.id.floor_drain).setOnClickListener(this);
		view.findViewById(R.id.hanging_drop).setOnClickListener(this);
		view.findViewById(R.id.all_shower_room).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_toshow:
			// 鏄剧ず瀛愯彍鍗�
			if (layout_menu.getVisibility() == View.VISIBLE) {
				layout_toshow.setBackgroundResource(R.color.white);
				layout_menu.setVisibility(View.GONE);
			} else {
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

}
