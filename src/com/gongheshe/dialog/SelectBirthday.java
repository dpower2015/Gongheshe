package com.gongheshe.dialog;

import java.util.Calendar;

import zy.zh.widget.WheelViewImp;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.gongheshe.R;
import com.example.widget.NumericWheelAdapter;
import com.example.widget.OnWheelChangedListener;
import com.example.widget.WheelView;

public class SelectBirthday extends PopupWindow implements OnClickListener {

	private Activity mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	public Button btn_submit, btn_cancel;
	private String age;
	private DateNumericAdapter monthAdapter, dayAdapter, yearAdapter;
	private WheelViewImp year, month, day;
	// private int mCurYear = 80, mCurMonth = 5, mCurDay = 14;// 2012-11-2
	private int mCurYear = 80, mCurMonth = 5, mCurDay = 14;
	private String[] dateType;
	private String tag = "SelectBirthday";
	private OnDatePickerListener datePickerListener;

	public interface OnDatePickerListener {
		public void onDatePick(int year, int month, int day);
	}

	public void setOnDatePickerListener(OnDatePickerListener datePickerListener) {
		this.datePickerListener = datePickerListener;
	}

	public SelectBirthday(Activity context) {
		super(context);
		mContext = context;
		this.age = "2012-9-25";
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.birthday, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		year = (WheelViewImp) mMenuView.findViewById(R.id.year);
		month = (WheelViewImp) mMenuView.findViewById(R.id.month);
		day = (WheelViewImp) mMenuView.findViewById(R.id.day);
		btn_submit = (Button) mMenuView.findViewById(R.id.submit);
		btn_cancel = (Button) mMenuView.findViewById(R.id.cancel);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		Calendar calendar = Calendar.getInstance();
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				Log.i(tag,
						"滚动事件：:" + year.getCurrentItem() + "-"
								+ month.getCurrentItem() + "-"
								+ day.getCurrentItem());
				updateDays(year, month, day);

			}
		};
		int curYear = calendar.get(Calendar.YEAR);
		if (age != null && age.contains("-")) {
			String str[] = age.split("-");
			mCurYear = 100 - (curYear - Integer.parseInt(str[0]));
			mCurMonth = Integer.parseInt(str[1]) - 1;
			mCurDay = Integer.parseInt(str[2]) - 1;
			;
		}
		dateType = mContext.getResources().getStringArray(R.array.date);
		monthAdapter = new DateNumericAdapter(context, 1, 12, 5);
		monthAdapter.setTextType(dateType[1]);
		month.setViewAdapter(monthAdapter);
		Log.i(tag, "设置时间mCurMonth=" + mCurMonth);
		// month.setCurrentItem(mCurMonth);
		month.addChangingListener(listener);
		// year

		yearAdapter = new DateNumericAdapter(context, curYear - 100,
				curYear + 100, 100 - 20);
		yearAdapter.setTextType(dateType[0]);
		year.setViewAdapter(yearAdapter);
		Log.i(tag, "设置时间mCurYear=" + mCurYear);
		// year.setCurrentItem(mCurYear);
		year.addChangingListener(listener);
		// day

		// updateDays(year, month, day);
		Log.i(tag, "设置时间mCurDay=" + mCurDay);
		// day.setCurrentItem(mCurDay);
		// updateDays(year, month, day);
		// setCurrent(mCurYear, mCurYear, mCurYear);
		setCurrent(2015, 5, 24);
		day.addChangingListener(listener);

		viewfipper.addView(mMenuView);
		viewfipper.setFlipInterval(6000000);
		this.setContentView(viewfipper);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
		this.update();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		viewfipper.startFlipping();
	}

	public void setCurrent(int mCurYear, int mCurMonth, int mCurDay) {
		// mCurYear = 100;
		mCurYear = mCurYear - 1915;
		// mCurMonth = 0;
		mCurMonth -= 1;
		// mCurDay = 0;
		mCurDay -= 1;
		year.setCurrentItem(mCurYear);
		month.setCurrentItem(mCurMonth);
		day.setCurrentItem(mCurDay);
		updateDays(year, month, day);
	}

	private void updateDays(WheelViewImp year, WheelViewImp month,
			WheelViewImp day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dayAdapter = new DateNumericAdapter(mContext, 1, maxDays,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		int years = calendar.get(Calendar.YEAR) - 100;
		age = years + "-" + (month.getCurrentItem() + 1) + "-"
				+ (day.getCurrentItem() + 1);
		Log.i(tag, "age : " + age);
		// ZhengZhiying
		if (datePickerListener != null) {
			datePickerListener.onDatePick(years, (month.getCurrentItem() + 1),
					(day.getCurrentItem() + 1));
		}
		Log.i(tag,
				"updateDays : " + calendar.get(Calendar.YEAR) + "，"
						+ (month.getCurrentItem() + 1) + ","
						+ (day.getCurrentItem() + 1));
	}

	// public void setCurrentDate(int year, int month, int day) {
	// mCurYear = year;
	// mCurMonth = month;
	// mCurDay = day;
	// this.year.setCurrentItem(year, false);
	// updateDays(this.year, this.month, this.day);
	// this.month.setCurrentItem(mCurMonth, false);
	// updateDays(this.year, this.month, this.day);
	// this.day.setCurrentItem(day, false);
	// updateDays(this.year, this.month, this.day);
	// this.update();
	// }

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(24);
		}

		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		public CharSequence getItemText(int index) {
			currentItem = index;
			return super.getItemText(index);
		}

	}

	public void onClick(View v) {
		this.dismiss();
	}

	public void updateByZheng() {
		year.setCurrentItem(mCurYear);
		month.setCurrentItem(mCurMonth);
		day.setCurrentItem(mCurDay);
	}

}
