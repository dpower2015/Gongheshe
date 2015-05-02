package com.gongheshe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 *
 * @author ZhengZhiying
 *
 * @Funtion		文件上传，主要是图片上传
 * 
 *
 */
public class FileUpload {

	private static FileUpload fileUpload;
	/** 提交文件的地址列表 */
	public List<String> strUrls;
	/** 记录百分比 */
	int countPercent = 0;

	private FileUpload() {

	}

	public static FileUpload get() {
		if (fileUpload == null) {
			fileUpload = new FileUpload();
		}
		return fileUpload;
	}

	protected String upLoadTask(int mposition, String name, File file) {
		final String end = "\r\n";
		final String twoHyphens = "--";
		final String boundary = "*****";
		final int position = mposition;
		String result = null;
		String fileName = name;
		File uploadFile = file;
		try {
			URL url = new URL(strUrls.get(position));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + fileName + "\"" + end);
			ds.writeBytes(end);
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;

			final int fileSize = (int) uploadFile.length();
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				ds.write(buffer, 0, length);
				countPercent++;
			}
			countPercent = 0;
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			result = b.toString().trim();
			/* 关闭DataOutputStream */
			ds.close();

		} catch (Exception e) {
			result = e.toString();
		}
		return result;
	}

	/**
	 *  根据大小压缩图片
	 */
	public Bitmap compressBmp(Bitmap bitmap, int memorySize) {

		if (bitmap == null) {
			return bitmap;
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length > memorySize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
				if (options < 10) {
					break;
				}
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

			try {
				baos.close();
				isBm.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;

		}

	}

}
