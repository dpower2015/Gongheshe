package com.gongheshe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gongheshe.R;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
			}
		}, 3000);
	}
}
