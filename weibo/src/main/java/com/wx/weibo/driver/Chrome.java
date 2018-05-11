package com.wx.weibo.driver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.weibo.util.Constants;

public class Chrome {
	private static final Logger logger = LoggerFactory.getLogger(Chrome.class);
	@SuppressWarnings("unused")
	private static String CHROME_VERSION;
	private boolean loadImg=true;
	private static ChromeDriverService service;
	private ChromeDriver driver;
	public Chrome() {
	}
	public Chrome(boolean loadImg) {
		this.loadImg=loadImg;
	}

	public void init() {
		try {
			@SuppressWarnings("unused")
			String path=System.getProperty("user.dir");
			// 获取chrome浏览器保存数据目录
			String profileDir = Constants.CHROME_DATA_DIR;
			File profileDirFile = new File(profileDir);
			if (!profileDirFile.exists()) {
				profileDirFile.mkdirs();
			}
			// 获取chrome安装目录
			File chromeDirFile = new File(Constants.CHROME_BINARY_DIR);
			if (!chromeDirFile.exists()) {
				logger.error(chromeDirFile.getAbsolutePath()+"不存在");
			}
			// 加载chromedirver到环境变量中
			System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, Constants.CHROME_DRIVER_DIR);
			System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, Constants.CHROME_LOGFILE);
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--verbose");
			// 关闭沙盒模式，chrome在linux中启动需关闭沙盒模式才能以root用户启动浏览器
			chromeOptions.addArguments("--no-sandbox");
			// ChromeDriver切换浏览器语言
			chromeOptions.addArguments("--lang=" + "zh-CN");
			// ChromeDriver设置忽略 Chrome 浏览器证书错误报警提示
			chromeOptions.addArguments("--test-type", "--ignore-certificate-errors");
			// ChromeDriver设置Chrome参数使浏览器最大化并且默认不检查浏览器
			chromeOptions.addArguments(/*"--start-maximized",*/"no-default-browser-check");
			// 设置chrome可执行文件所在目录
			chromeOptions.setBinary(Constants.CHROME_BINARY_DIR);
			// ？？？
			chromeOptions.setExperimentalOption("detach", false);
			chromeOptions.addArguments("user-data-dir=" + profileDir);
			// 去掉Chrome正受到自动测试软件的控制的提示
			chromeOptions.addArguments("--disable-infobars");
			// 单进程运行
			// chromeOptions.addArguments("single-process");
			// 每个标签使用单独进程
//			 chromeOptions.addArguments("--process-per-tab");
//			chromeOptions.addExtensions(new File(path+"/crx/Adblock-Plus_v1.12.1.crx"));
			//禁用弹出拦截
			chromeOptions.addArguments("--disable-popup-blocking");
			chromeOptions.addArguments("--disable-default-apps");
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("--no-first-run");
			chromeOptions.addArguments("--noerrdialogs");
			chromeOptions.addArguments("--skip-gpu-data-loading");
			chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			chromeOptions.setCapability("pageLoadStrategy", "none");
			chromeOptions.setHeadless(true);
			//chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			//不加载图片
//			chromeOptions.addArguments("--disable-images");
			if(!loadImg){//不加载图片
				Map<String, Object> prefs = new HashMap<String, Object>();
		        prefs.put("profile.managed_default_content_settings.images", 2);
		        chromeOptions.setExperimentalOption("prefs", prefs);
			}
			if(service==null||!service.isRunning()) {
				service=new ChromeDriverService.Builder()
						.usingDriverExecutable(new File(Constants.CHROME_DRIVER_DIR))
						.usingPort(Constants.CHROME_DRIVER_PORT).build();
				try {
					service.stop();
				}catch (Throwable e) {
					// TODO: handle exception
				}
				service.start();
			}
				driver = new ChromeDriver(service,
						chromeOptions);
				
//			}
			driver.manage().timeouts().implicitlyWait(Constants.IMPLICITLYWAIT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(Constants.PAGELOADTIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(Constants.JSTIMEOUT, TimeUnit.SECONDS);
		}catch(Exception e) {
			logger.error("chrome driver init failed",e);
		}
	}
	/**
	 *  跳转至url
	 * @param url
	 * @return
	 */
	public boolean getUrl(String url) {
		boolean success=false;
		try {
			if (driver != null) {
				driver.get(url);
			}
			success=true;
		}catch (WebDriverException e) {
			// TODO: handle exception
			logger.error("get url "+url+" failed",e);
		}
		return success;
	}
	public void destory() {
		try {
			if (driver != null) {
				driver.quit();
			}
		}catch (WebDriverException e) {
			// TODO: handle exception
			logger.error("driver quit failed",e);
		}
	}
	public void startService() {
		try {
			if (service != null) {
				service.start();
			}
		}catch (WebDriverException e) {
			// TODO: handle exception
			logger.error("service start failed",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("service start failed",e);
		}
	}
	public void stopService() {
		try {
			if (service != null) {
				service.stop();
			}
		}catch (WebDriverException e) {
			// TODO: handle exception
			logger.error("driver quit failed",e);
		}
	}
	public String getPageSource() {
		String pageSource=null;
		try {
			if (driver != null) {
				pageSource=driver.getPageSource();
			}
		}catch (WebDriverException e) {
			// TODO: handle exception
			logger.error("driver quit failed",e);
		}
		return pageSource;
	}
	public static void main(String[] args) {
		Chrome chrome=new Chrome();
		chrome.init();
		chrome.getUrl("https://www.baidu.com");
		System.out.println(chrome.getPageSource());
		chrome.destory();
		chrome.stopService();
	}
}
