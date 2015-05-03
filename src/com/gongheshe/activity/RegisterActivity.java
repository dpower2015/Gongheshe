package com.gongheshe.activity;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.injectView.InjectView;
import com.gongheshe.util.LoggerSZ;
import com.googheshe.entity.GhhConst;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author ZhengZhiying<br>
 * @function 	 注册界面
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	@InjectView(id = R.id.user_name)
	private EditText edt_name;

	@InjectView(id = R.id.phone_number)
	private EditText edt_tel_number;

	@InjectView(id = R.id.password)
	private EditText edt_psd;

	//@InjectView(id = R.id.edt_psd_again)
	//private EditText edt_psd_again;
	@InjectView(id = R.id.company_belong)
	private EditText edt_company;
	
	
	
	@InjectView(id = R.id.ibtn_back)
	private ImageButton bt_back;
	@InjectView(id = R.id.register)
	private Button register;
	
	private AsyncHttpClient httpClient;
	private final static String TAG="RegisterActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		bt_back.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_back:
			finish();
			
			break;
		case R.id.register:
			if(inputValidCheck()){
				registerNewUser();
			}
			break;
		default:
			break;
		}
	}
	private boolean inputValidCheck(){
		if (TextUtils.isEmpty(edt_name.getText().toString())) {
			Toast.makeText(this, R.string.username_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(edt_tel_number.getText().toString())) {
			Toast.makeText(this, R.string.telphone_number_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (edt_tel_number.getText().toString().length() != 11) {
			Toast.makeText(this, R.string.no_the_right_tel_number,
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (TextUtils.isEmpty(edt_psd.getText().toString())) {
			Toast.makeText(this, R.string.password_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!edt_psd.getText().toString()
				.equals(edt_company.getText().toString())) {
			Toast.makeText(this, R.string.company_name_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		
		return true;
	}
	private void registerNewUser() {
		//String url = "http://s-145217.abc188.com/stone/index.php?g=API&m=Register";
		httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("member.userName", edt_name.getText().toString());
		params.put("member.mobile", edt_tel_number.getText().toString());
		params.put("member.pwd", edt_psd.getText().toString());
		params.put("member.companyName",edt_company.getText().toString());
		LoadingDlg.get().show();
		httpClient.post(GhhConst.REGISTER_USER, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "访问失败" + e.toString());
				Toast.makeText(RegisterActivity.this, R.string.register_fail, Toast.LENGTH_SHORT).show();
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				try {
					Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}
	
}
