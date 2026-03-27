package com.wdio.mobile.tests;

import com.wdio.mobile.base.BaseTest;
import com.wdio.mobile.components.CarouselComponent;
import com.wdio.mobile.pages.SwipePage;
import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

//Test of the swipe functionality in the carousel, validating that only the active card is aligned at x=0 and that vertical scrolling can find a specific element.

public class SwipeCardsTest extends BaseTest {

    @Test
    public void swipeForwardHidesPreviousCard() {
        tabBar.openSwipe();
        new SwipePage(driver).screen();
        CarouselComponent carousel = new CarouselComponent(driver);

        Assert.assertTrue(carousel.isCardActive(carousel.card(0)), "Primera tarjeta activa al inicio");

        carousel.swipeForwardOnCarousel();
        carousel.waitUntilCardActive(1);
        Assert.assertTrue(carousel.isCardActive(carousel.card(1)), "Segunda tarjeta activa");
        Assert.assertFalse(carousel.isCardActive(carousel.card(0)), "La tarjeta anterior ya no está alineada a x=0");

        carousel.swipeRightOnCarousel();
        carousel.waitUntilCardActive(0);
        Assert.assertTrue(carousel.isCardActive(carousel.card(0)), "Swipe a la derecha vuelve a la primera");
    }

    @Test
    public void lastCardIsTheOnlyOneAlignedAsActive() {
        tabBar.openSwipe();
        new SwipePage(driver).screen();
        CarouselComponent carousel = new CarouselComponent(driver);

        for (int i = 0; i < 5; i++) {
            carousel.swipeForwardOnCarousel();
            carousel.waitUntilCardActive(i + 1);
        }

        WebElement last = carousel.card(5);
        Assert.assertTrue(carousel.isCardActive(last), "Última tarjeta activa");
        for (int i = 0; i < 5; i++) {
            Assert.assertFalse(carousel.isCardActive(carousel.card(i)),
                    "Las tarjetas anteriores no deben estar activas en x=0");
        }
    }

    @Test
    public void verticalScrollFindsYouFoundMe() {
        tabBar.openSwipe();
        new SwipePage(driver).screen();

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"You found me\"))"));

        WebElement surprise = WaitUtils.waitVisible(driver,
                AppiumBy.xpath("//*[contains(@text,'You found me')]"));
        Assert.assertTrue(surprise.isDisplayed(), "Texto sorpresa visible");
        Assert.assertTrue(surprise.getText().contains("You found me"), "Texto exacto esperado");
    }
}
