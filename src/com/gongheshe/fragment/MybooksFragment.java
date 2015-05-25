package com.gongheshe.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zy.zh.xListView.XListView;
import android.R.string;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.MybooksListAdapter;
import com.gongheshe.adapter.MybooksListAdapter.onClickListener;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.OrderMod;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MybooksFragment extends BaseFragment implements OnClickListener{
	private View view;
	private BaseActivity baseActivity;
	private XListView xListView;
	private final static String TAG="MybooksFragment";
	private final static int PAGE_SIZE=20;
	private int mPageNum;
	private AsyncHttpClient httpClient;
	private String userId;
	private ShareSave shareSave = ShareSave.get();
	private MybooksListAdapter mybooksListAdapter;
	private final static int CONFIRM_ACCEPT_FLAG=1;
	private final static int APPLY_BACKWARD_FLAG=2;
	private Button finishBt,goingBt;
	private final static int ORDER_FLAG_FINISHED=1;
	private final static int ORDER_FLAG_GOING=2;
	private int curOrderFlag;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mybooks, container, false);
		baseActivity=(BaseActivity)getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		finishBt=(Button)view.findViewById(R.id.finished);
		finishBt.setOnClickListener(this);
		goingBt=(Button)view.findViewById(R.id.going);
		goingBt.setOnClickListener(this);
		mybooksListAdapter=new MybooksListAdapter(baseActivity);
		xListView=(XListView)view.findViewById(R.id.xListView);
		xListView.setAdapter(mybooksListAdapter);
		xListView.setPullLoadEnable(true);
		xListView.setPullRefreshEnable(false);
		mybooksListAdapter.setItemclickListener(new onClickListener() {
			
			@Override
			public void onClick(int index) {
				// TODO Auto-generated method stub
				ArrayList<OrderMod> orderList;
				orderList=mybooksListAdapter.getBookList();
				ProductThirdDetailFragment productDetail = new ProductThirdDetailFragment();
				productDetail.setTypeClassMod(orderList.get(index).products.get(0));
				baseActivity.replaceFragment(productDetail, false);
			}

			@Override
			public void onBackward(int index) {
				// TODO Auto-generated method stub
				ArrayList<OrderMod> orderList;
				orderList=mybooksListAdapter.getBookList();
				postData(orderList.get(index).id,APPLY_BACKWARD_FLAG);
			}
			@Override
			public void onConfirmAccept(int index) {
				// TODO Auto-generated method stub
				ArrayList<OrderMod> orderList;
				orderList=mybooksListAdapter.getBookList();
				postData(orderList.get(index).id,CONFIRM_ACCEPT_FLAG);
			}
		});
		xListView.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
			
				
			}

			@Override
			public void onLoadMore() {
				mPageNum++;
				getOrderListData(curOrderFlag);
				//requestWebServer(pageNumber);
			}
		});
		
		mPageNum=1;
		curOrderFlag=ORDER_FLAG_FINISHED;
		userId =shareSave.getUid();
		getOrderListData(curOrderFlag);
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.ibtn_back:
			baseActivity.onBackPressed();
			break;
		case R.id.finished:
			
			finishBt.setBackgroundResource(R.color.red);
			finishBt.setTextColor(baseActivity.getResources().getColor(R.color.white));
			goingBt.setBackgroundResource(R.color.white);
			goingBt.setTextColor(baseActivity.getResources().getColor(R.color.red));
			mPageNum=1;
			curOrderFlag=ORDER_FLAG_FINISHED;
			mybooksListAdapter.clear();
			getOrderListData(curOrderFlag);
			break;
		case R.id.going:
			goingBt.setBackgroundResource(R.color.red);
			goingBt.setTextColor(baseActivity.getResources().getColor(R.color.white));
			finishBt.setBackgroundResource(R.color.white);
			finishBt.setTextColor(baseActivity.getResources().getColor(R.color.red));
			curOrderFlag=ORDER_FLAG_GOING;
			mPageNum=1;
			mybooksListAdapter.clear();
			getOrderListData(curOrderFlag);
			
			break;
		default:
			break;
		}
		
	}
	void postData(String id,final int flag){
		
		String url=null;
		RequestParams params = new RequestParams();
		params.put("pwd",shareSave.getPsdword());
		params.put("userName",shareSave.getUserName());
		params.put("id",id);
		if(flag==CONFIRM_ACCEPT_FLAG){
			url=GhhConst.POST_CONFIRM_ACCEPT;
		}else if(flag==APPLY_BACKWARD_FLAG){
			url=GhhConst.POST_APPLY_BACKWARD;
		}
		httpClient = new AsyncHttpClient();
		httpClient.post(url, params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				xListView.stopLoadMore();
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
					String msg=jsonObject.getString("msg");
					Toast.makeText(baseActivity,msg, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(baseActivity, e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});		
		
		
	}
	private void getOrderListData(int flag){
		RequestParams params = new RequestParams();
		params.put("pagesize", PAGE_SIZE+"");
		params.put("pagenumber",mPageNum+"");
		params.put("memberId",userId);
		params.put("orderState", flag+"");
		httpClient = new AsyncHttpClient();
		httpClient.post(GhhConst.GET_ORDER_LIST, params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				xListView.stopLoadMore();
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
				 xListView.stopLoadMore();
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
			
			mybooksListAdapter.addLists(datas);
			mybooksListAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
}
