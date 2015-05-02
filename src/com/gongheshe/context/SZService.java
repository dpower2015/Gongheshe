package com.gongheshe.context;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gongheshe.dialog.LoadingDlg;

public class SZService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LoadingDlg.init(this);
	}

	
	
}
