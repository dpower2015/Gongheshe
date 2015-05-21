package com.gongheshe.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class BmpToFile {

	private static BmpToFile bmpToFile;
	private Context context;

	private BmpToFile(Context context) {
		this.context = context;
	}

	public static BmpToFile get(Context context){
		if(null == bmpToFile){
			bmpToFile = new BmpToFile(context);
		}
		return bmpToFile;
	} 
	
	public File bitmapToFile(Bitmap photo, String fileName) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		// int quality = 100;
		int quality = 80;
		OutputStream stream = null;
		File result = null;
		try {
			File fileSD = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/" + context.getPackageName() + "/cache");
			try {
				result = new File(fileSD, fileName);// 建立文件
				result.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			stream = new FileOutputStream(result.getPath());
			photo.compress(format, quality, stream);
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
