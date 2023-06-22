package com.yourcompany.pageobject.android.homepage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.BaseTest;
import com.yourcompany.base.Gestures;
import com.yourcompany.locators.android.homepage.HomePageLocator;

public class HomePage extends Gestures {
	public static Logger logger = LogManager.getLogger(HomePage.class.getName());
	
	private HomePageLocator homePageLocator;

	public HomePage(WebDriverWait wait, BaseTest b) {
		super(b.getDriver());
		homePageLocator = new HomePageLocator(wait);
	}
	
	public void inputDataOnNameField(String name) {
		enterValue(homePageLocator.getNameTxb(), name);
	}
	
	public void tapOnLetsShopBtn() throws Exception {
		tap(homePageLocator.getLetsShopBtn());
	}
	
}
