package com.yourcompany.locators.android.homepage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.ElementUtils;

public class HomePageLocator extends ElementUtils {
	public HomePageLocator(WebDriverWait wait) {
		super(wait);
	}
	
	public WebElement getNameTxb() {
		return waitUntilElementVisibilityById("nameField");
	}
	
	public WebElement getLetsShopBtn() {
		return waitUntilElementVisibilityById("btnLetsShop");
	}
}
