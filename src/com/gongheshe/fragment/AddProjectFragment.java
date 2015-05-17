package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.provider.Telephony.Mms;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddProjectFragment extends BaseFragment implements OnClickListener{
	class ProjectInfo{
		public String nameProject;
		public String chargeProject;
		public String chargeTelProject;
		public String nameClient;
		public String telClient;
		public String addrProject;
		public String timeBegin;
		public String timeEnd;
		public String stateProject;
		public String describeProject;
	}
	
	private View view;
	private BaseActivity baseActivity;
	private ToggleButton switchState;
	private AsyncHttpClient httpClient;
	private ShareSave shareSave = ShareSave.get();
	private final static String TAG="AddProjectFragment";
	private EditText nameProjectEdit,chargeProjectEdit,chargeTelProjectEdit,nameClientEdit,telClientEdit,
	addrProjectEdit,desclibeProjectEdit;
	private TextView timeBeginEdit,timeEndEdit;
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
		switchState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				switchState.setChecked(arg1);
			}
		});
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.ibtn_back){
			baseActivity.onBackPressed();
		}else if(id==R.id.save_project){
			ProjectInfo info=new ProjectInfo();
			if(validJuedge(info)){
				admitProject(info);
			}
			
		}
	}
	
	
	private boolean validJuedge(ProjectInfo info){
		String temp=null;
		temp=nameProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "项目名称不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.nameProject=temp;
		temp=nameClientEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "客户姓名不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.nameClient=temp;
		temp=chargeProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "负责人不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.chargeProject=temp;
		temp=chargeTelProjectEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "负责人电话不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.chargeTelProject=temp;
		
		temp=telClientEdit.getText().toString();
		if(temp.isEmpty()){
			Toast.makeText(baseActivity, "客户电话不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		info.telClient=temp;
		if(switchState.isChecked()){
			
			info.stateProject=1+"";
		}else info.stateProject=2+"";
		info.addrProject=addrProjectEdit.getText().toString();
		info.describeProject=desclibeProjectEdit.getText().toString();
		info.timeBegin=timeBeginEdit.getText().toString();
		info.timeEnd=timeEndEdit.getText().toString();
		
		return true;
	}
	private void admitProject(ProjectInfo info){
		RequestParams params = new RequestParams();
		params.put("pwd",shareSave.getPsdword());
		params.put("userName",shareSave.getUserName());
		params.put("m.completeTime",info.timeEnd);
		params.put("m.contents", info.describeProject);
		params.put("m.cusName", info.nameClient);
		params.put("m.cusPhone",info.telClient);
		params.put("m.manName", info.chargeProject);
		params.put("m.manPhone", info.chargeTelProject);
		params.put("m.prjAddress", info.addrProject);
		params.put("m.prjName", info.nameProject);
		params.put("m.status", info.stateProject);
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
