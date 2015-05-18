package com.gongheshe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.BussinessGridViewAdapter;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.util.ToastUtil;

/**
 * @author ZhengZhiying<br>
 * @function 品牌
 */
public class BrandFragment extends BaseFragment implements OnClickListener {

	private View view;
	private GridView mBussinesList;
	private BussinessGridViewAdapter mBussinesListAdapter;
	private Button bt_showCity;
	private CityListPopWindow cityListPopWindow;
	private BaseActivity baseActivity;
	private BrandClassFragment classFragment;
	private FrameLayout frame_class;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = inflater.inflate(R.layout.my_collect, container, false);
		view = inflater.inflate(R.layout.fragment_brand, container, false);
		mBussinesList = (GridView) view.findViewById(R.id.merchant_list);
		mBussinesListAdapter = new BussinessGridViewAdapter(getActivity());
		mBussinesList.setAdapter(mBussinesListAdapter);
		bt_showCity = (Button) view.findViewById(R.id.bt_brand_show_city);
		bt_showCity.setOnClickListener(this);
		setListenerCityListPopWindow();
		view.findViewById(R.id.bt_class).setOnClickListener(this);
		baseActivity = (BaseActivity) getActivity();
		frame_class = (FrameLayout) view.findViewById(R.id.frame_class);
		frame_class.setVisibility(View.GONE);
		mBussinesList.setVisibility(View.VISIBLE);
		return view;
	}

	private void initBrandClassFragment() {
		classFragment = new BrandClassFragment();
		classFragment
				.setOnBrankClickListener(new BrandClassFragment.OnBrankClickListener() {

					@Override
					public void onBrankClick(int firstClassId, int secondClassId) {
						ToastUtil.showToast(getActivity(), firstClassId + " , "
								+ secondClassId);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_brand_show_city:
			if (cityListPopWindow.isShowing()) {
				cityListPopWindow.dismiss();
			} else {
				cityListPopWindow.show(v);
			}
			break;
		case R.id.bt_class:
			if (frame_class.getVisibility() == View.VISIBLE) {
				frame_class.setVisibility(View.GONE);
				mBussinesList.setVisibility(View.VISIBLE);
			} else {
				frame_class.setVisibility(View.VISIBLE);
				mBussinesList.setVisibility(View.GONE);
				initBrandClassFragment();
				replaceFragment(classFragment, true);
			}

			break;
		default:
			break;
		}
	}

	public void replaceFragment(BrandClassFragment fragment,
			boolean isAddToBackStack) {

		// this.fragment = (BaseFragment) fragment;

		FragmentTransaction fragmentTransaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
//		if (isAddToBackStack) {
//			fragmentTransaction.addToBackStack(fragment.getClass().getName());
//		}
		// fragmentList.add(fragment);
		// fragmentTransaction.setCustomAnimations(R.anim.fragment_left,
		// R.anim.fragment_right);
		// fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 2));
		fragmentTransaction.replace(R.id.frame_class, fragment);
		fragmentTransaction.commit();
	}

	private void setListenerCityListPopWindow() {
		cityListPopWindow = CityListPopWindow.getIns(getActivity());
		cityListPopWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isVisible()) {
					cityListPopWindow.dismiss();
					bt_showCity.setText(cityListPopWindow.citys.get(position).name);
				}
			}
		});
	}

}
