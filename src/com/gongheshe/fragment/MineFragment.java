package com.gongheshe.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gongheshe.R;

public class MineFragment extends BaseFragment implements OnClickListener{
	
	View view;
	LinearLayout mCopyrightView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		mCopyrightView =(LinearLayout)view.findViewById(R.id.copyright_id);
		mCopyrightView.setOnClickListener(this);
		return view;
	}
	@Override 
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.copyright_id:
			CopyrightFragment cprightFragment = new CopyrightFragment();
			FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.setCustomAnimations(R.anim.fragment_left,
					R.anim.fragment_right);
			//fragmentTransaction.add(R.id, fragment)
			//fragmentTransaction.commit();
			break;
		default:
			break;
		 
		}
		
	}

}
