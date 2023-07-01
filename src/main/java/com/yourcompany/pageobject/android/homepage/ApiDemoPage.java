package com.yourcompany.pageobject.android.homepage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.BaseTest;
import com.yourcompany.base.Direction;
import com.yourcompany.base.Gestures;
import com.yourcompany.base.RotateMode;
import com.yourcompany.locators.android.homepage.ApiDemoLocator;

public class ApiDemoPage extends Gestures {
	public static Logger logger = LogManager.getLogger(ApiDemoPage.class.getName());
	private ApiDemoLocator apiDemoLocator;
	
	public ApiDemoPage(WebDriverWait wait, BaseTest b) {
		super(b.getDriver());
		apiDemoLocator = new ApiDemoLocator(wait);
	}
	
	public void performTap() throws Exception {
		tap(apiDemoLocator.getViews());
	}
	
	public void performDoubleTap() throws Exception {
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getButtons());
		doubleTap(apiDemoLocator.getToggleButton());
	}
	
	public void performDragAndDrop() throws Exception {
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getDragNDrop());
		drag(apiDemoLocator.getDragDot1(), apiDemoLocator.getDragDot4());
		Thread.sleep(3000);
	}
	
	//Scroll down
	public void performScroll() throws Exception {
		tap(apiDemoLocator.getViews());
		scrollTo("//*[@text='Lists']");
	}
	
	public void performFlick() throws Exception {
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getGallery());
		tap(apiDemoLocator.getPhotos());
		flick(apiDemoLocator.getGalleryContainer(), Direction.LEFT_TO_RIGHT, 2);
		flick(apiDemoLocator.getGalleryContainer(), Direction.RIGHT_TO_LEFT, 2);
	}
	
	public void performFlickTo() throws Exception {
		String targetLocator = "There is notthing";
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getGallery());
		tap(apiDemoLocator.getPhotos());
		flickTo(apiDemoLocator.getGalleryContainer(), Direction.LEFT_TO_RIGHT, targetLocator);
	}
	
	public void performSwipe() throws Exception {
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getGallery());
		tap(apiDemoLocator.getPhotos());
		swipe(apiDemoLocator.getGalleryContainer(), Direction.LEFT_TO_RIGHT, 1);
		swipe(apiDemoLocator.getGalleryContainer(), Direction.RIGHT_TO_LEFT, 1);
	}
	
	public void performSwipeTo() throws Exception {
		String targetLocator = "There is notthing";
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getGallery());
		tap(apiDemoLocator.getPhotos());
		swipeTo(apiDemoLocator.getGalleryContainer(), Direction.LEFT_TO_RIGHT, targetLocator);
	}
	
	public void performLongPress() throws Exception {
		tap(apiDemoLocator.getViews());
		tap(apiDemoLocator.getGallery());
		tap(apiDemoLocator.getPhotos());
		longPress(apiDemoLocator.getPhoto1());
	}
	
	public void performRotate() throws Exception {
		rotate(RotateMode.LANDSCAPE);
		Thread.sleep(2000);
		rotate(RotateMode.NORMAL);
	}
	
	public void performPressKey() throws Exception {
		pressBackButton();
		pressHomeButton();
		pressVolDownButton();
		pressVolUpButton();
		pressVolMuteButton();
	}
	
	public void performZoomOut() throws Exception {
		tap(apiDemoLocator.getGraphics());
		scrollTo("//*[@text='FingerPaint']");
		tap(apiDemoLocator.getFingerPaint());
		zoomOut(apiDemoLocator.getAndroidView());
	}
	
	public void performZoomIn() throws Exception {
		tap(apiDemoLocator.getGraphics());
		scrollTo("//*[@text='FingerPaint']");
		tap(apiDemoLocator.getFingerPaint());
		zoomIn(apiDemoLocator.getAndroidView());
	}
	
}
