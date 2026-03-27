package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;


public class TabBarPage {

    private final AndroidDriver driver;

    public TabBarPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitForTabBarShown() {
        WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Home"));
    }

    public void openHome() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Home")).click();
    }

    public void openWebView() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Webview")).click();
    }

    public void openLogin() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Login")).click();
    }

    public void openForms() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Forms")).click();
    }

    public void openSwipe() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Swipe")).click();
    }

    public void openDrag() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("Drag")).click();
    }
}
