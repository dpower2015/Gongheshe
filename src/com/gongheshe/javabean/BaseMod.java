package com.gongheshe.javabean;

import java.io.Serializable;

import org.json.JSONObject;

public class BaseMod implements Serializable {

	/**  */
	private static final long serialVersionUID = 1084106898497162532L;
	
	/**
	 * JSONObject数据填充到实体中
	 * @param jsonObject
	 * @return
	 */
	public boolean fillByJson(JSONObject jsonObject){
		return false;
	}
}
