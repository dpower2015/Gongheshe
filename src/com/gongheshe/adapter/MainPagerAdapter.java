package com.gongheshe.adapter;

import java.util.ArrayList;

import com.gongheshe.fragment.BaseFragment;
import com.gongheshe.fragment.BrandFragment;
import com.gongheshe.fragment.HomePageFragment;
import com.gongheshe.fragment.HotSaleFragment;
import com.gongheshe.fragment.MineFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {
	
	private ArrayList<BaseFragment> fragments;
	

	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new HomePageFragment());
		fragments.add(new BrandFragment());
		fragments.add(new HotSaleFragment());
		fragments.add(new MineFragment());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
