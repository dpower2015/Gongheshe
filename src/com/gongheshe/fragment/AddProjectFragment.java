package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.javabean.ProjectContentMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddProjectFragment extends BaseFragment implements OnClickListener{
	private View view;
	private BaseActivity baseActivity;
	private ToggleButton switchState;
	private AsyncHttpClient httpClient;
	private ShareSave shareSave = ShareSave.get();
	private final static String TAG="AddProjectFragment";
	private EditText nameProjectEdit,chargeProjectEdit,chargeTelProjectEdit,nameClientEdit,telClientEdit,
	addrProjectEdit,desclibeProjectEdit;
	private TextView timeBeginEdit,timeEndEdit,stateText;
	private Bundle bundle=null;
	public static final String MODIFY_OR_ADD="modifyflag";
	public static final String MODIFY_ID="modifyid";
	private Boolean isModify=false;
	private String modifyId=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_add_project, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.save_project).setOnClickListener(this);
		nameProjectEdit=(EditText)view.findViewById(R.id.name_project);
		chargeProjectEdit=(EditText)view.findViewById(R.id.charge_project);
		chargeTelProjectEdit=(EditText)view.findViewById(R.id.charge_tel_project);
		nameClientEdit=(EditText)view.findViewById(R.id.name_client);
		telClientEdit=(EditText)view.findViewById(R.id.tel_client);
		addrProjectEdit=(EditText)view.findViewById(R.id.add_project);
		timeBeginEdit=(TextView)view.findViewById(R.id.time_begin);
		timeEndEdit=(TextView)view.findViewById(R.id.time_end);
		desclibeProjectEdit=(EditText)view.findViewById(R.id.decribe_project);
		switchState=(ToggleButton)view.findViewById(R.id.switch_state);
		stateText=(TextView)view.findViewById(R.id.state);
		switchState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				switchState.setChecked(arg1);
			}
		});
		bundle = this.getArguments();
		if (bundle != null)
		 isModify = bundle.getBoolean(MODIFY_OR_ADD, false);
		if (bundle != null)modifyId=bundle.getString(MODIFY_ID, "");
		if(isModify){
			requestModifyData();
		}
		return view;
	}
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.ibtn_back){
			baseActivity.onBackPressed();
		}else if(id==R.id.save_project){
			ProjectContentMod info=new ProjectContentMod();
			if(validJuedge(info)){
				admitProject(info);
			}
			
		}
	}
	
	
	private boolean validJuedge(ProjectContentMod info){
		String temp=null;
		temp=nameProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "项目名称不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.prjName=temp;
		temp=nameClientEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "客户姓名不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.cusName=temp;
		temp=chargeProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "负责人不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.manName=temp;
		temp=chargeTelProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "负责人电话不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.manPhone=temp;
		
		temp=telClientEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "客户电话不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.cusPhone=temp;
		if(switchState.isChecked()){
			
			info.status=1+"";
		}else info.status=2+"";
		info.prjAddress=addrProjectEdit.getText().toString();
		info.contents=desclibeProjectEdit.getText().toString();
		info.createTime=timeBeginEdit.getText().toString();
		info.completeTime=timeEndEdit.getText().toString();
		
		return true;
	}
	
	private void requestModifyData(){
		
		RequestParams params = new RequestParams();
		params.put("id",modifyId);
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.GET_PROJECT_DETAIL, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "访问失败" + e.toString());
				Toast.makeText(baseActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				LoggerSZ.i(TAG, "result = " + new String(response));
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				try {
					JSONObject jsonObject = new JSONObject(new String(response));
					Boolean state=jsonObject.getBoolean("status");
					Gson gson = new Gson();
					if(state){
						String str=jsonObject.get("prjDeti").toString();
						ProjectContentMod mod=gson.fromJson(str,ProjectContentMod.class);
						nameProjectEdit.setText(mod.prjName);
						chargeProjectEdit.setText(mod.manName);
						chargeTelProjectEdit.setText(mod.manPhone);
						nameClientEdit.setText(mod.cusName);
						telClientEdit.setText(mod.cusPhone);
						addrProjectEdit.setText(mod.prjAddress);
						desclibeProjectEdit.setText(mod.contents);
						timeBeginEdit.setText(mod.createTime);
						timeEndEdit.setText(mod.completeTime);
						stateText.setText(mod.statusName);
						if(mod.status.equals("1")){
							switchState.setChecked(false);
							
						}else if(mod.status.equals("2")){
							switchState.setChecked(true);
						}
						//Toast.makeText(baseActivity,"获取数据成功", Toast.LENGTH_SHORT).show();
						//baseActivity.onBackPressed();
					}else {
						
						Toast.makeText(baseActivity,"获取失败", Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private void admitProject(ProjectContentMod info){
		RequestParams params = new RequestParams();
		params.put("pwd",shareSave.getPsdword());
		params.put("userName",shareSave.getUserName());
		params.put("m.completeTime",info.completeTime);
		params.put("m.contents", info.contents);
		params.put("m.cusName", info.cusName);
		params.put("m.cusPhone",info.cusPhone);
		params.put("m.manName", info.manName);
		params.put("m.manPhone", info.manPhone);
		params.put("m.prjAddress", info.prjAddress);
		params.put("m.prjName", info.prjName);
		params.put("m.status", info.status);
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.ADD_PROJECT, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "访问失败" + e.toString());
				Toast.makeText(baseActivity, "添加项目失败", Toast.LENGTH_SHORT).show();
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				LoggerSZ.i(TAG, "result = " + new String(response));
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				try {
					JSONObject jsonObject = new JSONObject(new String(response));
					Boolean state=jsonObject.getBoolean("status");
					if(state){
						
						Toast.makeText(baseActivity,"添加项目成功", Toast.LENGTH_SHORT).show();
						baseActivity.onBackPressed();
					}else {
						
						Toast.makeText(baseActivity,"添加项目失败", Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
