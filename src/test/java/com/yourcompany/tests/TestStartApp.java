package com.yourcompany.tests;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.yourcompany.base.BaseTest;
import com.yourcompany.pageobject.android.homepage.ApiDemoPage;
import com.yourcompany.pageobject.android.homepage.HomePage;
import com.yourcompany.pageobject.android.homepage.ProductPage;

public class TestStartApp extends BaseTest {
	
	@BeforeMethod
	public void preConditions() throws Exception {
		logger = LogManager.getLogger(TestStartApp.class);
	}

	//General store
	@Test(enabled=false)
	public void generalStoreTest() throws Exception {
		HomePage homePage = new HomePage(wait, this);
		ProductPage productPage = new ProductPage(wait, this);
		
		logger.info("*** Step 1: Enter value on NameFiled ***");
		homePage.inputDataOnNameField("abc");
		
		logger.info("*** Step 2: Click on Lets Shop button ***");
		homePage.tapOnLetsShopBtn();
		
		logger.info("*** Step 3: Buy goods ***");
		productPage.addGoodsToCart();
	}
	
	@Test(enabled=true, invocationCount = 5)
	public void tap() throws Exception{
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performTap();
	}
	
	@Test(enabled=false)
	public void doubleTap() throws Exception{
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performDoubleTap();
	}
	
	@Test(enabled=false)
	public void dragAndDrop() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performDragAndDrop();
	}
	
	@Test(enabled=false)
	public void scrollDown() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performScroll();
	}
	
	@Test(enabled=false)
	public void flick() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performFlick();
	}
	
	@Test(enabled=false)
	public void flickTo() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performFlickTo();
	}
	
	@Test(enabled=false)
	public void swipe() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performSwipe();
	}
	
	@Test(enabled=false)
	public void swipeTo() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performSwipeTo();
	}
	
	@Test(enabled=false)
	public void longPress() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performLongPress();
	}
	//Zoom-in
	@Test(enabled=false)
	public void zoomout() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performZoomOut();
	}
	
	//Zoom-out
	@Test(enabled=true)
	public void zoomin() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performZoomIn();
	}
	
	@Test(enabled=false)
	public void rotate() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performRotate();
	}
	
	@Test(enabled=false)
	public void pressKey() throws Exception {
		ApiDemoPage apiPage = new ApiDemoPage(wait, this);
		apiPage.performPressKey();
		Assert.assertFalse(true);
	}
}
