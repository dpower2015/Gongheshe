package com.gongheshe.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.gongheshe.R;
import com.gongheshe.adapter.MainPagerAdapter;
import com.gongheshe.fragment.BaseFragment;
import com.gongheshe.util.ToastUtil;

public class MainFragmentActivity extends BaseActivity implements OnClickListener{

	private Context context;
	private ViewPager viewPager;
	private MainPagerAdapter mainPagerAdapter;
	private ImageButton imgButton;
	private int imgResId;

	private BaseFragment fragment;
	private List<Fragment> fragmentList;
	private long firstTime;
	private long secondTime;
	private long spaceTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		context = this;
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mainPagerAdapter);
		setOnPageChangeListener();
		findViewById(R.id.ibtn_home).setOnClickListener(this);
		findViewById(R.id.ibtn_brand).setOnClickListener(this);
		findViewById(R.id.ibtn_hotsell).setOnClickListener(this);
		findViewById(R.id.ibtn_mine).setOnClickListener(this);
//		findViewById(R.id.ibtn_home).performClick();
//		viewPager.setCurrentItem(0);
		onPageSelect(0);
	}

	private void setOnPageChangeListener() {

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				onPageSelect(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}
	
	private void onPageSelect(int position){
		ImageButton img = null;
		if(imgButton != null){
			imgButton.setImageResource(imgResId);
		}
		switch (position) {
		case 0:
			img = (ImageButton)findViewById(R.id.ibtn_home);
			img.setImageResource(R.drawable.ic_home_on);
			imgResId = R.drawable.ic_home_off;
			break;
		case 1:
			img = (ImageButton)findViewById(R.id.ibtn_brand);
			img.setImageResource(R.drawable.ic_brand_on);
			imgResId = R.drawable.ic_brand_off;
			break;
		case 2:
			img = (ImageButton)findViewById(R.id.ibtn_hotsell);
			img.setImageResource(R.drawable.ic_hotsell_on);
			imgResId = R.drawable.ic_hotsell_off;
			break;
		case 3:
			img = (ImageButton)findViewById(R.id.ibtn_mine);
			img.setImageResource(R.drawable.ic_mine_on);
			imgResId = R.drawable.ic_mine_off;
			break;
		default:
			break;
		}
		imgButton = img;
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_home:
			viewPager.setCurrentItem(0);
			break;
		case R.id.ibtn_brand:
			viewPager.setCurrentItem(1);
			break;
		case R.id.ibtn_hotsell:
			viewPager.setCurrentItem(2);
			break;
		case R.id.ibtn_mine:
			viewPager.setCurrentItem(3);
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 增加
	 * 
	 * @param fragment
	 * @param isAddToBackStack
	 */
	public void replaceFragment(Fragment fragment, boolean isAddToBackStack) {

		this.fragment = (BaseFragment) fragment;

		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		if (isAddToBackStack) {
			fragmentTransaction.addToBackStack(fragment.getClass().getName());
		}
		fragmentList.add(fragment);
		fragmentTransaction.setCustomAnimations(R.anim.fragment_left,
				R.anim.fragment_right);
		fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 2));
		//fragmentTransaction.add(R.id.fragment, fragment);
		fragmentTransaction.commit();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 0) {

			if (fragmentList.size() < 2) {
				return;
			}

			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.anim.fragment_back_left,
					R.anim.fragment_back_right);

			this.fragment = (BaseFragment) fragmentList
					.get(fragmentList.size() - 2);

			fragmentTransaction.show(fragmentList.get(fragmentList.size() - 2));
			fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 1));

			fragmentTransaction.commit();
			fragmentManager.popBackStack();
			fragmentList.remove(fragmentList.size() - 1);
		} else {

			firstTime = System.currentTimeMillis();
			spaceTime = firstTime - secondTime;
			secondTime = firstTime;
			if (spaceTime > 2000) {
				ToastUtil.showToast(this, "再按一次退出程序");
			} else {
				super.onBackPressed();
				System.exit(0);
			}

		}
	}

	@Override
	public void reSetView() {
		// TODO Auto-generated method stub
		super.reSetView();

		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 0) {

			if (fragmentList.size() < 2) {
				return;
			}
			
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.anim.fragment_back_left,
					R.anim.fragment_back_right);

			this.fragment = (BaseFragment) fragmentList
					.get(fragmentList.size() - 2);

			fragmentTransaction.show(fragmentList.get(0));
			fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 1));

			fragmentTransaction.commit();
			
			for (int i = 0,size = fragmentList.size(); i < size; i++) {
				fragmentManager.popBackStack();
			}
			fragmentList.clear();
			//fragmentList.add(homeFragment);
		}
	}
}
