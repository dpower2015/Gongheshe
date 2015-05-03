package com.gongheshe.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.injectView.InjectView;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author ZhengZhiying<br>
 * @function 登录页面
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener{

	@InjectView(id = R.id.username)
	private EditText edt_name;
	@InjectView(id = R.id.password)
	private EditText edt_psd;
	
	private Context context;
	private AsyncHttpClient httpClient;
	private final static String TAG="LoginActivity";
	private ShareSave shareSave = ShareSave.get();
	private userInfo mod;
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
			initDataByLocalData();
			if(inputValidCheck()){
				loginserver(edt_psd.getText().toString(),edt_psd.getText().toString());
			}
			//startActivity(new Intent(context,MainFragmentActivity.class));
			//finish();
			break;
		case R.id.btn_register:
			startActivity(new Intent(context,RegisterActivity.class));
			break;
		default:
			break;
		}
	}
	private void initDataByLocalData() {
		if (shareSave.getUserName() != null) {
			//if (shareSave.isKeepPassword()) {
				edt_name.setText(shareSave.getUserName());
			//}
		}
		if (shareSave.getPsdword() != null) {
			//if (shareSave.isKeepPassword()) {
				edt_psd.setText(shareSave.getPsdword());
			//}
		}
		//cbKeepPsd.setChecked(shareSave.isKeepPassword());
	}
	private boolean inputValidCheck(){
		if (TextUtils.isEmpty(edt_name.getText().toString())) {
			Toast.makeText(this, R.string.username_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(edt_psd.getText().toString())) {
			Toast.makeText(this, R.string.password_is_null,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private void loginserver(final String name,final String psw){
		
		RequestParams params = new RequestParams();
		params.put("userName", name);
		params.put("pwd", psw);
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.LOGIN_USER, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "访问失败" + e.toString());
				Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				LoggerSZ.i(TAG, "login result = " + new String(response));
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				try {
					JSONObject jsonObject = new JSONObject(new String(response));
					boolean result = jsonObject.getBoolean("status");
					String msg=jsonObject.getString("msg");
					if(result){
						// 登录成功
						jsonObject = jsonObject.getJSONObject("userInfo");
						mod = new Gson().fromJson(jsonObject.toString(),
								userInfo.class);
						shareSave.setUid(mod.id);
						shareSave.setUserName(name);
						shareSave.setPsdword(psw);
						shareSave.Enter(true);
						Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT)
								.show();
						Intent intent = new Intent();
						intent.setClass(context, MainFragmentActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						finish();
						startActivity(intent);
					}else {
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
				}

				LoggerSZ.i(TAG, "登录接口返回：" + new String(response));

			}

		});
	}
	
	private class userInfo {
		private String companyAddress;
		private String companyName;
		private String icon;
		private String id;
		private String mobile;
		private String status;
		private String userName;
	}
}
