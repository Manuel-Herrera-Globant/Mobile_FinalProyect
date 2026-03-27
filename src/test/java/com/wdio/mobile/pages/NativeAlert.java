package com.wdio.mobile.pages;

import com.wdio.mobile.config.AppConfig;
import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;


public class NativeAlert {

    private final AndroidDriver driver;

    public NativeAlert(AndroidDriver driver) {
        this.driver = driver;
    }

    private By alertTitle() {
        return AppiumBy.id(AppConfig.appPackage() + ":id/alert_title");
    }

    private By alertMessage() {
        return AppiumBy.id("android:id/message");
    }

    public void waitForVisible() {
        WaitUtils.waitVisible(driver, alertTitle(), 15);
    }

    public String fullText() {
        waitForVisible();
        String title = driver.findElement(alertTitle()).getText();
        String msg = driver.findElement(alertMessage()).getText();
        return title + "\n" + msg;
    }

    public void tapOk() {
        By ok = AppiumBy.xpath("//android.widget.Button[@text='OK']");
        WaitUtils.waitClickable(driver, ok).click();
    }
}
