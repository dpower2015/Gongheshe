package com.gongheshe.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zy.zh.xListView.XListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.MybooksListAdapter;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.OrderMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MybooksFragment extends BaseFragment{
	private View view;
	private BaseActivity baseActivity;
	private XListView xListView;
	
	private final static String TAG="MybooksFragment";
	private final static int PAGE_SIZE=20;
	private int mPageNum=1;
	private AsyncHttpClient httpClient;
	private String userId;
	private ShareSave shareSave = ShareSave.get();
	private MybooksListAdapter mybooksListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mybooks, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				baseActivity.onBackPressed();
			}
		});
		mybooksListAdapter=new MybooksListAdapter(baseActivity);
		xListView=(XListView)view.findViewById(R.id.xListView);
		xListView.setAdapter(mybooksListAdapter);
		userId =shareSave.getUid();
		getOrderListData();
		return view;
	}
	private void getOrderListData(){
		RequestParams params = new RequestParams();
		params.put("pagesize", PAGE_SIZE+"");
		params.put("pagenumber",mPageNum+"");
		params.put("memberId",userId);
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.GET_ORDER_LIST, params, new AsyncHttpResponseHandler() {
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
				 mPageNum++;
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
						String data=jsonObject.getString("orders");
						onDataReturn(data);
						
					}else {
						
						Toast.makeText(baseActivity,"获取数据失败1", Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private void onDataReturn(String str){
		Gson gson = new Gson();
		JSONObject jsonObject;
		try {
			ArrayList<OrderMod> datas=new ArrayList<OrderMod>();
			jsonObject = new JSONObject(str);
			JSONArray arr = jsonObject.getJSONArray("data");
			for (int i = 0; i < arr.length(); i++) {
				datas.add(gson.fromJson(arr.getJSONObject(i)
						.toString(), OrderMod.class));
			}
			
			mybooksListAdapter.setBooksList(datas);
			mybooksListAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
