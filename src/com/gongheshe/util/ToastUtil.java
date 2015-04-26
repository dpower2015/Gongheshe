package com.gongheshe.util;

import android.annotation.SuppressLint;
import android.content.Context;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

	private static Toast toast;

	private ToastUtil() {
	};

	/**
	 * toast 提示消息
	 * @param context
	 * @param msg
	 * @return 
	 * @return
	 */
	@SuppressLint("ShowToast")
	public static void showToast(Context context,String msg){
		if(toast == null){
			toast = Toast.makeText(context, msg, 3000);
		}else {
			toast.setText(msg);
		}
		toast.setMargin(0, 0.05f);
		toast.show();
	}
	
}
