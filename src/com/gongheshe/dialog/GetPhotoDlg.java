package com.gongheshe.dialog;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.example.gongheshe.R;

/**
 * 
 * @author ZhengZhiying
 * 
 * @Funtion 打开图像
 * 
 * 
 */
public class GetPhotoDlg extends Dialog {

	private Activity activity;
	/** 相册返回 */
	public final static int requestCodePicture = 0x21;
	/** 相机返回 */
	public final static int requestCamera = 0x22;
	public String filePath = "";

	public GetPhotoDlg(Context context) {
		super(context, R.style.Dialog);
		activity = (Activity) context;
		setContentView(R.layout.dialog_get_photo);
		setListenerToView();
	}

	private void setListenerToView() {
		View.OnClickListener l = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_picture: {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					activity.startActivityForResult(intent, requestCodePicture);
					dismiss();
					break;
				}

				case R.id.btn_camera: {
					Intent intent = new Intent();
					filePath = getFileName();
					intent.setAction("android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(filePath)));
					activity.startActivityForResult(intent, requestCamera);
					dismiss();
					break;
				}
				case R.id.btn_cancel:
					dismiss();
					break;

				default:
					break;
				}
			}
		};
		findViewById(R.id.btn_picture).setOnClickListener(l);
		findViewById(R.id.btn_camera).setOnClickListener(l);
		findViewById(R.id.btn_cancel).setOnClickListener(l);
	}
	
	private String getFileName() {
		String saveDir = Environment.getExternalStorageDirectory()
				+ "/Android/data/com/image";
		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdir(); // 创建文件夹
		}
		filePath = saveDir + "/" + System.currentTimeMillis() + ".jpg";

		return filePath;
	}

}
