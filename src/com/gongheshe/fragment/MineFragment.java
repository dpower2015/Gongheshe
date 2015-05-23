package com.gongheshe.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.activity.LoginActivity;
import com.gongheshe.util.ShareSave;

public class MineFragment extends BaseFragment implements OnClickListener{
	
	View view;
	private BaseActivity baseActivity;
	private final int REQUEST_IMG=1;
	private ShareSave shareSave = ShareSave.get();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		view.findViewById(R.id.myproject).setOnClickListener(this);
		view.findViewById(R.id.modify_pw).setOnClickListener(this);
		view.findViewById(R.id.modify_head_img).setOnClickListener(this);
		view.findViewById(R.id.mybooks).setOnClickListener(this);
		view.findViewById(R.id.mystore).setOnClickListener(this);
		view.findViewById(R.id.exit_curuser).setOnClickListener(this);
		baseActivity=(BaseActivity)getActivity();
		return view;
	}
	@Override 
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.myproject:
			MyprojectFragment myproject=new MyprojectFragment();
			baseActivity.replaceFragment(myproject, true);
			break;
		case R.id.modify_head_img:
			Intent intent = new Intent(Intent.ACTION_PICK);  
		    intent.setType("image/*");//相片类型  
		    startActivityForResult(intent, REQUEST_IMG);  
            // REQUEST_CAMERA =0;
			break;
		case R.id.modify_pw:
			ModifypwFragment modifyFragment = new ModifypwFragment();
			baseActivity.replaceFragment(modifyFragment, true);
			break;
		case R.id.mybooks:
			MybooksFragment mybooksFragment =new MybooksFragment();
			baseActivity.replaceFragment(mybooksFragment, true);
			break;
		case R.id.mystore:
			MystoreFragment mystoreFragment =new MystoreFragment();
			baseActivity.replaceFragment(mystoreFragment, true);
			break;
		case R.id.exit_curuser:
			shareSave.setPsdword("");
			shareSave.setUserName("");
			shareSave.setUid("");
			baseActivity.startActivity(new Intent(baseActivity, LoginActivity.class));
			baseActivity.finish();
			break;
		default:
			break;
		 
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			
		if (requestCode == REQUEST_IMG) {             
                Uri uri = data.getData();  
                //获取头像图片
            
        	} 
			
		}
}
