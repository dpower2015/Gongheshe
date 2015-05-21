package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.ShopDetailAdapter;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.javabean.CityMod;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.PullStagGridViewUT;
import com.gongheshe.util.PullStagGridViewUT.OnStagGridViewListener;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ShopDetail extends BaseFragment implements View.OnClickListener {
	
	private View view;
	private BaseActivity baseActivity;
	private PullToRefreshStaggeredGridView stag_gridview;
	private ShopDetailAdapter adapter;
	protected String tag = "ShopDetail";
	private StringBuffer url = new StringBuffer(GhhConst.HomeSecondActivity);
	private int firstClassId = 1;
	private int secondClassId = 1;
	private CityMod cityMod;
	private int pageNumber = 1;

	private CityListPopWindow cityListPopWindow;
	private Button bt_showCity;
	private PullStagGridViewUT stagGridViewUT;
	private String title = "";
	// private BaseActivity baseActivity;
	private ProductThirdDetailFragment thirdlFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.shop_detail, container, false);
		baseActivity = (BaseActivity) getActivity();
		stag_gridview = (PullToRefreshStaggeredGridView) view
				.findViewById(R.id.stag_gridview);
		stagGridViewUT = new PullStagGridViewUT(getActivity(), stag_gridview);
		adapter = new ShopDetailAdapter(getActivity());
		adapter.title = title;
		stagGridViewUT.setAdapter(adapter);
		setListenerStagGridViewUT();
		bt_showCity = (Button) view.findViewById(R.id.bt_to_show_citys);
		bt_showCity.setOnClickListener(this);
		thirdlFragment = new ProductThirdDetailFragment();
		view.findViewById(R.id.ibtn_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						baseActivity.onBackPressed();
					}
				});
		requestWebServer(pageNumber);
		setListenerCityListPopWindow();

		return view;
	}

	private void setListenerStagGridViewUT() {
		stagGridViewUT.setGridViewListener(new OnStagGridViewListener() {

			@Override
			public void onRefreshStart() {
				requestWebServer(++pageNumber);
			}

			@Override
			public void onItemClick(int position) {
				if(position > 0){
					thirdlFragment.setTypeClassMod(adapter.datas.get(position-1));
					baseActivity.replaceFragment(thirdlFragment, true);
				}
				
				// ToastUtil.showToast(getActivity(), "点击事件" + position);

			}
		});
	}

	private void setListenerCityListPopWindow() {
		cityListPopWindow = CityListPopWindow.getIns(getActivity());
		cityListPopWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isVisible()) {
					cityListPopWindow.dismiss();
					bt_showCity.setText(cityListPopWindow.citys.get(position).name);
				}
			}
		});
	}

	/**
	 * @author ZhengZhiying
	 * @param firstClassId
	 * @param secondClassId
	 * @return
	 */
	public ShopDetail setIntentData(int firstClassId, int secondClassId,
			CityMod cityMod, String title) {
		this.firstClassId = firstClassId;
		this.secondClassId = secondClassId;
		this.cityMod = cityMod;
		this.title = title;
		return this;
	}

	/**
	 * http://121.40.219.222/phone/data/pByType.htm?pagesize=20&pagenumber=1&
	 * firstClassId=3&secondClassId=1&cityId=1
	 * 
	 * @param pageNumber
	 *            1,2,3,4,....
	 */
	private void requestWebServer(int pageNumber) {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		// AsyncHttpResponseHandler
		url.append("?");
		url.append("pagesize=" + 20);
		url.append("&");
		url.append("pagenumber=" + pageNumber);
		url.append("&");
		url.append("firstClassId=" + firstClassId);
		url.append("&");
		url.append("secondClassId=" + secondClassId);
		if (cityMod != null) {
			url.append("&");
			url.append("cityId=" + cityMod.id);
		} else {
			ToastUtil.showToast(getActivity(), "error for no cityId");
		}
		// httpClient.get("http://www.baidu.com", getResponseHandler());
		Log.i(tag, "访问：" + url);
		httpClient.get(url.toString(), getResponseHandler());
	}

	private AsyncHttpResponseHandler getResponseHandler() {
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				stagGridViewUT.dismissLoadingView();
//				Log.i(tag, new String(response));
				try {
					JSONObject jsonObject;
					jsonObject = new JSONObject(new String(response).replace(
							"\n", ""));
					JSONArray arr;
					arr = jsonObject.getJSONObject("products").getJSONArray(
							"data");
					Gson gson;
					gson = new Gson();
					ProductMod data;
					for (int i = 0; i < arr.length(); i++) {
						data = gson.fromJson(arr.get(i).toString(),
								ProductMod.class);
						adapter.datas.add(data);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					pageNumber--;
					ToastUtil.showToast(getActivity(),
							getString(R.string.server_error));
					// TODO Auto-generated catch block
					// ToastUtil.showToast(getActivity(),
					// "json error："+e.toString());
					e.printStackTrace();

				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				pageNumber--;
				stagGridViewUT.dismissLoadingView();
				ToastUtil.showToast(getActivity(),
						getString(R.string.no_more_data_loaded));

			}
		};
		return handler;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_to_show_citys:
			if (cityListPopWindow.isShowing()) {
				cityListPopWindow.dismiss();
			} else {
				cityListPopWindow.show(v);
			}
			break;

		default:
			break;
		}
	}

}
