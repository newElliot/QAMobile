package com.yourcompany.locators.android.homepage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.ElementUtils;

public class ProductPageLocator extends ElementUtils {
	
	public ProductPageLocator(WebDriverWait wait) {
		super(wait);
	}
	
	public WebElement getAddToCartBtn() {
		return waitUntilElementVisibilityById("productAddCart");
	}
}
