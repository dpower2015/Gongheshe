package com.gongheshe.activity;

import com.example.gongheshe.R;
import com.gongheshe.adapter.MainPagerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainFragmentActivity extends BaseActivity implements OnClickListener{

	private Context context;
	private ViewPager viewPager;
	private MainPagerAdapter mainPagerAdapter;
	private ImageButton imgButton;
	private int imgResId;

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
}
