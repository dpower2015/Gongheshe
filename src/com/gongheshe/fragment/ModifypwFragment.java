package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.injectView.InjectView;
import com.gongheshe.javabean.UserInfo;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ModifypwFragment extends BaseFragment implements OnClickListener{


	private EditText oldpwEdit;
	private EditText newpwEdit;
	private EditText newpwAgainEdit;
	
	
	private View view;
	private BaseActivity baseActivity;
	private final static String TAG="ModifypwFragment";
	private AsyncHttpClient httpClient;
	private ShareSave shareSave = ShareSave.get();
	private UserInfo mod;
	private String newPW;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_modify_passwd, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.admit_modify).setOnClickListener(this);
		oldpwEdit=(EditText)view.findViewById(R.id.oldpw);
		newpwEdit=(EditText)view.findViewById(R.id.newpw);
		newpwAgainEdit=(EditText)view.findViewById(R.id.newpw_again);
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.ibtn_back){
			baseActivity.onBackPressed();
		}else if(id==R.id.admit_modify){
			String oldpw,newpw1,newpw2,usrId;
			oldpw=oldpwEdit.getText().toString();
			newpw1=newpwEdit.getText().toString();
			newpw2=newpwAgainEdit.getText().toString();
			if(!newpw1.endsWith(newpw2)){
				Toast.makeText(baseActivity, "两次输入密码不一致", Toast.LENGTH_SHORT)
				.show();
				return ;
			}
			usrId=shareSave.getUid();
			newPW=newpw1;
			admitChange(oldpw,newPW,usrId);
			
		}
	}
	
	private void admitChange(String oldpw,String newpw,String userId){
		
		RequestParams params = new RequestParams();
		params.put("pwd",oldpw);
		params.put("pwdNew",newpw);
		params.put("id", userId);
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.MODIFY_PASSWORD, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "failed result:" + e.toString());
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				LoggerSZ.i(TAG, "modify result = " + new String(response));
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				try {
					JSONObject jsonObject = new JSONObject(new String(response));
					boolean result = jsonObject.getBoolean("status");
					String msg=jsonObject.getString("msg");
					Toast.makeText(baseActivity, msg, Toast.LENGTH_SHORT)
					.show();
					if(result){
						shareSave.setPsdword(newPW);
						baseActivity.onBackPressed();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, "getdata exception", Toast.LENGTH_SHORT).show();
				}


			}

		});
		
	}
	
	
}
