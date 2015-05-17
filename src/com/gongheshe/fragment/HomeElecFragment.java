package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.javabean.CityMod;

/**
 * @author ZhengZhiying<br>
 */
public class HomeElecFragment extends BaseFragment implements OnClickListener {

	private View view;
	private View layout_toshow;
	private View layout_menu;
	private BaseActivity baseActivity;
	private int[] resIds = { R.id.bt_elec1, R.id.bt_elec2, R.id.bt_elec3,
			R.id.bt_elec4, R.id.bt_elec5, R.id.bt_elec6, R.id.bt_elec7,
			R.id.bt_elec8, R.id.bt_elec9, R.id.bt_elec10, R.id.bt_elec11,
			R.id.bt_elec12 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_electron, container,
				false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);

		for(int i=0;i<resIds.length;i++){
			setOnClickListenerTo(resIds[i]);
		}
		baseActivity = (BaseActivity) getActivity();
		return view;

	}
	
	private CityMod cityMod;
	public void setCityMod(CityMod cityMod){
		this.cityMod = cityMod;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_toshow:
			if (layout_menu.getVisibility() == View.VISIBLE) {
				layout_menu.setVisibility(View.GONE);
				layout_toshow.setBackgroundResource(R.color.white);
			} else {
				layout_toshow.setBackgroundResource(R.drawable.menu_bg);
				layout_menu.setVisibility(View.VISIBLE);
			}
			break;
		default:
			ShopDetail shopDetail;
//			int cityId = 1;
			for(int i=0;i<resIds.length;i++){
				if(resIds[i] == v.getId()){
					shopDetail = new ShopDetail().setIntentData(2, i+1,cityMod, ((Button) view.findViewById(v.getId()))
							.getText().toString());
					baseActivity.replaceFragment(shopDetail, true);
					break;
				}
			}
			break;
		}
	}

	private void setOnClickListenerTo(int resId) {
		view.findViewById(resId).setOnClickListener(this);
	}

}
