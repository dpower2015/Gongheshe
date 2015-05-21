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
import com.gongheshe.util.LoggerSZ;

/**
 * @author ZhengZhiying<br>
 * @function HomeBathFragment
 */
public class HomeBathFragment extends BaseFragment implements OnClickListener {

	private View view;
	private View layout_toshow;
	private View layout_menu;
	private BaseActivity baseActivity;
	private int[] resId = { R.id.bt_bath1, R.id.bt_bath2, R.id.bt_bath3,
			R.id.bt_bath4, R.id.bt_bath5, R.id.bt_bath6, R.id.bt_bath7 };
	private BrandClassFragment parentF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_bath, container, false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);
		baseActivity = (BaseActivity) getActivity();

		for (int i = 0; i < resId.length; i++) {
			view.findViewById(resId[i]).setOnClickListener(this);
		}
		if (getParentFragmentX() != null) {
			if (getParentFragmentX() instanceof BrandClassFragment) {
				parentF = (BrandClassFragment) getParentFragmentX();
			}
		}
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
				layout_toshow.setBackgroundResource(R.color.white);
				layout_menu.setVisibility(View.GONE);
			} else {
				layout_toshow.setBackgroundResource(R.drawable.menu_bg);
				layout_menu.setVisibility(View.VISIBLE);
			}
			break;
		default:
			ShopDetail shopDetail;
//			int cityId = 1;
			for (int i = 0; i < resId.length; i++) {
				if (resId[i] == v.getId()) {
					if (parentF != null) {
						parentF.setOnBrankClickListener(3, i + 1);
					} else {
					shopDetail = new ShopDetail().setIntentData(3, i + 1,
							cityMod, ((Button) view.findViewById(v.getId()))
									.getText().toString());
					baseActivity.replaceFragment(shopDetail, true);}
					break;
				}
			}

			break;
		}
	}

}
