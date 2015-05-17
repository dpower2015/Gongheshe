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
import com.gongheshe.adapter.MystoreListAdapter;
import com.gongheshe.dialog.CateListPopWindow;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MystoreFragment extends BaseFragment implements OnClickListener{

	private View view;
	private BaseActivity baseActivity;
	private XListView xListView;
	private CateListPopWindow cateListPopWindow;
	private AsyncHttpClient httpClient;
	private final static String TAG="MystoreFragment";
	private final static int PAGE_SIZE=20;
	private int mPageNum=1;
	private ShareSave shareSave = ShareSave.get();
	private String userId;
	private MystoreListAdapter mystoreListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_my_collect, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.more_cate).setOnClickListener(this);
		xListView=(XListView)view.findViewById(R.id.xListView);
		mystoreListAdapter =new MystoreListAdapter(baseActivity);
		xListView.setAdapter(mystoreListAdapter);
		cateListPopWindow =CateListPopWindow.getIns(baseActivity);
		cateListPopWindow.setStoreFragment(this);
		userId=shareSave.getUid();
		requestStoreData(-1);
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id=arg0.getId();
		if(id==R.id.more_cate){
			
			if(cateListPopWindow.isShowing()){
				
				cateListPopWindow.dismiss();
			}else {
				
				cateListPopWindow.show(arg0);
			}
			
		}else if(id==R.id.ibtn_back){
			
			baseActivity.onBackPressed();
			
		}
		
	}
	public void update(int id){
		mPageNum=1;
		requestStoreData(id+1);
	}
	public void requestStoreData(int id){
		String url=null;
		RequestParams params = new RequestParams();
		params.put("pagesize", PAGE_SIZE+"");
		params.put("pagenumber",mPageNum+"");
		params.put("memberId",userId+"");
		if(id!=-1){
			url=GhhConst.GET_MY_STORE_LIST_BYCATE;
			params.put("product.typeOneId",id+"");
		}
		else {
			
			url=GhhConst.GET_MY_STORE_LIST;
		}

		
		httpClient = new AsyncHttpClient();
		System.out.println("uerId :"+userId);
		httpClient.post(GhhConst.GET_MY_STORE_LIST, params, new AsyncHttpResponseHandler() {

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
						
						onDataReturn(new String(response));
						
					}else {
						
						Toast.makeText(baseActivity,"获取数据失败", Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		}
	
	private void onDataReturn(String string){
		try {
			JSONObject jsonObject = new JSONObject(string);
			jsonObject = jsonObject.getJSONObject("products");
			JSONArray arr = jsonObject.getJSONArray("data");
			ArrayList<ProductMod> datas;
			datas = new ArrayList<ProductMod>();
			Gson gson = new Gson();
			for (int i = 0; i < arr.length(); i++) {
				datas.add(gson.fromJson(arr.getJSONObject(i)
						.toString(), ProductMod.class));
			}
			mystoreListAdapter.setMystoreList(datas);
			mystoreListAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
}
