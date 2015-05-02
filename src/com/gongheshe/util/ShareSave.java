package com.gongheshe.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author ZhengZhiying
 * 
 * @Funtion 需要初始化
 * 
 * 
 */
public class ShareSave {

	public static ShareSave shareSave;
	public Context context;
	private String file_name = "shizai";
	private SharedPreferences reader;
	private SharedPreferences.Editor writer;

	private ShareSave() {
		//
	}

	public static ShareSave get() {
		if (shareSave == null) {
			shareSave = new ShareSave();
		}
		return shareSave;
	}

	public void setUserName(String userName) {
		writer.putString("userName", userName);
		writer.commit();
	}
	

	public String getUserName() {
		return reader.getString("userName", null);
	}
	
	public void setPsdword(String password){
		writer.putString("password", password);
		writer.commit();
	}
	
	public String getPsdword(){
		return reader.getString("password", null);
	}
	
	public boolean isKeepPassword(){
		return reader.getBoolean("isKeepPsd", false);
	}
	
	public void enableKeepPsd(boolean result){
		writer.putBoolean("isKeepPsd", result);
		writer.commit();
	}
	public Boolean isEnter(){
		
		return reader.getBoolean("isLogin", false);
	}
	public void Enter(Boolean enter){
		writer.putBoolean("isLogin", enter);
		writer.commit();
		
	}
	public void initSharePre(Context context) {
		this.context = context;
		reader = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
		writer = context.getSharedPreferences(file_name, Context.MODE_PRIVATE)
				.edit();
	}
	
	public String getUid(){
		return reader.getString("uid", null);
	}
	
	public void setUid(String uid){
		writer.putString("uid", uid);
		writer.commit();
	}

	public void setTelPhone(String mobile) {
		writer.putString("mobile", mobile);
		writer.commit();
	}
	
	public String getTelPhone(){
		return reader.getString("mobile", null);
	}

}
