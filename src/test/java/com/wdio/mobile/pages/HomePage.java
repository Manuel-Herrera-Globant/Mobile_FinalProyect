package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


public class HomePage {

    private final AndroidDriver driver;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement screen() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Home-screen"));
    }

    public WebElement webdriverTitle() {
        return WaitUtils.waitVisible(driver, AppiumBy.xpath("//android.widget.TextView[contains(@text,'WEBDRIVER')]"));
    }

    public WebElement subtitle() {
        return WaitUtils.waitVisible(driver, AppiumBy.xpath("//android.widget.TextView[contains(@text,'Demo app for the appium-boilerplate')]"));
    }
}
