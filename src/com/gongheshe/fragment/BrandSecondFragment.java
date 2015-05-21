package com.gongheshe.fragment;

import java.util.ArrayList;
import java.util.List;

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

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.BrandSecondListAdapter;
import com.gongheshe.javabean.BrandMainListMod;
import com.gongheshe.javabean.BrandSecondMod;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author ZhengZhiying<br>
 * @function brand/brand list/brand detail
 */
// 使用卫浴类/脸盆 ，有数据
public class BrandSecondFragment extends BaseFragment implements
		OnClickListener {

	private String url = GhhConst.BASE_URL + "pProductByBrandId.htm?";
	private View view;
	private BaseActivity baseActivity;
	private BrandMainListMod data;
	private XListView xlistview;
	private BrandSecondListAdapter adapter;
	private int pageNumber = 1;
	private List<BrandSecondMod> datas = new ArrayList<BrandSecondMod>();
	private ProductThirdDetailFragment thirdlFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_brand_second, container,
				false);
		baseActivity = (BaseActivity) getActivity();
		// TextView textView1 = (TextView) view.findViewById(R.id.textView1);
		// textView1.setText(data.name);
		xlistview = (XListView) view.findViewById(R.id.xlistview_branddetail);
		view.findViewById(R.id.bt_return).setOnClickListener(this);
		setXListViewParams();
		pageNumber = 1;
		requestData(data.id, pageNumber);
		thirdlFragment = new ProductThirdDetailFragment();
		return view;
	}

	public void setBrandMainListMod(BrandMainListMod data) {
		this.data = data;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_return:
			baseActivity.onBackPressed();
			break;

		default:
			break;
		}
	}

	private void setXListViewParams() {
		xlistview.setPullLoadEnable(true);
		xlistview.setPullRefreshEnable(false);
		adapter = new BrandSecondListAdapter(getActivity());
		adapter.seBrandMainListMod(data);
		xlistview.setAdapter(adapter);
		xlistview.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadMore() {
				pageNumber++;
				requestData(data.id, pageNumber);
			}
		});
		adapter.setOnBussItemClickListener(new BrandSecondListAdapter.OnBussItemClickListener() {

			@Override
			public void onBussItemClick(int position) {
				// position = position - 2;
				// ToastUtil.showToast(getActivity(), datas.get(position).name
				// + "," + datas.get(position).description);
				// data
				// ToastUtil.showToast(getActivity(), "position="+position);
				ProductMod typedata = new ProductMod();
				// typedata.id = Integer.valueOf(data.id);
				typedata.id = datas.get(position).id;
				// typedata.androidNote2ImagesUrl = data.androidNote2Top;
				typedata.androidNote2ImagesUrl = datas.get(position).androidNote2ImagesMinUrl;
				thirdlFragment.setTypeClassMod(typedata);
				baseActivity.replaceFragment(thirdlFragment, true);
			}
		});
	}

	private void requestData(String brandId, int pageNumber) {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		// AsyncHttpResponseHandler
		StringBuffer buffer = new StringBuffer(url);
		buffer.append("brandId=" + brandId);
		buffer.append("&pagesize=" + 20);
		buffer.append("&pagenumber=" + pageNumber);
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				onGetSuccess(statusCode, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				xlistview.stopLoadMore();
				ToastUtil.showToast(getActivity(),
						getString(R.string.loading_fail) + "404");
			}
		};
		LoggerSZ.i("BrandSecondFragment", buffer.toString());
		httpClient.get(buffer.toString(), handler);
	}

	private void onGetSuccess(int statusCode, byte[] response) {
		xlistview.stopLoadMore();
		try {
			JSONArray arr;
			arr = new JSONObject(new String(response)).getJSONArray("products");
			if (arr == null || arr.length() < 1) {
				ToastUtil.showToast(getActivity(), getString(R.string.no_data));
				return;
			}
			if (pageNumber == 1) {
				adapter.cleanListData();
			}
			ArrayList<BrandSecondMod> temps = new ArrayList<BrandSecondMod>();
			Gson gson = new Gson();
			BrandSecondMod data;
			for (int i = 0; i < arr.length(); i++) {
				data = gson.fromJson(arr.getJSONObject(i).toString(),
						BrandSecondMod.class);
				datas.add(data);
				temps.add(data);
			}

			adapter.appendData(temps);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
