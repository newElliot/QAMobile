package com.yourcompany.base;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.yourcompany.configuration.Configuration;
import com.yourcompany.configuration.DevicesMode;
import com.yourcompany.configuration.PropertyReader;
import com.yourcompany.configuration.SystemVariable;
import com.yourcompany.utilities.MobileConstants;
import com.yourcompany.utilities.TestUtilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseTest {
	public AndroidDriver androidDriver;
	public IOSDriver iosDriver;
	public WebDriverWait wait;
	private Configuration config;
	private AppiumDriverLocalService service;
	
	private static final int APPIUM_PORT = 4723;
	private static final int MAX_CONNECTION_ATTEMPTS = 5;
	public static Logger logger = LogManager.getLogger(BaseTest.class);
	
	public AppiumDriver getDriver() {
		return this.androidDriver != null ? this.androidDriver : iosDriver;
	}
	
	public IOSDriver setUpIOS() throws Exception {
		return new IOSDriver(new URL(""), new DesiredCapabilities());
	}
	
	/**
	 * Initial appium driver by kind of device: REAL device or EMULATOR device
	 * 
	 * @param String device: REAL or EMULATOR
	 * @return AndroidDriver
	 * @throws Exception
	 */
	@BeforeMethod(alwaysRun=true)
	@Parameters({"osName", "deviceType", "hub", "port", "timeOut"})
	public AppiumDriver setUpMobileDriver(String osName, String deviceType, String hub, String port, int timeOut) throws Exception {
		config = new PropertyReader().loadConfigurationByPropertyFile(SystemVariable.CURRENT_DIR.concat(SystemVariable.MAIN_RESOURCE + "/env/common.properties"));

		File src = new File(SystemVariable.CURRENT_DIR.concat(SystemVariable.TEST_RESOURCE));
		URL url = new URL(hub.concat(port).concat("/wd/hub"));
		File myApp = new File(src, config.getApp());
		DesiredCapabilities cap = new DesiredCapabilities();

		switch (osName) {
		case "Android":
			androidDriver = setUpAndroidDriver(deviceType, cap, myApp, url, timeOut);
			androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(MobileConstants.IMPLICIT_WAIT_DEFAULT));
			wait = new WebDriverWait(androidDriver, Duration.ofSeconds(MobileConstants.EXPLICIT_WAIT_DEFAULT));
			return androidDriver;
		case "IOS":
			break;
		default:
			break;
		}

		return null;
	}
	
	public AndroidDriver setUpAndroidDriver(String deviceMode, DesiredCapabilities cap, File myApp, URL url, int timeOut) throws Exception {
		AndroidDriver driver = null;
		if (deviceMode.contains(DevicesMode.REAL.toString())) {
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		} else if (deviceMode.contains(DevicesMode.EMULATOR.toString())) {
			startEmulatorDevice(config.getDevice());
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, config.getDevice());
		}

		cap.setCapability(MobileCapabilityType.APP, myApp.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, timeOut);

		int attempts = 0;
		do {
			Assert.assertNull(driver, "AndroidDriver is already initialized");
			try {
				logger.info("Initializing AndroidDriver, hubUrl=" + url + ", capabilities=" + cap);
				driver = new AndroidDriver(url, cap);
			} catch (Exception e) {
				logger.error("Error initializing AndroidDriver, attempts=" + attempts + ", maxAttempts="
						+ MAX_CONNECTION_ATTEMPTS, e);
				Thread.sleep(TimeUnit.SECONDS.toMillis(5));
			}
		} while (driver == null && attempts++ < MAX_CONNECTION_ATTEMPTS);
		Assert.assertNotNull(driver, "Unable to initialize AndroidDriver, hubUrl="+url.toString());
		return driver;
	}

	private void startEmulatorDevice(String deviceName) throws Exception {
		String fullPath = SystemVariable.CURRENT_DIR
				.concat(SystemVariable.MAIN_RESOURCE.concat("/env/startEmulator.bat"));
		try {
			logger.info("Starting emulator device, device name=" + deviceName);
			Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/c", fullPath, SystemVariable.CURRENT_USER, deviceName });
			TestUtilities.setDelayTime(1);
			
		} catch (Exception e) {
			throw new Exception("        Can not execute startEmulator.bat");
		}
	}
	
//	@BeforeTest
	public void killAllNodes() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		startAppiumServer();
		
	}
	
	private AppiumDriverLocalService startAppiumServer() {

		// Check if appium server already started, do not start again
		if (!isAppiumServerRunning(APPIUM_PORT)) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}

		return service;
	}
	
	private boolean isAppiumServerRunning(int port) {
		boolean flag = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (Exception e) {
			flag = true;
		} finally {
			socket = null;
		}

		return flag;
	}
	
}
