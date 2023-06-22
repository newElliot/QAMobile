package com.yourcompany.pageobject.android.homepage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.BaseTest;
import com.yourcompany.base.Gestures;
import com.yourcompany.locators.android.homepage.ProductPageLocator;

public class ProductPage extends Gestures {
	public static Logger logger = LogManager.getLogger(ProductPage.class.getName());
	
	private ProductPageLocator productPageLocator;
	
	public ProductPage(WebDriverWait wait, BaseTest b) {
		super(b.getDriver());
		productPageLocator = new ProductPageLocator(wait);
	}
	
	public void addGoodsToCart() throws Exception {
		tap(productPageLocator.getAddToCartBtn());
	}
}
