package com.gongheshe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.gongheshe.R;

/**
 *
 * @author ZhengZhiying
 *
 * @Funtion		确定对话框
 * 
 *
 */
public class ForSureDlg extends Dialog {

	private TextView txt_title;
	private Button btn_cancel;
	public Button btn_ok;
	
	private static ForSureDlg forSureDlg;
	
	public static void init(Context context){
		forSureDlg = new ForSureDlg(context);
	}

	public ForSureDlg(Context context) {
		super(context, R.style.Dialog);
		setContentView(R.layout.dlg_for_sure);
		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		int type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		getWindow().setType(type);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public static ForSureDlg get(){
		return forSureDlg;
	}

	public ForSureDlg setTitleText(CharSequence title) {
		txt_title.setText(title);
		return this;
	}

}
