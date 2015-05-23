package com.gongheshe.util;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.gongheshe.R;

public class HomeBrandClassView implements OnClickListener {

	private View view;
	private OnBrandClickListener listener;
	private ScrollView sv_homebrand;
	private View gl_01;
	private View gl_02;
	private View gl_03;
	private View gl_04;
	private View gl_05;
	private View gl_06;
	private View gl_07;

	public interface OnBrandClickListener {
		public void onBrandClick(String firstClassId, String secondClassId,
				String name);
	}

	public HomeBrandClassView(View view) {
		this.view = view;
		gl_01 = view.findViewById(R.id.gl_01);
		gl_02 = view.findViewById(R.id.gl_02);
		gl_03 = view.findViewById(R.id.gl_03);
		gl_04 = view.findViewById(R.id.gl_04);
		gl_05 = view.findViewById(R.id.gl_05);
		gl_06 = view.findViewById(R.id.gl_06);
		gl_07 = view.findViewById(R.id.gl_07);
		// frame_class sv_homebrand
		sv_homebrand = (ScrollView) view.findViewById(R.id.frame_class);
		//
		int firstId = 0;
		int secondId = 0;
		//
		firstId++;
		initViewById(R.id.bt_structure_1, firstId, ++secondId);
		initViewById(R.id.bt_structure_2, firstId, ++secondId);
		initViewById(R.id.bt_structure_3, firstId, ++secondId);
		initViewById(R.id.bt_structure_4, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_elec1, firstId, ++secondId);
		initViewById(R.id.bt_elec2, firstId, ++secondId);
		initViewById(R.id.bt_elec3, firstId, ++secondId);
		initViewById(R.id.bt_elec4, firstId, ++secondId);
		initViewById(R.id.bt_elec5, firstId, ++secondId);
		initViewById(R.id.bt_elec6, firstId, ++secondId);
		initViewById(R.id.bt_elec7, firstId, ++secondId);
		initViewById(R.id.bt_elec8, firstId, ++secondId);
		initViewById(R.id.bt_elec9, firstId, ++secondId);
		initViewById(R.id.bt_elec10, firstId, ++secondId);
		initViewById(R.id.bt_elec11, firstId, ++secondId);
		initViewById(R.id.bt_elec12, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_bath1, firstId, ++secondId);
		initViewById(R.id.bt_bath2, firstId, ++secondId);
		initViewById(R.id.bt_bath3, firstId, ++secondId);
		initViewById(R.id.bt_bath4, firstId, ++secondId);
		initViewById(R.id.bt_bath5, firstId, ++secondId);
		initViewById(R.id.bt_bath6, firstId, ++secondId);
		initViewById(R.id.bt_bath7, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_wood_1, firstId, ++secondId);
		initViewById(R.id.bt_wood_2, firstId, ++secondId);
		initViewById(R.id.bt_wood_3, firstId, ++secondId);
		initViewById(R.id.bt_wood_4, firstId, ++secondId);
		initViewById(R.id.bt_wood_5, firstId, ++secondId);
		initViewById(R.id.bt_wood_6, firstId, ++secondId);
		initViewById(R.id.bt_wood_7, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_face_1, firstId, ++secondId);
		initViewById(R.id.bt_face_2, firstId, ++secondId);
		initViewById(R.id.bt_face_3, firstId, ++secondId);
		initViewById(R.id.bt_face_4, firstId, ++secondId);
		initViewById(R.id.bt_face_5, firstId, ++secondId);
		initViewById(R.id.bt_face_6, firstId, ++secondId);
		initViewById(R.id.bt_face_7, firstId, ++secondId);
		initViewById(R.id.bt_face_8, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_lamp_1, firstId, ++secondId);
		initViewById(R.id.bt_lamp_2, firstId, ++secondId);
		initViewById(R.id.bt_lamp_3, firstId, ++secondId);
		initViewById(R.id.bt_lamp_4, firstId, ++secondId);
		initViewById(R.id.bt_lamp_5, firstId, ++secondId);
		//
		firstId++;
		secondId = 0;
		initViewById(R.id.bt_furnish_1, firstId, ++secondId);
		initViewById(R.id.bt_furnish_2, firstId, ++secondId);
		initViewById(R.id.bt_furnish_3, firstId, ++secondId);
		initViewById(R.id.bt_furnish_4, firstId, ++secondId);
		initViewById(R.id.bt_furnish_5, firstId, ++secondId);
		initViewById(R.id.bt_furnish_6, firstId, ++secondId);
		//
		view.findViewById(R.id.layout_toshow1).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow2).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow3).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow4).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow5).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow6).setOnClickListener(this);
		view.findViewById(R.id.layout_toshow7).setOnClickListener(this);
	}

	private void initViewById(int resId, int firstClassId, int secondClassId) {
		View v = view.findViewById(resId);
		v.setTag(new ViewHolder(firstClassId + "", secondClassId + ""));
		v.setOnClickListener(this);
	}

	public void setOnBrandClickListener(OnBrandClickListener listener) {
		this.listener = listener;
	}

	class ViewHolder {
		String firstClassId;
		String secondClassId;

		public ViewHolder(String firstClassId, String secondClassId) {
			this.firstClassId = firstClassId;
			this.secondClassId = secondClassId;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_toshow1:
			setVisibleView(gl_01);
			break;
		case R.id.layout_toshow2:
			setVisibleView(gl_02);
			break;
		case R.id.layout_toshow3:
			setVisibleView(gl_03);
			break;
		case R.id.layout_toshow4:
			setVisibleView(gl_04);
			break;
		case R.id.layout_toshow5:
			setVisibleView(gl_05);
			scrollToBottom();
			break;
		case R.id.layout_toshow6:
			setVisibleView(gl_06);
			scrollToBottom();
			break;
		case R.id.layout_toshow7:
			setVisibleView(gl_07);
			scrollToBottom();
			break;
		default:
			ViewHolder holder;
			holder = (ViewHolder) v.getTag();
			if (listener != null) {
				listener.onBrandClick(holder.firstClassId,
						holder.secondClassId, ((Button) v).getText().toString());
			}
			break;
		}
	}

	private void setVisibleView(View view) {
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	private void scrollToBottom() {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				sv_homebrand.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
}
