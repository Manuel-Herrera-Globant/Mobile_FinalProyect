package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


public class FormsPage {

    private final AndroidDriver driver;

    public FormsPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement screen() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Forms-screen"));
    }

    public WebElement textInput() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("text-input"));
    }

    public WebElement inputTextResult() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("input-text-result"));
    }

    public WebElement toggleSwitch() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("switch"));
    }

    public WebElement switchText() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("switch-text"));
    }

    public WebElement dropdown() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Dropdown"));
    }

    public WebElement activeButton() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-Active"));
    }

    public WebElement inactiveButton() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-Inactive"));
    }
}
