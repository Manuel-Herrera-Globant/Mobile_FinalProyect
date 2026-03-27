package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


public class WebViewPage {

    private final AndroidDriver driver;

    public WebViewPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement webView() {
        return WaitUtils.waitVisible(driver, AppiumBy.xpath("//android.webkit.WebView"), 45);
    }
}
