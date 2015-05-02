package com.gongheshe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.example.gongheshe.R;
import com.gongheshe.util.LoggerSZ;

/**
 *
 * @author ZhengZhiying
 *
 * @Funtion		系统对话框，显示正在加载
 * 
 *
 */
public class LoadingDlg extends Dialog {

	private static LoadingDlg loadingDlg;
	private String tag = "LoadingDlg";
	
	public LoadingDlg(Context context) {
		super(context, R.style.Dialog);
		setContentView(R.layout.dlg_loading);
		int type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		getWindow().setType(type);
		LoggerSZ.i(tag , "init LoadingDlg");
	}
	
	public static void init(Context context){
		if(loadingDlg == null){
			loadingDlg = new LoadingDlg(context);
		}
	}
	
	public static LoadingDlg get(){
		return loadingDlg;
	}

}
