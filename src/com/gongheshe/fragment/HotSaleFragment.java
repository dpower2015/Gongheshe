package com.gongheshe.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.solo.waterfall.WaterfallSmartView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gongheshe.R;
import com.gongheshe.adapter.HotSelAdapter;
import com.gongheshe.util.cache.ACache;
import com.googheshe.entity.GhhConst;
import com.googheshe.entity.HotSelEntity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HotSaleFragment extends BaseFragment {
	
	private View view;
	private WaterfallSmartView mWaterfall;
	private HotSelAdapter mHotselAdapter;
	private ACache mCache;
	private AsyncHttpClient client;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_hotsell, container, false);
		
		mWaterfall=(WaterfallSmartView)view.findViewById(R.id.hotsel_shop_list);
		mHotselAdapter=new HotSelAdapter(getActivity());
		mHotselAdapter.setWaterfallView(mWaterfall);
		mWaterfall.setAdapter(mHotselAdapter);
		mCache = ACache.get(getActivity());
		internetData();
		
		return view;
	}
	private void internetData() {
		client = new AsyncHttpClient();
		//test
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		mHotselAdapter.add(new HotSelEntity(),30,30);
		//end
	}
	//请求热卖产品列表
	public void requestHotSelGoodsList(){
			String url = GhhConst.GET_HOT_SEL_LIST;
			String result = null;
			result = mCache.getAsString(url);
			if (result != null) {
				// return;
			}
			client.get(getActivity(), url, null, null,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] response, Throwable e) {
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] response) {
							onDataReturn(new String(response));
						}

						private void onDataReturn(String string) {
//							ArrayList<AsyncIndustryMod> datas;
//							datas = new ArrayList<AsyncIndustryMod>();
//							try {
//								AsyncIndustryMod data;
//								JSONObject jsonObject = new JSONObject(string);
//								jsonObject = jsonObject.getJSONObject("Data");
//								JSONArray arr = jsonObject.getJSONArray("dataList");
//								for (int i = 0; i < arr.length(); i++) {
//									data = new AsyncIndustryMod();
//									data.fillByJson(arr.getJSONObject(i));
//									datas.add(data);
//								}
//								adapter.setBusinessAsynMods(datas);
//								adapter.notifyDataSetChanged();
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						}

					});

		}
}
