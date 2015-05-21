package com.gongheshe.util;

import com.googheshe.util.log.LoggerConfig;
import com.googheshe.util.log.TAPrintToLogCatLogger;

public class LoggerSZ {

	private static final TAPrintToLogCatLogger logger = new TAPrintToLogCatLogger();

	public static void i(String tag, String msg) {
		if (LoggerConfig.DEBUG) {
			logger.i(tag, msg);
		}
	}
	
	public static void  e(String tag, String msg) {
		if (LoggerConfig.DEBUG) {
			logger.e(tag, msg);
	}
	}

}
