package com.wx.weibo.util;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.GsonBuilder;

/**
 *  NMSServer 公共常量和配置文件参数信息类
 *  @date Jan 5, 2012 11:38:45 AM
 *	@author ZhangGang
 *
 */
public class Constants {
	public static final String UNDER_LINE="_";
	public static final String SPILTOR=",";
	public static final String HTTP_PERFIX="http://";
	public static final String HTTPS_PERFIX="https://";
	public static final String URL_SPILTER="/";
	public static Random rand=new Random();
	public static GsonBuilder builder=new GsonBuilder();
	public static  String JSON_DATE_FORMAT;
	//全局休眠时间
	public static long GLOBAL_SLEEP_TIME;
	public static int IMPLICITLYWAIT;
	public static int PAGELOADTIMEOUT;
	public static long JSTIMEOUT;
	public static String charset;
	public static String CHROME_LOGFILE;
	public static String CHROME_DATA_DIR;
	public static String CHROME_BINARY_DIR;
	public static String CHROME_DRIVER_DIR;
	public static int CHROME_DRIVER_PORT;
	static {
		try {
			JSON_DATE_FORMAT=Config.getString("json_date_format", "yyyy-MM-dd HH:mm:ss");
			GLOBAL_SLEEP_TIME=Config.getLong("global_sleep_time", 1000);
			IMPLICITLYWAIT=Config.getInteger("implicitlywait", 1);
			PAGELOADTIMEOUT=Config.getInteger("pageloadtimeout", 1);
			JSTIMEOUT=Config.getInteger("jstimeout", 1);
			charset=Config.getString("charset", "utf-8");
			CHROME_LOGFILE=Config.getString("chrome.logfile", "chrome.log");
			CHROME_DATA_DIR=Config.getString("chrome.data.dir");
			CHROME_BINARY_DIR=Config.getString("chrome.binary.dir");
			CHROME_DRIVER_DIR=Config.getString("chrome.driver.dir");
			CHROME_DRIVER_PORT=Config.getInteger("chrome_driver_port");
		}catch(Exception e) {
			
		}
	}
	@SuppressWarnings("unused")
	private static String formatPath(String path){
		if(StringUtils.isNotEmpty(path)){
			if(!path.endsWith("/") && !path.endsWith("=")){
				path = path+"/";
			}
			path = path.replaceAll("\\\\", "/");
		}
		return path;
	}
	/**
	 * 获取操作系统类型
	 * @return
	 */
	public static String getOsType(){
		String os = System.getProperty("os.name");
		if(os.toLowerCase().contains("windows")){
			os = "windows";
		}else if(os.toLowerCase().contains("linux")){
			os = "linux";
		}else if(os.toLowerCase().contains("mac")){
			os = "mac";
		}
		return os;
	}
}
