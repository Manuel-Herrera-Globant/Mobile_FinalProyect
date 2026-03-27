package com.wdio.mobile.pages;


import com.wdio.mobile.config.AppConfig;
import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;



public class NativeAlert {


    private static final int ALERT_TIMEOUT_SEC = 25;


    private final AndroidDriver driver;


    public NativeAlert(AndroidDriver driver) {
        this.driver = driver;
    }


    public void waitForVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(ALERT_TIMEOUT_SEC)).until(d -> titleElement() != null);
    }


    private WebElement titleElement() {
        List<By> titleLocators = List.of(
                AppiumBy.id("android:id/alertTitle"),
                AppiumBy.id(AppConfig.appPackage() + ":id/alert_title"),
                AppiumBy.xpath("//android.widget.TextView[@text='Success']"),
                AppiumBy.xpath("//android.widget.TextView[@text='Signed Up!']")
        );
        for (By by : titleLocators) {
            List<WebElement> found = driver.findElements(by);
            if (!found.isEmpty() && found.get(0).isDisplayed()) {
                return found.get(0);
            }
        }
        return null;
    }


    public String fullText() {
        waitForVisible();
        WebElement title = titleElement();
        String titleText = title != null ? title.getText() : "";
        String message = "";
        List<WebElement> msg = driver.findElements(AppiumBy.id("android:id/message"));
        if (!msg.isEmpty()) {
            message = msg.get(0).getText();
        }
        return (titleText + "\n" + message).trim();
    }


    public void tapOk() {
        By ok = AppiumBy.xpath("//android.widget.Button[@text='OK']");
        WaitUtils.waitClickable(driver, ok).click();
    }
}




