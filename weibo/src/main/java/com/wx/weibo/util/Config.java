package com.wx.weibo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取和保存myconfig信息的类
 * 
 * @author ZGGG3
 * 
 */
public class Config {
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	private static Properties properties;
	public static Config config = Config.getInstance();
	public static String getSystemDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 单例 初始化获取实例
	 * 
	 * @return
	 */
	public static Config getInstance() {
		if (config == null)
			return new Config();
		return config;
	}

	/**
	 * 单例 重新读取文件已获取参数
	 * 
	 * @return
	 */
	public static Config reinitialize() {
		config = null;
		return new Config();
	}

	/**
	 * 构造函数私有，用于单例实例
	 */
	private Config() {
		InputStreamReader is = null;
		properties = new Properties();
		try {
			String workingDirectory=System.getProperty("user.dir");
			is = new InputStreamReader(new FileInputStream(workingDirectory+"/config/weibo.properties"), "UTF-8");
			properties.load(is);
		} catch (IOException e) {
			logger.error("读取properties文件错误"+"",e);
		}catch (NullPointerException e) {
			try {
				is = new InputStreamReader(getClass().getResourceAsStream("/config/weibo.properties"), "UTF-8");
				properties.load(is);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.error("读取properties文件错误"+"",e);
			}
			
		}finally{
			try {
				if(is!=null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("发生异常！",e);
			}
		}
	}
	/**
	 * 根据name获取value
	 * 
	 * @param oid
	 * @return
	 */
	public static Integer getInteger(String name) {

		return Integer.parseInt(getString(name));

	}
	/**
	 * 根据name获取value
	 * 
	 * @param oid
	 * @return
	 */
	public static Integer getInteger(String name,int defaultVal) {
		// TODO Auto-generated method stub
		Integer val=Integer.parseInt(getString(name));
		return val==null?defaultVal:val;
	}
	/**
	 * 根据name获取value
	 * 
	 * @param oid
	 * @return
	 */
	public static Boolean getBoolean(String name,boolean defaultVal) {
		// TODO Auto-generated method stub
		Boolean val=Boolean.parseBoolean(getString(name));
		return val==null?defaultVal:val;
	}
	/**
	 * 根据name获取value
	 * 
	 * @param oid
	 * @return
	 */
	public static String getString(String name) {
		String str = null;
		try {
			str = properties.getProperty(name);
			if(StringUtils.isNotEmpty(str)){
				str = str.trim();
			}else{
				logger.warn("资源配置文件取值异常 >> "+name+" 值为空");
			}
		} catch (Exception e) {
			logger.error("资源配置文件取值异常", e);
		}
		return str;
	}
	
	public static String getString(String name,String defaultVal) {
		String str = defaultVal;
		try {
			str = properties.getProperty(name);
			if(StringUtils.isNotEmpty(str)){
				str = str.trim();
			}else{
				logger.warn("资源配置文件取值异常 >> "+name+" 值为空");
			}
		} catch (Exception e) {
			logger.error("资源配置文件取值异常", e);
		}
		return str;
	}

	/**
	 * 根据name获取value
	 * 
	 * @param oid
	 * @return
	 */
	public static Long getLong(String name,long defaultVal) {
		// TODO Auto-generated method stub
		Long val=Long.parseLong(getString(name));
		return val==null?defaultVal:val;
	}
}
