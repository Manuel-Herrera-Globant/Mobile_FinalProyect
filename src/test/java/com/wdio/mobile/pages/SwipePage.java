package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


public class SwipePage {

    private final AndroidDriver driver;

    public SwipePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement screen() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Swipe-screen"));
    }

    public WebElement logo() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("WebdriverIO logo"));
    }
}
