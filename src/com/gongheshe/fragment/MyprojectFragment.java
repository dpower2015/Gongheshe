package com.gongheshe.fragment;

import org.apache.http.Header;

import zy.zh.xListView.XListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.MyProjectListAdapter;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.util.LoggerSZ;
import com.googheshe.entity.GhhConst;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyprojectFragment extends BaseFragment implements OnClickListener{

	
	private View view;
	private BaseActivity baseActivity;
	private AsyncHttpClient client;
	private final String TAG="CopyrightFragment";
	private XListView xListView;
	private MyProjectListAdapter myprojectListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_myproject, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.add_project).setOnClickListener(this);
		
		xListView=(XListView)view.findViewById(R.id.myprojectlist);
		myprojectListAdapter =new MyProjectListAdapter(baseActivity);
		xListView.setAdapter(myprojectListAdapter);
		requestData();
		return view;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.ibtn_back){
			baseActivity.onBackPressed();
		}else if(id==R.id.add_project){
			AddProjectFragment myproject=new AddProjectFragment();
			baseActivity.replaceFragment(myproject, true);
		}
	}
	
	
	
	private void requestData() {
		client = new AsyncHttpClient();
		client.get(getActivity(), GhhConst.GET_PROJECT_LIST, null, null,
				new AsyncHttpResponseHandler() {

					public void onFailure(int statusCode, Header[] headers,
							byte[] response, Throwable e) {
						xListView.stopLoadMore();
						xListView.stopRefresh();
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
						xListView.stopLoadMore();
						xListView.stopRefresh();
						onDataReturn(new String(response));
						try {
							LoadingDlg.get().hide();
						} catch (Exception ex) {
							LoggerSZ.e(TAG, "" + ex.toString());
						}
					}
				});

	}
	protected void onDataReturn(String string) {
		System.out.println("onDataReturn :"+string);
		//adapter.fillDataToView(string);
	}





	
	
}
