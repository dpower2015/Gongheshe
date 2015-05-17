package com.gongheshe.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.gongheshe.R;
import com.gongheshe.util.ToastUtil;

public class BrandClassFragment extends BaseFragment {

	private View view;
	private FragmentManager fmn;
	private OnBrankClickListener brankClickListener;
	private ScrollView sv_home;

	public interface OnBrankClickListener {
		public void onBrankClick(int firstClassId, int secondClassId);
	}

	public void setOnBrankClickListener(OnBrankClickListener brankClickListener) {
		this.brankClickListener = brankClickListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.fragment_brand_class, container, false);
		fmn = getActivity().getSupportFragmentManager();
		sv_home = (ScrollView) view.findViewById(R.id.sv_home);
		replaceAllView();
		return view;
	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		replaceAllView();
//	}
	
	private void replaceAllView(){
		FragmentTransaction trans = fmn.beginTransaction();
		trans.replace(R.id.homeStructureFragment,
				new HomeStructureFragment().setParent(this));
		trans.replace(R.id.homeElecFragment,
				new HomeElecFragment().setParent(this));
		trans.replace(R.id.homeBathFragment,
				new HomeBathFragment().setParent(this));
		trans.replace(R.id.homeWoodFragment,
				new HomeWoodFragment().setParent(this));
		trans.replace(R.id.homeFaceFragment,
				new HomeFaceFragment().setParent(this));
		trans.replace(R.id.homeLampFragment,
				new HomeLampFragment().setParent(this));
		trans.replace(R.id.homeFurnishFragment,
				new HomeFurnishFragment().setParent(this));
		int result = trans.commit();
		ToastUtil.showToast(getActivity(), "trans.commit()="+result);
	}

	public void setOnBrankClickListener(int firstClassId, int secondClassId) {
		if (brankClickListener != null) {
			brankClickListener.onBrankClick(firstClassId, secondClassId);
		}
	}
	
	public void scrollToBottom() {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				sv_home.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

}
