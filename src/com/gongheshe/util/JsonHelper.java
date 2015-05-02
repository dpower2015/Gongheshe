package com.gongheshe.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ZhengZhiying
 *
 * @Funtion		JSON解析帮助类
 * 
 *
 */
public class JsonHelper {

	private static JsonHelper jsonHelper;
	
	public static JsonHelper get(){
		if(jsonHelper == null){
			jsonHelper = new JsonHelper();
		}
		return jsonHelper;
	}
	
	/**
	 * 读取 “Data” JSON组中的第一条数据
	 * @param data
	 * @return
	 */
	public String getDataJObj(String data){
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(data);
			JSONArray arr = jsonObject.getJSONArray("Data");
			return arr.getJSONObject(0).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取 “Data” JSON组中的数据
	 * @param data
	 * @return JSONArray数据
	 */
	public JSONArray getDataJArr(String data){
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(data);
			JSONArray arr = jsonObject.getJSONArray("Data");
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
