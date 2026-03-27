package com.wdio.mobile.base;

import com.wdio.mobile.config.AppConfig;
import com.wdio.mobile.pages.TabBarPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;


public abstract class BaseTest {

    protected static AndroidDriver driver;
    protected static TabBarPage tabBar;

    @BeforeSuite(alwaysRun = true)
    public void startAppiumSession() throws MalformedURLException {
        synchronized (BaseTest.class) {
            if (driver != null) {
                return;
            }
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName(AppConfig.deviceName())
                    .setApp(AppConfig.appPath().toAbsolutePath().toString())
                    .setNewCommandTimeout(Duration.ofSeconds(120))
                    .setAutoGrantPermissions(true);

            String udid = AppConfig.deviceUdid();
            if (!udid.isBlank()) {
                options.setUdid(udid);
            }

            driver = new AndroidDriver(
                    URI.create(AppConfig.appiumServerUrl()).toURL(),
                    options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            tabBar = new TabBarPage(driver);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void resetAppToFreshState() {
        String pkg = AppConfig.appPackage();
        try {
            driver.terminateApp(pkg);
        } catch (Exception ignored) {
        }
        driver.activateApp(pkg);
        tabBar.waitForTabBarShown();
    }

    @AfterSuite(alwaysRun = true)
    public void quitSession() {
        synchronized (BaseTest.class) {
            if (driver != null) {
                driver.quit();
                driver = null;
                tabBar = null;
            }
        }
    }
}
