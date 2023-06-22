package com.yourcompany.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public class ElementUtils {
	private AppiumDriver driver;
	private WebDriverWait wait;
	public ElementUtils(WebDriverWait wait) {
		this.wait = wait;
	}
	
	public ElementUtils(AppiumDriver driver) {
		this.driver = driver;
	}
	
	protected WebElement waitUntilElementVisibilityById(String id) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}
	
	protected WebElement waitUntilElementVisibilityByXpath(String xpath) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	protected WebElement waitUntilElementPresenceById(String id) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
	}
	
	protected WebElement waitUntilElementPresenceByXpath(String xpath) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}
	
	protected List<WebElement> getElementsByXpath(String xpath) {
		return driver.findElements(By.xpath(xpath));
	}
}
