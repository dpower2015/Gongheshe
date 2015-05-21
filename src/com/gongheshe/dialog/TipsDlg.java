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
 * @Funtion		提示对话框
 * 
 *
 */
public class TipsDlg extends Dialog {

	private TextView txt_title;
	private Button btn_cancel;
	public Button btn_ok;
	
	private static TipsDlg forSureDlg;
	
	public static void init(Context context){
		forSureDlg = new TipsDlg(context);
	}

	public TipsDlg(Context context) {
		super(context, R.style.Dialog);
		setContentView(R.layout.dlg_for_sure);
		txt_title = (TextView) findViewById(R.id.txt_title);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setVisibility(View.GONE);
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
	
	public static TipsDlg get(Context context){
		if(forSureDlg == null){
			init(context);
		}
		return forSureDlg;
	}
	
	public static TipsDlg newIns(Context context){
		return (new TipsDlg(context));
	}

	public TipsDlg setTitleText(CharSequence title) {
		txt_title.setText(title);
		return this;
	}

}
