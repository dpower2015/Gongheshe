package com.gongheshe.fragment;

import org.apache.http.Header;
import org.solo.waterfall.WaterfallSmartView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.HotSelAdapter;
import com.gongheshe.util.cache.ACache;
import com.googheshe.entity.GhhConst;
import com.googheshe.entity.HotSelEntity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HotSaleFragment extends BaseFragment implements OnItemClickListener ,OnCheckedChangeListener{
	
	private View view;
	private WaterfallSmartView mWaterfall;
	private HotSelAdapter mHotselAdapter;
	private ACache mCache;
	private AsyncHttpClient client;
	private BaseActivity baseActivity;
	private CheckBox price,sentiment,sale_volume;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_hotsell, container, false);
		mWaterfall=(WaterfallSmartView)view.findViewById(R.id.hotsel_shop_list);
		price=((CheckBox)view.findViewById(R.id.price));
		sentiment=((CheckBox)view.findViewById(R.id.sentiment));
		sale_volume=((CheckBox)view.findViewById(R.id.sale_volume));
		sale_volume.setOnCheckedChangeListener(this);
		sentiment.setOnCheckedChangeListener(this);
		price.setOnCheckedChangeListener(this);
		mHotselAdapter=new HotSelAdapter(getActivity());
		mHotselAdapter.setWaterfallView(mWaterfall);
		mWaterfall.setAdapter(mHotselAdapter);
		mWaterfall.setOnItemClickListener(this);
		mCache = ACache.get(getActivity());
		baseActivity =(BaseActivity)getActivity();
		internetData();
		return view;
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.price){
			price.setBackgroundResource(R.color.red_check);
			price.setTextColor(baseActivity.getResources().getColor(R.color.white));
			sentiment.setBackgroundResource(R.color.white);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
			sale_volume.setBackgroundResource(R.color.white);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
		}else if(arg0.getId()==R.id.sentiment){
			price.setBackgroundResource(R.color.white);
			price.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
			sentiment.setBackgroundResource(R.color.red_check);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.white));
			sale_volume.setBackgroundResource(R.color.white);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
		}else if(arg0.getId()==R.id.sale_volume){
			price.setBackgroundResource(R.color.white);
			price.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
			sentiment.setBackgroundResource(R.color.white);
			sentiment.setTextColor(baseActivity.getResources().getColor(R.color.red_check));
			sale_volume.setBackgroundResource(R.color.red_check);
			sale_volume.setTextColor(baseActivity.getResources().getColor(R.color.white));
		} 
		
		
		
		System.out.println("...."+arg0.toString());
	}
	private void internetData() {
		client = new AsyncHttpClient();
		HotSelEntity temp=new HotSelEntity();
		temp.flag=1;
		//test
		mHotselAdapter.add(temp,540,150);
		mHotselAdapter.add(new HotSelEntity(),540,337);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		mHotselAdapter.add(new HotSelEntity(),540,357);
		//end
	}
	//����������Ʒ�б�
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
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		System.out.println("view width :"+arg1.getWidth());
		System.out.println("view heigth :"+arg1.getHeight());
		Toast.makeText(baseActivity, position + ":" + mHotselAdapter.getItem(position), Toast.LENGTH_SHORT).show();
	}
	
}
