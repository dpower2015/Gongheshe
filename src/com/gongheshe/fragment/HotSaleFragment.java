package com.gongheshe.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.HotSelAdapter;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.HotSelMod;
import com.gongheshe.javabean.HotSelTimeMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.cache.ACache;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HotSaleFragment extends BaseFragment implements OnItemClickListener ,OnCheckedChangeListener,
PullToRefreshBase.OnRefreshListener<StaggeredGridView>,OnScrollListener{
	
	private final static String TAG="HotSaleFragment";
	private final static int PAGE_SIZE=20;
	private int mPageNum=1;
	private View view;
	//private WaterfallSmartView mWaterfall;
	private HotSelAdapter mHotselAdapter;
	private ACache mCache;
	private AsyncHttpClient httpClient;
	private BaseActivity baseActivity;
	private CheckBox price,sentiment,sale_volume;
	private PullToRefreshStaggeredGridView hotselList;
	private final static int GET_PRICE_LIST=0;
	private final static int GET_SENTIMENT_LIST=1;
	private final static int GET_SALE_LIST=2;
	private int mCurFlag=GET_PRICE_LIST;
	//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_hotsell, container, false);
		hotselList=(PullToRefreshStaggeredGridView)view.findViewById(R.id.hotsel_shop_list);
		price=((CheckBox)view.findViewById(R.id.price));
		sentiment=((CheckBox)view.findViewById(R.id.sentiment));
		sale_volume=((CheckBox)view.findViewById(R.id.sale_volume));
		sale_volume.setOnCheckedChangeListener(this);
		sentiment.setOnCheckedChangeListener(this);
		price.setOnCheckedChangeListener(this);
		mHotselAdapter=new HotSelAdapter(getActivity());
		hotselList.setAdapter(mHotselAdapter);
		hotselList.setOnRefreshListener(this);
		hotselList.setMode(Mode.PULL_UP_TO_REFRESH); 
		hotselList.setOnScrollListener(this);
		hotselList.setOnItemClickListener(this);
		mCache = ACache.get(getActivity());
		baseActivity =(BaseActivity)getActivity();
		getUpProductTime();
		requestHotSelGoodsList(GET_PRICE_LIST);
		return view;
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.price){
			price.setBackgroundResource(R.color.red);
			price.setTextColor(baseActivity.getResources().getColor(R.color.white));
			sentiment.setBackgroundResource(R.color.white);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.red));
			sale_volume.setBackgroundResource(R.color.white);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.red));
			requestHotSelGoodsList(GET_PRICE_LIST);
			mCurFlag=GET_PRICE_LIST;
		}else if(arg0.getId()==R.id.sentiment){
			price.setBackgroundResource(R.color.white);
			price.setTextColor(baseActivity.getResources().getColor(R.color.red));
			sentiment.setBackgroundResource(R.color.red);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.white));
			sale_volume.setBackgroundResource(R.color.white);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.red));
			requestHotSelGoodsList(GET_SENTIMENT_LIST);
			mCurFlag=GET_SENTIMENT_LIST;
			
		}else if(arg0.getId()==R.id.sale_volume){
			price.setBackgroundResource(R.color.white);
			price.setTextColor(baseActivity.getResources().getColor(R.color.red));
			sentiment.setBackgroundResource(R.color.white);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.red));
			sale_volume.setBackgroundResource(R.color.red);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.white));
			requestHotSelGoodsList(GET_SALE_LIST);
			mCurFlag=GET_SALE_LIST;
		} 
	}
	//����������Ʒ�б�	
	private void getUpProductTime(){
		httpClient = new AsyncHttpClient();
		httpClient.get(GhhConst.GET_HOTSEL_TIME, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				LoggerSZ.i(TAG, "result = " + new String(arg2));
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
				onTimeDataReturn(new String(arg2));
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(baseActivity, "获取数据失败", Toast.LENGTH_SHORT).show();
				try {
					LoadingDlg.get().hide();
				} catch (Exception ex) {
					LoggerSZ.e(TAG, "" + ex.toString());
				}
			}
		});
		
		
	}
	public void requestHotSelGoodsList(int flag){
		RequestParams params = new RequestParams();
		String url=null;
		mPageNum=1;
		params.put("pagesize", PAGE_SIZE);
		params.put("pagenumber",mPageNum);
		switch (flag){
		case GET_PRICE_LIST:
			params.put("sortType",1);
			url=GhhConst.PRODUCT_BY_PRICE;
			break;
		case GET_SENTIMENT_LIST:
			params.put("sortType",2);
			url=GhhConst.PRODUCT_BY_SENTIMENT;
			break;
		case GET_SALE_LIST:
			params.put("sortType",3);
			url=GhhConst.PRODUCT_BY_SALE;
			break;
		default :
			break;
		}
		System.out.println("#####url:"+url);
		httpClient = new AsyncHttpClient();
		httpClient.post(url, params, new AsyncHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				hotselList.onRefreshComplete();
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
				hotselList.onRefreshComplete();
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
	private void onTimeDataReturn(String string){
				LoggerSZ.i(TAG, "###### TimeDataReturn ;" + string);
			Gson gson = new Gson();
			mHotselAdapter.setTimeMod(gson.fromJson(string,HotSelTimeMod.class));
			mHotselAdapter.notifyDataSetChanged();
	}
	private void onDataReturn(String string){
			try {
				JSONObject jsonObject = new JSONObject(string);
				jsonObject = jsonObject.getJSONObject("products");
				JSONArray arr = jsonObject.getJSONArray("data");
				ArrayList<HotSelMod> datas;
				datas = new ArrayList<HotSelMod>();
				HotSelMod temp=new HotSelMod();
				temp.id="-1";
				datas.add(temp);
				Gson gson = new Gson();
				for (int i = 0; i < arr.length(); i++) {
					datas.add(gson.fromJson(arr.getJSONObject(i)
							.toString(), HotSelMod.class));
				}
				mHotselAdapter.setHotSetList(datas);
				mHotselAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		System.out.println("view width :"+arg1.getWidth());
		System.out.println("view heigth :"+arg1.getHeight());
		Toast.makeText(baseActivity, position + ":" + mHotselAdapter.getItem(position), Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		 boolean lastItemVisible = (totalItemCount > 0) && 
				    (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
				    if (lastItemVisible) {
				    	hotselList.setPullUpToRefreshing(hotselList);
				    }
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
		// TODO Auto-generated method stub
		     //System.out.println("###onRefresh");
		     requestHotSelGoodsList(mCurFlag);
	}
	
}
