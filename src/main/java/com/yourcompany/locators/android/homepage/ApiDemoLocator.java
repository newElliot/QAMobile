package com.yourcompany.locators.android.homepage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.yourcompany.base.ElementUtils;

import io.appium.java_client.AppiumDriver;

public class ApiDemoLocator extends ElementUtils {
	
	public ApiDemoLocator(WebDriverWait wait) {
		super(wait);
	}
	
	public ApiDemoLocator(AppiumDriver driver) {
		super(driver);
	}

	public WebElement getViews() {
		return waitUntilElementVisibilityByXpath("//*[@text='Views']");
	}
	
	public WebElement getDragNDrop() {
		return waitUntilElementVisibilityByXpath("//*[@text='Drag and Drop']");
	}
	
	public WebElement getGallery() {
		return waitUntilElementVisibilityByXpath("//*[@text='Gallery']");
	}
	
	public WebElement getPhotos() {
		return waitUntilElementVisibilityByXpath("//*[@text='1. Photos']");
	}
	
	public WebElement getPhoto1() {
		return waitUntilElementVisibilityByXpath("//*[@class='android.widget.ImageView']");
	}
	
	public WebElement getGraphics() {
		return waitUntilElementVisibilityByXpath("//*[@text='Graphics']");
	}
	
	public WebElement getFingerPaint() {
		return waitUntilElementVisibilityByXpath("//*[@text='FingerPaint']");
	}
	
	public WebElement getDragDot1() {
		return waitUntilElementVisibilityById("drag_dot_1");
	}
	
	public WebElement getDragDot2() {
		return waitUntilElementVisibilityById("drag_dot_2");
	}
	
	public WebElement getDragDot3() {
		return waitUntilElementVisibilityById("drag_dot_3");
	}
	
	public WebElement getDragDot4() {
		return waitUntilElementPresenceByXpath("//android.widget.RelativeLayout");
	}
	
	public WebElement getGalleryContainer() {
		return waitUntilElementVisibilityById("gallery");
	}
	
	public WebElement getButtons() {
		return waitUntilElementVisibilityByXpath("//*[@text='Buttons']");
	}
	
	public WebElement getToggleButton() {
		return waitUntilElementVisibilityById("button_toggle");
	}
	
	public WebElement getAndroidView() {
		return waitUntilElementVisibilityByXpath("//android.view.View");
	}
	
	public String getTabs() {
		return "//*[@text='Tabs']";
	}
	
	public WebElement getToastMessage() {
		return waitUntilElementVisibilityByXpath("//android.widget.Toast[1]");
	}
	
	public WebElement getPopupMenu() {
		return waitUntilElementVisibilityByText("Popup Menu");
	}
	
	public WebElement getMakePopupButton() {
		return waitUntilElementVisibilityByText("MAKE A POPUP!");
	}
	
	public WebElement getSearchOption() {
		return waitUntilElementVisibilityByText("Search");
	}
}
