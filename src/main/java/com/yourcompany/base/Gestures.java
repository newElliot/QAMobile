package com.yourcompany.base;


import java.time.Duration;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;

import com.yourcompany.utilities.MobileConstants;
import com.yourcompany.utilities.TestUtilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.Origin;

public class Gestures {
	public static Logger logger = LogManager.getLogger(Gestures.class.getName());
	private static final String YOUR_FINGER = "finger";
	private static final int SCROLL_SPEED = 1;
	private static final int DRAG_SPEED = 2;
	private static final int FLICK_SPEED = 2;
	private static final long SWIPE_SPEED = 300;
	private static final int LONG_PRESS_SECOND = 3;
	private static final int MAX_SCROLL_TIME = 5;
	private static final int MAX_FLICK_TIME = 5;
	private static final int MAX_SWIPE_TIME = 5;
	
	private ElementUtils elementUtils;
	protected AppiumDriver driver;
	
	public Gestures(AppiumDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(this.driver);
	}
	
	public void enterValue(WebElement e, String value) {
		e.clear();
		e.sendKeys(value);
		logger.info("    Entered value |" + value + "| to element " + e);
	}
	
	public String getText(WebElement e) {
		String text = e.getText();
		logger.info("    Getted text = " + text + ", element=" + e);
		return text;
	}
	
	public void setImplicitWait(int timeOut) throws Exception {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
			logger.info("    Override implicit wait successful, timeout=" + timeOut);
		} catch (Exception e) {
			throw new Exception("        Failed to set implicit wait, timeout=" + timeOut);
		}
		
	}
	
	public void tap(WebElement e) throws Exception {
		// Create a new PointerInput object with touch type
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
		// Check the driver is not null, make sure you already initialize this class
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}		
		try {
			Sequence tap = new Sequence(finger, 1);
			tap.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(e), 0, 0));
			tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(Arrays.asList(tap));
			logger.info("    Tapped on element, element " + e);
		} catch (Exception ex) {
			throw new Exception("        Tap failed," + ex);
		}
	}
	
	public void doubleTap(WebElement e) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		// Create a new PointerInput object with touch type
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
		try {
			Sequence doubleClick = new Sequence(finger, 1);
			doubleClick.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(e), 0, 0));
			doubleClick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			doubleClick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			doubleClick.addAction(new Pause(finger, Duration.ofMillis(100)));
			doubleClick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			doubleClick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(Arrays.asList(doubleClick));
			logger.info("    Double tapped on element, element " + e);
		} catch (Exception ex) {
			throw new Exception("        Double tap failed," + ex);
		}
	}
	
	public void drag(WebElement source, WebElement target) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
		
		try {
			Sequence drag = new Sequence(finger, 1); //1 is unique sequence, if it has more action then define 2, 3, 4
			drag.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(source), 0, 0));
			drag.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			drag.addAction(new Pause(finger, Duration.ofSeconds(1)));
			drag.addAction(finger.createPointerMove(Duration.ofSeconds(DRAG_SPEED), Origin.fromElement(target), 0, 0));
			drag.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(Arrays.asList(drag));
			logger.info("    Draged element " + source + " to element " + target);
		} catch (Exception ex) {
			throw new Exception("        Drag failed," + ex);
		}
	}
	
	public void scrollTo(String targetLocator) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = null;
		// Get the size of the screen
		Dimension size = null;
		// Calculate the start and end points for the swipe gesture
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int scrollTime = 0;
		boolean isFound = false;
		try {
			//override implicit wait
			setImplicitWait(2);
			while (!isElementFound(targetLocator) && scrollTime++ < MAX_SCROLL_TIME) {
				finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
				// Get the size of the screen
				size = driver.manage().window().getSize();
				// Calculate the start and end points for the swipe gesture
				startX = size.width / 2;
				startY = (int) (size.height * 0.8);
				endX = startX;
				endY = (int) (size.height * 0.2);
				Sequence scroll = new Sequence(finger, 1);
				TestUtilities.setDelayTime(1);
				
				scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
				scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				scroll.addAction(new Pause(finger, Duration.ofSeconds(1)));
				scroll.addAction(finger.createPointerMove(Duration.ofSeconds(SCROLL_SPEED), PointerInput.Origin.viewport(), endX, endY));
				scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				driver.perform(Arrays.asList(scroll));
				logger.info("    Scrolling to element " + targetLocator + " for " + scrollTime + " times");
				if (isElementFound(targetLocator)) {
					isFound = true;
				}
			}
			Assert.assertTrue(isFound, "        Element not displayed " + targetLocator);
			logger.info("    Found element, element " + targetLocator);
		} catch (Exception ex) {
			throw new Exception("        Scroll failed," + ex);
		} finally {
			//revert implicit wait
			setImplicitWait(MobileConstants.IMPLICIT_WAIT_DEFAULT);
		}
	}
	
	public void flick(WebElement e, Direction direction, int flickCount) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = null;
		// Get the size of the screen
		Dimension size = null;
		// Calculate the start and end points for the swipe gesture
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int flickTime = 0;
		
		try {
			setImplicitWait(2);
			while (flickTime++ < flickCount) {
				finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
				// Get the size of the screen
				size = driver.manage().window().getSize();
				// Calculate the start and end points for the swipe gesture
				switch (direction) {
				case LEFT_TO_RIGHT:
					startX = (int) (e.getRect().x + e.getRect().width * 0.8);
					startY = e.getRect().y + e.getRect().height / 2;
					endX = (int) (size.width * 0.1);
					endY = startY;
					break;
				case RIGHT_TO_LEFT:
					startX = (int) (e.getRect().x + e.getRect().width * 0.2);
					startY = e.getRect().y + e.getRect().height / 2;
					endX = (int) (size.width * 0.9);
					endY = startY;
					break;
				default:
					break;
				}
				
				Sequence flick = new Sequence(finger, 1);
				TestUtilities.setDelayTime(1);

				flick.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
				flick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				flick.addAction(new Pause(finger, Duration.ofMillis(100)));
				flick.addAction(finger.createPointerMove(Duration.ofSeconds(FLICK_SPEED), PointerInput.Origin.viewport(), endX, endY));
				flick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				driver.perform(Arrays.asList(flick));
				logger.info("    Flicked from element " + e + " for " + flickTime + " times");
			}
		} catch (Exception ex) {
			throw new Exception("        Flick failed, element=" + e + ", direction=" + direction.toString()
			+ ", flick count=" + flickCount + ex);
		} finally {
			setImplicitWait(MobileConstants.IMPLICIT_WAIT_DEFAULT);
		}
	}

	public void flickTo(WebElement source, Direction direction, String targetLocator) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = null;
		// Get the size of the screen
		Dimension size = null;
		// Calculate the start and end points for the swipe gesture
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int flickTime = 0;
		boolean isFound = false;
		
		try {
			setImplicitWait(2);
			while (!isElementFound(targetLocator) && flickTime++ < MAX_FLICK_TIME) {
				finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
				// Get the size of the screen
				size = driver.manage().window().getSize();
				// Calculate the start and end points for the swipe gesture
				switch (direction) {
				case LEFT_TO_RIGHT:
					startX = (int) (source.getRect().x + source.getRect().width * 0.8);
					startY = source.getRect().y + source.getRect().height / 2;
					endX = (int) (size.width * 0.1);
					endY = startY;
					break;
				case RIGHT_TO_LEFT:
					startX = (int) (source.getRect().x + source.getRect().width * 0.2);
					startY = source.getRect().y + source.getRect().height / 2;
					endX = (int) (size.width * 0.9);
					endY = startY;
					break;
				default:
					break;
				}
				
				Sequence flick = new Sequence(finger, 1);
				TestUtilities.setDelayTime(1);

				flick.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
				flick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				flick.addAction(new Pause(finger, Duration.ofMillis(100)));
				flick.addAction(finger.createPointerMove(Duration.ofSeconds(FLICK_SPEED), PointerInput.Origin.viewport(), endX, endY));
				flick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				driver.perform(Arrays.asList(flick));
				logger.info("    Flicked from element " + source + " for " + flickTime + " times");
				if (isElementFound(targetLocator)) {
					isFound = true;
				}
			}
			Assert.assertTrue(isFound, "        Element is not found, element=" + targetLocator);
		} catch (Exception ex) {
			throw new Exception("        Flick failed, source element=" + source + ", direction=" + direction.toString()
					+ ", target locator=" + targetLocator + ex);
		} finally {
			setImplicitWait(MobileConstants.IMPLICIT_WAIT_DEFAULT);
		}
	}
	
	public void swipe(WebElement e, Direction direction, int swipeCount) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = null;
		// Get the size of the screen
		Dimension size = null;
		// Calculate the start and end points for the swipe gesture
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int swipeTime = 0;
		
		try {
			while (swipeTime++ < swipeCount) {
				finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
				// Get the size of the screen
				size = driver.manage().window().getSize();
				// Calculate the start and end points for the swipe gesture
				switch (direction) {
				case LEFT_TO_RIGHT:
					startX = (int) (e.getRect().x + e.getRect().width * 0.9);
					startY = e.getRect().y + e.getRect().height / 2;
					endX = (int) (size.width * 0.1);
					endY = startY;
					break;
				case RIGHT_TO_LEFT:
					startX = (int) (e.getRect().x + e.getRect().width * 0.1);
					startY = e.getRect().y + e.getRect().height / 2;
					endX = (int) (size.width * 0.9);
					endY = startY;
					break;
				default:
					break;
				}
				
				Sequence swipe = new Sequence(finger, 1);
				TestUtilities.setDelayTime(1);

				swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
				swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				swipe.addAction(new Pause(finger, Duration.ofMillis(100)));
				swipe.addAction(finger.createPointerMove(Duration.ofMillis(SWIPE_SPEED), PointerInput.Origin.viewport(), endX, endY));
				swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				driver.perform(Arrays.asList(swipe));
				logger.info("    Swiped from element " + e + " for " + swipeTime + " times");
			}
		} catch (Exception ex) {
			throw new Exception("        Swipe failed, element=" + e + ", direction=" + direction.toString()
			+ ", swipe count=" + swipeCount + ex);
		}
	}
	
	public void swipeTo(WebElement source, Direction direction, String targetLocator) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null.");
		}
		PointerInput finger = null;
		// Get the size of the screen
		Dimension size = null;
		// Calculate the start and end points for the swipe gesture
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int swipeTime = 0;
		boolean isFound = false;
		
		try {
			setImplicitWait(2);
			while (!isElementFound(targetLocator) && swipeTime++ < MAX_SWIPE_TIME) {
				finger = new PointerInput(PointerInput.Kind.TOUCH, YOUR_FINGER);
				// Get the size of the screen
				size = driver.manage().window().getSize();
				// Calculate the start and end points for the swipe gesture
				switch (direction) {
				case LEFT_TO_RIGHT:
					startX = (int) (source.getRect().x + source.getRect().width * 0.8);
					startY = source.getRect().y + source.getRect().height / 2;
					endX = (int) (size.width * 0.1);
					endY = startY;
					break;
				case RIGHT_TO_LEFT:
					startX = (int) (source.getRect().x + source.getRect().width * 0.2);
					startY = source.getRect().y + source.getRect().height / 2;
					endX = (int) (size.width * 0.9);
					endY = startY;
					break;
				default:
					break;
				}
				
				Sequence swipe = new Sequence(finger, 1);
				TestUtilities.setDelayTime(1);

				swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
				swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				swipe.addAction(new Pause(finger, Duration.ofMillis(100)));
				swipe.addAction(finger.createPointerMove(Duration.ofMillis(SWIPE_SPEED), PointerInput.Origin.viewport(), endX, endY));
				swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				driver.perform(Arrays.asList(swipe));
				logger.info("    Swiped from element " + source + " for " + swipeTime + " times");
				if (isElementFound(targetLocator)) {
					isFound = true;
				}
			}
			Assert.assertTrue(isFound, "        Element is not found, element=" + targetLocator);
		} catch (Exception ex) {
			throw new Exception("        Swipe failed, source element=" + source + ", direction=" + direction.toString()
					+ ", target locator=" + targetLocator + ex);
		} finally {
			setImplicitWait(MobileConstants.IMPLICIT_WAIT_DEFAULT);
		}
	}
	
	public void longPress(WebElement e) throws Exception {
		if (driver == null) {
			throw new Exception("        Driver can not be null");
		}
		
		PointerInput finger = new PointerInput(Kind.TOUCH, YOUR_FINGER);
		Sequence longPress = new Sequence(finger, 1);
		try {
			longPress.addAction(finger.createPointerMove(Duration.ZERO, Origin.fromElement(e), 0, 0));
			longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			longPress.addAction(new Pause(finger, Duration.ofSeconds(LONG_PRESS_SECOND)));
			longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			
			driver.perform(Arrays.asList(longPress));
			logger.info("    Pressed element for " + LONG_PRESS_SECOND + "s, element=" + e);
		} catch (Exception ex) {
			throw new Exception("        Long press failed, element=" + e + ", seconds=" + LONG_PRESS_SECOND, ex);
		}
	}
	
	public void rotate(RotateMode mode) throws Exception {
		if (mode == null) {
			throw new Exception("        RotateMode can not be null.");
		}
		if (this.driver instanceof AndroidDriver) {
			AndroidDriver androidDriver = (AndroidDriver) driver;
			switch (mode) {
			case NORMAL:
				androidDriver.rotate(new DeviceRotation(0, 0, 0));
				logger.info("    Rotated in normal");
				break;
			case LANDSCAPE:
				androidDriver.rotate(new DeviceRotation(0, 0, 90));
				logger.info("    Rotated in landscape");
				break;
			default:
				break;
			}
		} // else IOS driver
		
	}
	
	public void pressHomeButton() throws Exception {
		try {
			if (this.driver instanceof AndroidDriver) {
				AndroidDriver androidDriver = (AndroidDriver) driver;
				androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
				logger.info("    Pressed home button");
			}
			// else IOS driver
		} catch (Exception e) {
			throw new Exception("        Failed press home button.");
		}
	}
	
	public void pressBackButton() throws Exception {
		try {
			if (this.driver instanceof AndroidDriver) {
				AndroidDriver androidDriver = (AndroidDriver) driver;
				androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
				logger.info("    Pressed back button");
			}
			// else IOS driver
		} catch (Exception e) {
			throw new Exception("        Failed press back button.");
		}
	}
	
	public void pressVolUpButton() throws Exception {
		try {
			if (this.driver instanceof AndroidDriver) {
				AndroidDriver androidDriver = (AndroidDriver) driver;
				androidDriver.pressKey(new KeyEvent(AndroidKey.VOLUME_UP));
				logger.info("    Pressed volume up button");
			}
			// else IOS driver
		} catch (Exception e) {
			throw new Exception("        Failed press volume up button.");
		}
	}
	
	public void pressVolDownButton() throws Exception {
		try {
			if (this.driver instanceof AndroidDriver) {
				AndroidDriver androidDriver = (AndroidDriver) driver;
				androidDriver.pressKey(new KeyEvent(AndroidKey.VOLUME_DOWN));
				logger.info("    Pressed volume down button");
			}
			// else IOS driver
		} catch (Exception e) {
			throw new Exception("        Failed press volume down button.");
		}
	}
	
	public void pressVolMuteButton() throws Exception {
		try {
			if (this.driver instanceof AndroidDriver) {
				AndroidDriver androidDriver = (AndroidDriver) driver;
				androidDriver.pressKey(new KeyEvent(AndroidKey.VOLUME_MUTE));
				logger.info("    Pressed volume mute button");
			}
			// else IOS driver
		} catch (Exception e) {
			throw new Exception("        Failed press volume mute button.");
		}
	}
	
	private boolean isElementFound(String locator) throws Exception {
		return elementUtils.getElementsByXpath(locator).size() > 0;
	}

}
