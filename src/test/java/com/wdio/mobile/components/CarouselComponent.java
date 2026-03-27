package com.wdio.mobile.components;


import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Carrusel en pantalla Swipe (APK antigua + native-demo-app v2.x).
 * En v2.x react-native-reanimated-carousel suele <strong>desmontar</strong> slides fuera de vista:
 * no se puede localizar el título de la primera tarjeta cuando ya estás en la última.
 */
public class CarouselComponent {


    private static final String[] SLIDE_TITLES = {
            "FULLY OPEN SOURCE",
            "GREAT COMMUNITY",
            "JS.FOUNDATION",
            "SUPPORT VIDEOS",
            "EXTENDABLE",
            "COMPATIBLE"
    };


    /** Fragmentos únicos para {@code textContains} / scroll (más estables que el título completo). */
    private static final String[] TITLE_SNIPPET = {
            "OPEN SOURCE",
            "COMMUNITY",
            "FOUNDATION",
            "SUPPORT",
            "EXTEND",
            "COMPATIBLE"
    };


    private final AndroidDriver driver;


    public CarouselComponent(AndroidDriver driver) {
        this.driver = driver;
    }


    private org.openqa.selenium.By carouselLocator() {
        return AppiumBy.xpath("//*[contains(@resource-id,'Carousel')]");
    }


    public WebElement carousel() {
        try {
            return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Carousel"), 5);
        } catch (Exception e) {
            return WaitUtils.waitVisible(driver, carouselLocator());
        }
    }


    private void settleCarousel() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void scrollSnippetIntoView(String snippet) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                            + "new UiSelector().textContains(\"" + snippet + "\"))"));
        } catch (Exception ignored) {
            // ya visible o otro scrollable
        }
    }


    private List<By> titleStrategies(String full, String snippet) {
        List<By> list = new ArrayList<>();
        list.add(AppiumBy.xpath("//*[@text='" + full + "']"));
        list.add(AppiumBy.xpath("//*[contains(@text,'" + snippet + "')]"));
        list.add(AppiumBy.xpath("//*[contains(@content-desc,'" + snippet + "')]"));
        list.add(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + snippet + "\")"));
        list.add(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"" + snippet + "\")"));
        return list;
    }


    private WebElement tryFindVisibleTitle(int index, String full, String snippet) {
        for (By by : titleStrategies(full, snippet)) {
            List<WebElement> els = driver.findElements(by);
            for (WebElement e : els) {
                try {
                    if (e.isDisplayed()) {
                        return e;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        List<WebElement> cards = driver.findElements(AppiumBy.accessibilityId("card"));
        if (index < cards.size()) {
            WebElement c = cards.get(index);
            if (c.isDisplayed()) {
                return c;
            }
        }
        return null;
    }


    public WebElement card(int index) {
        String legacyId = "__CAROUSEL_ITEM_" + index + "__";
        List<WebElement> legacy = driver.findElements(
                AppiumBy.xpath("//*[contains(@resource-id,'" + legacyId + "')]"));
        if (!legacy.isEmpty()) {
            return legacy.get(0);
        }


        WaitUtils.waitVisible(driver, carouselLocator(), 25);
        settleCarousel();


        String full = SLIDE_TITLES[index];
        String snippet = TITLE_SNIPPET[index];


        WebElement found = tryFindVisibleTitle(index, full, snippet);
        if (found != null) {
            return found;
        }


        scrollSnippetIntoView(snippet);
        settleCarousel();


        found = tryFindVisibleTitle(index, full, snippet);
        if (found != null) {
            return found;
        }


        return WaitUtils.waitVisible(driver, AppiumBy.xpath("//*[contains(@text,'" + snippet + "')]"));
    }


    public void waitUntilCardActive(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(25)).until(d -> {
            try {
                return isCardActive(card(index));
            } catch (Exception ex) {
                return false;
            }
        });
    }


    public boolean isCardActive(WebElement cardOrTitle) {
        Rectangle r = cardOrTitle.getRect();
        int x = r.getX();
        return x >= -10 && x < 280;
    }


    public void swipeForwardOnCarousel() {
        swipeOnCarousel("left");
    }


    public void swipeRightOnCarousel() {
        swipeOnCarousel("right");
    }


    @SuppressWarnings("unchecked")
    private void swipeOnCarousel(String direction) {
        WebElement el = WaitUtils.waitVisible(driver, carouselLocator(), 15);
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement) el).getId());
        params.put("direction", direction);
        params.put("percent", 0.75);
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}





