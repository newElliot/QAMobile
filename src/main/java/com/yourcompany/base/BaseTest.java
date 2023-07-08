package com.yourcompany.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.yourcompany.configuration.Configuration;
import com.yourcompany.configuration.DevicesMode;
import com.yourcompany.configuration.PropertyReader;
import com.yourcompany.configuration.SystemVariable;
import com.yourcompany.utilities.Assertions;
import com.yourcompany.utilities.MobileConstants;
import com.yourcompany.utilities.ReportUtils;
import com.yourcompany.utilities.TestUtilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest extends Assertions {
	public AndroidDriver androidDriver;
	public IOSDriver iosDriver;
	public WebDriverWait wait;
	private Configuration config;
	private AppiumDriverLocalService service;
	
	private static final int APPIUM_PORT = 4723;
	private static final int MAX_CONNECTION_ATTEMPTS = 5;
	private static final int MAX_OPEN_ATTEMPTS = 3;
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
	@Parameters({"osName", "deviceType", "deviceName", "hub", "port", "timeOut"})
	public AppiumDriver setUpMobileDriver(String osName, String deviceType, String deviceName, String hub, String port, int timeOut) throws Exception {
		config = new PropertyReader().loadConfigurationByPropertyFile(SystemVariable.CURRENT_DIR.concat(SystemVariable.MAIN_RESOURCE + "/env/common.properties"));

		File src = new File(SystemVariable.CURRENT_DIR.concat(SystemVariable.TEST_RESOURCE));
		URL url = new URL(hub.concat(port));//.concat("/wd/hub"));
		File myApp = new File(src, config.getApp());
		DesiredCapabilities cap = new DesiredCapabilities();

		switch (osName) {
		case "Android":
			androidDriver = setUpAndroidDriver(deviceType, deviceName, cap, myApp, url, timeOut);
			androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(MobileConstants.IMPLICIT_WAIT_DEFAULT));
			wait = new WebDriverWait(androidDriver, Duration.ofSeconds(MobileConstants.EXPLICIT_WAIT_DEFAULT));
			attempToOpenAndroidApp();
			return androidDriver;
		case "IOS":
			break;
		default:
			break;
		}

		return null;
	}
	
	private void attempToOpenAndroidApp() throws Exception {
		int attemp = 0;
		boolean isAppOpen = false;
		do {
			try {
				// Replace your actual conditions in here, to make sure app is already opened, retry open for 3 times.
				verifyEquals(
						new ElementUtils(wait).waitUntilElementVisibilityByXpath("//*[@text='API Demos']").getText(),
						"API Demos", "        App didn't open, please check again");
				isAppOpen = true;
				logger.info("Application is opened successful");
			} catch (Exception e) {
				logger.error("        Error when launch app, attemp = " + attemp);
				isAppOpen = false;
				new Gestures(androidDriver).launchApp();
			}
		} while (!isAppOpen && attemp++ < MAX_OPEN_ATTEMPTS);
	}
	
	public AndroidDriver setUpAndroidDriver(String deviceMode, String deviceName, DesiredCapabilities cap, File myApp, URL url, int timeOut) throws Exception {
		AndroidDriver driver = null;
		if (deviceMode.contains(DevicesMode.REAL.toString())) {
			logger.info("    Setting capability for real device");
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
		} else if (deviceMode.contains(DevicesMode.EMULATOR.toString())) {
			logger.info("    Setting capability for emulator device");
//			startEmulatorDevice(config.getDevice());
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		}

		cap.setCapability(MobileCapabilityType.APP, myApp.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, timeOut);

		int attempts = 0;
		do {
			verifyNull(driver, "AndroidDriver is already initialized");
			try {
				logger.info("Initializing AndroidDriver, hubUrl=" + url + ", capabilities=" + cap);
				driver = new AndroidDriver(url, cap);
			} catch (Exception e) {
				logger.error("Error initializing AndroidDriver, attempts=" + attempts + ", maxAttempts="
						+ MAX_CONNECTION_ATTEMPTS, e);
				TestUtilities.setDelayTime(5);
			}
		} while (driver == null && attempts++ < MAX_CONNECTION_ATTEMPTS);
		verifyNotNull(driver, "Unable to initialize AndroidDriver, hubUrl="+url.toString());
		return driver;
	}

	private void startEmulatorDevice(String deviceName) throws Exception {
		String batFile = SystemVariable.CURRENT_DIR
				.concat(SystemVariable.MAIN_RESOURCE.concat("/env/startEmulator.bat"));
		try {
			logger.info("Starting emulator device, device name=" + deviceName);
			Process p = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", batFile, "minht", deviceName});
			TestUtilities.setDelayTime(5);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line;
			while ((line = br.readLine()) != null) {
				logger.error(line);
			}
			br.close();
		} catch (Exception e) {
			throw new Exception("        Can not execute startEmulator.bat");
		}
	}
	
	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() throws Exception {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			logger.info("    Killed all nodes");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("==== Starting appium service automaticaly ====");
		startAppiumServer();
	}
	
	private AppiumDriverLocalService startAppiumServer() throws Exception {
		try {
			File appiumJS = new File("C:\\Users\\minht\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");
			// Check if appium server already started, do not start again
			if (!isAppiumServerRunning(APPIUM_PORT) && appiumJS != null) {
				try {
					AppiumDriverLocalService service = new AppiumServiceBuilder()
							.withAppiumJS(appiumJS)
							.withIPAddress("127.0.0.1")
							.usingPort(APPIUM_PORT)
							.withLogFile(new File(SystemVariable.CURRENT_DIR + "/AppiumServerLog.log"))
							.build();
					service.start();
				} catch (Exception ex) {
					logger.info("Start with appiumJS failed. Begin start with appium default service");
					service = null;
					service = AppiumDriverLocalService.buildDefaultService();
					service.start();
				}
				
				logger.info("==== Appium server started successful ====");
			}
			return service;
		} catch (Exception e) {
			throw new Exception("        !!!Appium server start failed");
		}
	}
	
	private  boolean isAppiumServerRunning(int port) {
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
	
	@AfterMethod(alwaysRun=true) 
	public void tearDown(ITestResult result) throws Exception {
		logger.info("=== Running on teardown ===");
		switch(result.getStatus()) {
		case ITestResult.SUCCESS:
			logger.info("######      Test case:" + result.getMethod().getMethodName() + " PASSED      ######");
			break;
		case ITestResult.FAILURE:
			logger.info("######      Test case:" + result.getMethod().getMethodName() 
					+ " FAILED. Screenshot name " + takeScreenshot(getDriver(), result.getMethod().getMethodName()) + "      ######");
			break;
		case ITestResult.SKIP:
			logger.info("######      Test case:" + result.getMethod().getMethodName() + " SKIPPED      ######");
			break;
		default:
			logger.error("######      Test case:" + result.getMethod().getMethodName() + " has result not defined: " + result.getStatus() + "      ######");
			break;
		}
		try {
			new Gestures(getDriver()).closeApp();
		} catch (Exception e) {
			this.getDriver().close();
		} finally {
			androidDriver = null;
			iosDriver = null;
		}
		
	}
	
	private String takeScreenshot(AppiumDriver driver, String testMethod) throws IOException {
		if (driver == null) {
			logger.warn("Unable to take screenshot as driver is not initialized, methodName="
					+ ((testMethod != null) ? testMethod : null));
			return StringUtils.EMPTY;
		}
		byte[] content = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		File file = getScreenshotFile(testMethod, driver.getSessionId());
		FileUtils.writeByteArrayToFile(file, content);
		return file.getAbsolutePath();
	}
	
	private static File getScreenshotFile(String testName, SessionId sessionId) throws IOException {
		return new File(SystemVariable.CURRENT_DIR + File.separator + "screen-shots" + File.separator
				+ getScreenshotName(testName, sessionId));
	}

	private static String getScreenshotName(String testName, SessionId sessionId) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
		return testName + "_" + dateFormat.format(new Date()) + "_" + sessionId + ".png";
	}
	
	@AfterSuite(alwaysRun=true)
	public void afterSuite(ITestContext context) throws Exception {
		logger.info("*** Generating final report ***");
		ReportUtils report = new ReportUtils();
		List<ISuite> suites = Arrays.asList(context.getSuite());
		report.generateReport(suites);
	}

}
