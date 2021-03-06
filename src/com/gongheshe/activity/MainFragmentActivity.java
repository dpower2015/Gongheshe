package com.gongheshe.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.gongheshe.R;
import com.gongheshe.adapter.MainPagerAdapter;
import com.gongheshe.fragment.BaseFragment;
import com.gongheshe.fragment.BrandFragment;
import com.gongheshe.fragment.HomePageFragment;
import com.gongheshe.fragment.HotSaleFragment;
import com.gongheshe.fragment.MineFragment;
import com.gongheshe.util.ToastUtil;

public class MainFragmentActivity extends BaseActivity implements
		OnClickListener {

	private Context context;
	private ViewPager viewPager;
	private MainPagerAdapter mainPagerAdapter;
	private ImageButton imgButton;
	private int imgResId;

	private BaseFragment fragment;
	private BaseFragment mHomefragment;
	private List<Fragment> fragmentList;
	private long firstTime;
	private long secondTime;
	private long spaceTime;
	private int curPage=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		context = this;
		findViewById(R.id.ibtn_home).setOnClickListener(this);
		findViewById(R.id.ibtn_brand).setOnClickListener(this);
		findViewById(R.id.ibtn_hotsell).setOnClickListener(this);
		findViewById(R.id.ibtn_mine).setOnClickListener(this);
		initfragent();

	}

	public void initfragent() {

		fragmentList = new ArrayList<Fragment>();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		mHomefragment = new HomePageFragment();
		fragmentList.add(mHomefragment);
		fragmentTransaction.add(R.id.fragment, mHomefragment);
		fragmentTransaction.commit();
		imgResId = R.drawable.ic_home_off;
		imgButton = (ImageButton) findViewById(R.id.ibtn_home);

	}

	private void onPageSelect(int position) {
		ImageButton img = null;
		if (imgButton != null) {
			imgButton.setImageResource(imgResId);
		}
		switch (position) {
		case 0:
			img = (ImageButton) findViewById(R.id.ibtn_home);
			img.setImageResource(R.drawable.ic_home_on);
			imgResId = R.drawable.ic_home_off;
			break;
		case 1:
			img = (ImageButton) findViewById(R.id.ibtn_brand);
			img.setImageResource(R.drawable.ic_brand_on);
			imgResId = R.drawable.ic_brand_off;
			break;
		case 2:
			img = (ImageButton) findViewById(R.id.ibtn_hotsell);
			img.setImageResource(R.drawable.ic_hotsell_on);
			imgResId = R.drawable.ic_hotsell_off;
			break;
		case 3:
			img = (ImageButton) findViewById(R.id.ibtn_mine);
			img.setImageResource(R.drawable.ic_mine_on);
			imgResId = R.drawable.ic_mine_off;
			break;
		default:
			break;
		}
		imgButton = img;

	}

	public HomePageFragment getHomefragment() {
		return (HomePageFragment) mHomefragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_home:
			if(curPage!=0){
				onPageSelect(0);
				mHomefragment = new HomePageFragment();
				replaceFragment(mHomefragment);
			}
			curPage =0;
			break;
		case R.id.ibtn_brand:
			if(curPage!=1){
				
				onPageSelect(1);
				 replaceFragment(new BrandFragment(),false);
			
			}
			 curPage =1;
			// replaceFragment(new BrandFragment());
			break;
		case R.id.ibtn_hotsell:
			if(curPage!=2){
				onPageSelect(2);
				replaceFragment(new HotSaleFragment(), true);
			}
				curPage =2;
			// viewPager.setCurrentItem(2);
			break;
		case R.id.ibtn_mine:
			if(curPage!=3){
				onPageSelect(3);
				replaceFragment(new MineFragment(), true);
			}
			curPage =3;
			// viewPager.setCurrentItem(3);
			break;

		default:
			break;
		}

	}

	private void replaceFragment(Fragment fragment) {
		this.fragment = (BaseFragment) fragment;
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		//fragmentTransaction.setCustomAnimations(R.anim.fragment_left,
		//		R.anim.fragment_right);
		fragmentTransaction.replace(R.id.fragment, fragment);
		fragmentTransaction.commit();
	}

	/**
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
		//fragmentTransaction.setCustomAnimations(R.anim.fragment_left,
			//	R.anim.fragment_right);
//		{
//			List<Fragment> f = getSupportFragmentManager().getFragments();
//			for(int i=0;i<f.size();i++){
//				Log.i("MainFragmentActivity", "主Activity包含：f("+i+")="+f.get(i).getClass().getSimpleName());
//			}
//		}
//		 fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 2));
		 fragmentTransaction.add(R.id.fragment, fragment);
//		fragmentTransaction.replace(R.id.fragment, fragment);
		fragmentTransaction.commit();
	}

	/**
	 * @author ZhengZhiying
	 * @param f
	 */
	@Override
	public void addFragment(BaseFragment f) {
		super.addFragment(f);

		FragmentTransaction trans;
		trans = getSupportFragmentManager().beginTransaction();
		if (getSupportFragmentManager().getFragments().contains(f)) {
			trans.remove(f);
			trans.commit();
			trans = getSupportFragmentManager().beginTransaction();
		}
		trans.addToBackStack(f.getClass().getName());
		//trans.setCustomAnimations(R.anim.fragment_left, R.anim.fragment_right);
		trans.add(R.id.fragment, f);
//		if (fragmentList.size() > 2) {
//			trans.hide(fragmentList.get(fragmentList.size() - 2));
//		}
		fragmentList.add(f);
		trans.commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 0) {

			if (fragmentList.size() < 2) {
				firstTime = System.currentTimeMillis();
				spaceTime = firstTime - secondTime;
				secondTime = firstTime;
				if (spaceTime > 2000) {
					ToastUtil.showToast(this, getString(R.string.click_again_exit));
					
				} else {
					super.onBackPressed();
					System.exit(0);
				}
				return ;
			}

			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			//fragmentTransaction.setCustomAnimations(R.anim.fragment_back_left,
					//R.anim.fragment_back_right);

			this.fragment = (BaseFragment) fragmentList
					.get(fragmentList.size() - 2);

			fragmentTransaction.show(fragmentList.get(fragmentList.size() - 2));
			fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 1));
			// by ZhengZhiying
			fragmentTransaction
					.remove(fragmentList.get(fragmentList.size() - 1));
			fragmentTransaction.commit();
			// by ZhengZhiying
			// fragmentManager.popBackStack();
			fragmentList.remove(fragmentList.size() - 1);
		} else {

			firstTime = System.currentTimeMillis();
			spaceTime = firstTime - secondTime;
			secondTime = firstTime;
			if (spaceTime > 2000) {
				ToastUtil.showToast(this, getString(R.string.click_again_exit));
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
			//fragmentTransaction.setCustomAnimations(R.anim.fragment_back_left,
				//	R.anim.fragment_back_right);

			this.fragment = (BaseFragment) fragmentList
					.get(fragmentList.size() - 2);

			fragmentTransaction.show(fragmentList.get(0));
			fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 1));

			fragmentTransaction.commit();

			for (int i = 0, size = fragmentList.size(); i < size; i++) {
				fragmentManager.popBackStack();
			}
			fragmentList.clear();
			fragmentList.add(mHomefragment);
		}
	}
}
