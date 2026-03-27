package com.wdio.mobile.util;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//Class for explicit waits in Appium tests
public final class WaitUtils {

    public static final int DEFAULT_TIMEOUT_SEC = 25;

    private WaitUtils() {
    }

    public static WebElement waitVisible(AppiumDriver driver, By locator) {
        return waitVisible(driver, locator, DEFAULT_TIMEOUT_SEC);
    }

    public static WebElement waitVisible(AppiumDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitClickable(AppiumDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SEC))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitInvisible(AppiumDriver driver, By locator, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
