package com.gongheshe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gongheshe.R;

/**
 * @author ZhengZhiying<br>
 * @function 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_register).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			startActivity(new Intent(context,MainFragmentActivity.class));
			finish();
			break;
		case R.id.btn_register:
			startActivity(new Intent(context,RegisterActivity.class));
			break;
		default:
			break;
		}
	}
}
