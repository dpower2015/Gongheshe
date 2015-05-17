package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.gongheshe.R;
import com.gongheshe.adapter.BussinessGridViewAdapter;
import com.gongheshe.dialog.CityListPopWindow;

/**
 * @author ZhengZhiying<br>
 * @function 品牌
 */
public class BrandFragment extends BaseFragment implements OnClickListener {

	private View view;
	private GridView mBussinesList;
	private BussinessGridViewAdapter mBussinesListAdapter;
	private Button bt_showCity;
	private CityListPopWindow cityListPopWindow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view = inflater.inflate(R.layout.my_collect, container, false);
		view = inflater.inflate(R.layout.fragment_brand, container, false);
		mBussinesList = (GridView) view.findViewById(R.id.merchant_list);
		mBussinesListAdapter = new BussinessGridViewAdapter(getActivity());
		mBussinesList.setAdapter(mBussinesListAdapter);
		bt_showCity = (Button) view.findViewById(R.id.bt_brand_show_city);
		bt_showCity.setOnClickListener(this);
		setListenerCityListPopWindow();
		return view;
	}

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

		default:
			break;
		}
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

}
