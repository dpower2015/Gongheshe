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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.BussinessGridViewAdapter;
import com.gongheshe.dialog.CityListPopWindow;
import com.gongheshe.javabean.BrandMainListMod;
import com.gongheshe.javabean.CityMod;
import com.gongheshe.util.HomeBrandClassView;
import com.gongheshe.util.HomeBrandClassView.OnBrandClickListener;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author ZhengZhiying<br>
 * @function 品牌
 */
public class BrandFragment extends BaseFragment implements OnClickListener {

	private String url = GhhConst.BASE_URL
			+ "pByCompanyTypeAndCityId.htm?pagesize=20&";
	private View view;
	private BussinessGridViewAdapter adapter;
	private Button bt_showCity;
	private CityListPopWindow cityListPopWindow;
	private BaseActivity baseActivity;
	// private BrandClassFragment classFragment;
	private View frame_class;
	private XListView xlistview_brand;
	private static CityMod curCityMod = null;
	private int curPageNumber = 1;
	private String[] classId = new String[] { "1", "1" };
	private BrandSecondFragment brandSecondF;
	private HomeBrandClassView brandClassView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_brand, container, false);
		xlistview_brand = (XListView) view.findViewById(R.id.xlistview_brand);
		adapter = new BussinessGridViewAdapter(getActivity());
		xlistview_brand.setAdapter(adapter);
		bt_showCity = (Button) view.findViewById(R.id.bt_brand_show_city);
		bt_showCity.setOnClickListener(this);
		view.findViewById(R.id.bt_class).setOnClickListener(this);
		baseActivity = (BaseActivity) getActivity();
		frame_class = view.findViewById(R.id.frame_class);
		frame_class.setVisibility(View.GONE);
		xlistview_brand.setVisibility(View.VISIBLE);
		initHomeBrandClassView();
		setListenerCityListPopWindow();
		setListenerXListView();
		curPageNumber = 1;
		if (cityListPopWindow.citys != null) {
			if (curCityMod == null || curCityMod.id == 1) {
				if(cityListPopWindow.citys.size()>0){
					curCityMod = cityListPopWindow.citys.get(0);
				}
			}
		} else {
			curCityMod = new CityMod();
			curCityMod.id = 1;
			curCityMod.name = "error";
		}
		requestWebData(curPageNumber, null, 1 + "", 1 + "");
		brandSecondF = new BrandSecondFragment();
		// requestWebData(1, curCityMod.id + "", classId[0], classId[1]);
		return view;
	}

	private void initHomeBrandClassView() {
		brandClassView = new HomeBrandClassView(view);
		brandClassView.setOnBrandClickListener(new OnBrandClickListener() {

			@Override
			public void onBrandClick(String firstClassId, String secondClassId,
					String name) {
				frame_class.setVisibility(View.GONE);
				xlistview_brand.setVisibility(View.VISIBLE);
				adapter.cleanDatas();
				requestWebData(1, curCityMod.id + "", firstClassId,
						secondClassId);
			}
		});
	}

	private void setListenerXListView() {
		xlistview_brand.setPullLoadEnable(true);
		xlistview_brand.setPullRefreshEnable(false);
		adapter.setOnBussItemClickListener(new BussinessGridViewAdapter.OnBussItemClickListener() {

			@Override
			public void onBussItemClick(int position) {
				BrandMainListMod data = adapter.getListData().get(position);
				brandSecondF = new BrandSecondFragment();
				brandSecondF.setBrandMainListMod(data);
				// if(getActivity().getSupportFragmentManager().getFragments().contains(brandSecondF)){
				// getActivity().getSupportFragmentManager().beginTransaction().remove(brandSecondF).commit();
				// ToastUtil.showToast(getActivity(), "已经包含");
				// }
				baseActivity.addFragment(brandSecondF);
				// baseActivity.replaceFragment(brandSecondF, true);
			}
		});

		xlistview_brand
				.setXListViewListener(new XListView.IXListViewListener() {

					@Override
					public void onRefresh() {
					}

					@Override
					public void onLoadMore() {
						curPageNumber++;
						if (curCityMod == null) {
							requestWebData(curPageNumber, null, classId[0],
									classId[1]);
						} else {
							requestWebData(curPageNumber, curCityMod.id + "",
									classId[0], classId[1]);
						}
					}
				});
	}

	// private void initBrandClassFragment() {
	// classFragment = new BrandClassFragment();
	// classFragment
	// .setOnBrankClickListener(new BrandClassFragment.OnBrankClickListener() {
	//
	// @Override
	// public void onBrankClick(int firstClassId, int secondClassId) {
	// classId[0] = firstClassId + "";
	// classId[1] = secondClassId + "";
	// String cityId = null;
	// if (curCityMod != null) {
	// cityId = curCityMod.id + "";
	// }
	// view.findViewById(R.id.bt_class).performClick();
	// adapter.cleanDatas();
	// requestWebData(1, cityId, classId[0], classId[1]);
	// }
	// });
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_brand_show_city:
			if (cityListPopWindow.isShowing()) {
				cityListPopWindow.dismiss();
			} else {
				cityListPopWindow.show(v);
			}
			break;
		case R.id.bt_class:
			if (frame_class.getVisibility() == View.VISIBLE) {
				frame_class.setVisibility(View.GONE);
				xlistview_brand.setVisibility(View.VISIBLE);
			} else {
				frame_class.setVisibility(View.VISIBLE);
				xlistview_brand.setVisibility(View.GONE);
				// initBrandClassFragment();
				// replaceFragment(classFragment, true);
			}

			break;
		default:
			break;
		}
	}

	// public void replaceFragment(BrandClassFragment fragment,
	// boolean isAddToBackStack) {
	// FragmentTransaction fragmentTransaction;
	// fragmentTransaction = getActivity().getSupportFragmentManager()
	// .beginTransaction();
	// // fragmentTransaction = getChildFragmentManager().beginTransaction();
	// fragmentTransaction.replace(R.id.frame_class, fragment);
	// fragmentTransaction.commit();
	// }

	private void setListenerCityListPopWindow() {
		cityListPopWindow = CityListPopWindow.getIns(getActivity());
		cityListPopWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isVisible()) {
					cityListPopWindow.dismiss();
					curCityMod = cityListPopWindow.citys.get(position);
					bt_showCity.setText(curCityMod.name);
					adapter.cleanDatas();
					requestWebData(1, curCityMod.id + "", classId[0],
							classId[1]);
				}
			}
		});
	}

	private void requestWebData(int pagenumber, String cityId,
			String typeOneId, String typeTwoId) {
		if (pagenumber == 1) {
			adapter.cleanDatas();
		}
		if (cityId == null) {
			cityId = cityListPopWindow.citys.get(0).id + "";
		}
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		StringBuffer buffer = new StringBuffer(url);
		buffer.append("pagenumber=" + pagenumber);
		buffer.append("&cityId=" + cityId);
		buffer.append("&typeOneId=" + typeOneId);
		buffer.append("&typeTwoId=" + typeTwoId);
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				xlistview_brand.stopLoadMore();
				try {
					String data = new String(response);
					JSONObject jsonObject = new JSONObject(data);
					jsonObject = jsonObject.getJSONObject("brands");
					JSONArray arr = jsonObject.getJSONArray("data");
					if (arr == null || arr.length() == 0) {
						adapter.notifyDataSetChanged();
						if (curPageNumber == 1) {
							ToastUtil.showToast(getActivity(),
									getString(R.string.no_data));
							return;
						}
						ToastUtil.showToast(getActivity(),
								getString(R.string.no_more_data_loaded));
						return;
					}
					Gson gson = new Gson();
					BrandMainListMod mod;
					List<BrandMainListMod> mods;
					ToastUtil.showToast(getActivity(),
							getString(R.string.load_finish));
					mods = new ArrayList<BrandMainListMod>();
					for (int i = 0; i < arr.length(); i++) {
						mod = gson.fromJson(arr.get(i).toString(),
								BrandMainListMod.class);
						mods.add(mod);
					}
					adapter.appendMods(mods);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					ToastUtil.showToast(getActivity(),
							getString(R.string.server_error) + "(error:1001)");
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				ToastUtil.showToast(getActivity(),
						getString(R.string.server_error) + "(error:404)");
				xlistview_brand.stopLoadMore();
			}
		};
		LoggerSZ.i("BrandFragment", buffer.toString());
		httpClient.get(buffer.toString(), handler);
	}

}
