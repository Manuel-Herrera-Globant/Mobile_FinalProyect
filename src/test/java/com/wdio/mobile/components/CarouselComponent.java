package com.wdio.mobile.components;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

//Component class for interacting with a carousel element in the mobile app

public class CarouselComponent {

    private final AndroidDriver driver;

    public CarouselComponent(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement carousel() {
        try {
            return driver.findElement(AppiumBy.accessibilityId("Carousel"));
        } catch (Exception e) {
            return driver.findElement(AppiumBy.xpath(
                    "//*[contains(@resource-id,'Carousel') and not(contains(@resource-id,'CAROUSEL_ITEM'))]"));
        }
    }

    public void waitUntilCardActive(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> {
            try {
                return isCardActive(card(index));
            } catch (Exception ex) {
                return false;
            }
        });
    }

    public WebElement card(int index) {
        String id = "__CAROUSEL_ITEM_" + index + "__";
        return driver.findElement(AppiumBy.xpath("//*[contains(@resource-id,'" + id + "')]"));
    }

    public boolean isCardActive(WebElement card) {
        Rectangle r = card.getRect();
        return r.getX() == 0;
    }

    /**
     * Avanza el carrusel (gesto hacia la izquierda sobre el contenedor).
     */
    public void swipeForwardOnCarousel() {
        swipeOnCarousel("left");
    }

    /**
     * Deslizar hacia la derecha (gesto típico “hacia atrás” en el carrusel).
     */
    public void swipeRightOnCarousel() {
        swipeOnCarousel("right");
    }

    @SuppressWarnings("unchecked")
    private void swipeOnCarousel(String direction) {
        WebElement el = carousel();
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) el).getId());
        params.put("direction", direction);
        params.put("percent", 0.75);
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
    }
}
