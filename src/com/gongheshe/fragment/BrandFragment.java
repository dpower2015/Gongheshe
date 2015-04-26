package com.gongheshe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.gongheshe.R;
import com.gongheshe.adapter.BussinessGridViewAdapter;

/**
 * @author ZhengZhiying<br>
 * @function 	品牌
 */
public class BrandFragment extends BaseFragment {
	
	private View view;
	private GridView mBussinesList;
	private BussinessGridViewAdapter mBussinesListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//view = inflater.inflate(R.layout.my_collect, container, false);
		view = inflater.inflate(R.layout.fragment_brand, container, false);
		mBussinesList=(GridView)view.findViewById(R.id.merchant_list);
		mBussinesListAdapter=new BussinessGridViewAdapter(getActivity());
		mBussinesList.setAdapter(mBussinesListAdapter);
		return view;
	}
	
}
