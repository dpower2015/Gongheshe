package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.javabean.CityMod;

/**
 * @author ZhengZhiying<br>
 */
public class HomeStructureFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private View layout_toshow;
	private View layout_menu;
	private BaseActivity baseActivity;
	private int[] resIds = { R.id.bt_structure_1, R.id.bt_structure_2,
			R.id.bt_structure_3, R.id.bt_structure_4 };
	private CityMod cityMod;
	private BrandClassFragment parentF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_home_structure, container,
				false);
		layout_toshow = view.findViewById(R.id.layout_toshow);
		layout_menu = view.findViewById(R.id.layout_menu);
		layout_menu.setVisibility(View.GONE);
		layout_toshow.setOnClickListener(this);
		for (int i = 0; i < resIds.length; i++) {
			view.findViewById(resIds[i]).setOnClickListener(this);
		}
		baseActivity = (BaseActivity) getActivity();

		if (getParentFragmentX() != null) {
			if (getParentFragmentX() instanceof BrandClassFragment) {
				parentF = (BrandClassFragment) getParentFragmentX();
			}
		}

		return view;
	}

	public void setCityMod(CityMod cityMod) {
		this.cityMod = cityMod;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_toshow:
			// to show the item
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
			for (int i = 0; i < resIds.length; i++) {
				if (resIds[i] == v.getId()) {
					if (parentF != null) {
						parentF.setOnBrankClickListener(1, i + 1);
					} else {
						shopDetail = new ShopDetail().setIntentData(1, i + 1,
								cityMod,
								((Button) view.findViewById(v.getId()))
										.getText().toString());
						baseActivity.replaceFragment(shopDetail, true);
					}
					break;
				}
			}
			break;
		}
	}
	//
	// private void setOnClickListenerTo(int resId) {
	// view.findViewById(resId).setOnClickListener(this);
	// }

}
