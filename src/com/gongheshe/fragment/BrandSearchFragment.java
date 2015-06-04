package com.gongheshe.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import zy.zh.xListView.XListView;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.BrandSearchAdapter;
import com.gongheshe.adapter.BussinessGridViewAdapter;
import com.gongheshe.javabean.BrandMainListMod;
import com.gongheshe.javabean.BrandSearchMod;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author ZhengZhiying<br>
 * @function search for brand
 */
public class BrandSearchFragment extends BaseFragment implements
		OnClickListener {

	private String url = GhhConst.BASE_URL + "pByNameAndCityId.htm?";
	private View view;
	private BaseActivity baseActivity;
	private TextView txt_title;
	private String strTitle = "";
	private XListView xlistview_search;
	private BrandSearchAdapter adapter;
	private int cityId;
	protected int curPageNumber = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_brand_search, container,
				false);
		baseActivity = (BaseActivity) getActivity();
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setText(strTitle);
		view.findViewById(R.id.bt_back).setOnClickListener(this);
		xlistview_search = (XListView) view.findViewById(R.id.xlistview_search);
		adapter = new BrandSearchAdapter(getActivity());
		xlistview_search.setAdapter(adapter);
		setListenerXListView();
		requestWebServer(curPageNumber);
		return view;
	}

	public void setTitle(String title, int cityId) {
		strTitle = title;
		this.cityId = cityId;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_back:
			baseActivity.onBackPressed();
			break;

		default:
			break;
		}
	}

	private void requestWebServer(int pagenumber) {
		if (pagenumber == 1) {
			adapter.cleanDatas();
		}
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		StringBuffer buffer = new StringBuffer(url);
		buffer.append("pagenumber=" + pagenumber);
		buffer.append("&cityId=" + cityId);
		buffer.append("&pagesize=" + 20);
		buffer.append("&productName=" + strTitle);
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				xlistview_search.stopLoadMore();
				try {
					String data = new String(response);
					JSONObject jsonObject = new JSONObject(data);
					jsonObject = jsonObject.getJSONObject("products");
					JSONArray arr = jsonObject.getJSONArray("data");
					if (arr == null || arr.length() == 0) {
						if (curPageNumber == 1) {
							ToastUtil.showToast(getActivity(),
									getString(R.string.no_data));
						}else{
							ToastUtil.showToast(getActivity(),
									getString(R.string.no_more_data_loaded));
						}
						return;
					}
					
					Gson gson = new Gson();
					BrandSearchMod mod;
					List<BrandSearchMod> mods;
					ToastUtil.showToast(getActivity(),
							getString(R.string.load_finish));

					mods = new ArrayList<BrandSearchMod>();
					for (int i = 0; i < arr.length(); i++) {
						mod = gson.fromJson(arr.get(i).toString(),
								BrandSearchMod.class);
						mods.add(mod);
					}
					adapter.appendMods(mods);
					adapter.notifyDataSetChanged();
					return;
				} catch (Exception e) {
					// TODO: handle exception
					ToastUtil.showToast(getActivity(),
							getString(R.string.server_error) + "(error:1001)");
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				curPageNumber--;
				ToastUtil.showToast(getActivity(),
						getString(R.string.server_error) + "(error:404)");
				xlistview_search.stopLoadMore();
			}
		};

		LoggerSZ.i("BrandSearchFragment", buffer.toString());
		httpClient.get(buffer.toString(), handler);
	}

	private void setListenerXListView() {
		xlistview_search.setPullLoadEnable(true);
		xlistview_search.setPullRefreshEnable(false);
		adapter.setOnBussItemClickListener(new BrandSearchAdapter.OnBussItemClickListener() {

			@Override
			public void onBussItemClick(int position) {
				BrandSearchMod data = adapter.getListData().get(position);
				ProductThirdDetailFragment thirdlFragment;
				thirdlFragment = new ProductThirdDetailFragment();
				ProductMod productMod = new ProductMod();
				// requestWebServerTotalInfo(data.id);
				// imgLoader.show(data.androidNote2ImagesUrl, img_picture);
				productMod.id = data.id;
				productMod.androidNote2ImagesUrl = data.androidNote2ImagesMinUrl;
				thirdlFragment.setTypeClassMod(productMod);

				baseActivity.replaceFragment(thirdlFragment, false);

				// BrandSecondFragment brandSecondF;
				// brandSecondF = new BrandSecondFragment();
				// brandSecondF.setBrandMainListMod(data);
				// if(getActivity().getSupportFragmentManager().getFragments().contains(brandSecondF)){
				// getActivity().getSupportFragmentManager().beginTransaction().remove(brandSecondF).commit();
				// ToastUtil.showToast(getActivity(), "已经包含");
				// }
				// baseActivity.addFragment(brandSecondF);
				// baseActivity.replaceFragment(brandSecondF, true);
			}
		});

		xlistview_search
				.setXListViewListener(new XListView.IXListViewListener() {

					@Override
					public void onRefresh() {
					}

					@Override
					public void onLoadMore() {
						curPageNumber++;
						// if (curCityMod == null) {
						requestWebServer(curPageNumber);
						// } else {
						// requestWebData(curPageNumber, curCityMod.id + "",
						// classId[0], classId[1]);
						// }
					}
				});
	}

}
