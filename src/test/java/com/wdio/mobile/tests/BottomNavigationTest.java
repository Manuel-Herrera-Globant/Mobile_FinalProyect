package com.wdio.mobile.tests;


import com.wdio.mobile.base.BaseTest;
import com.wdio.mobile.pages.*;
import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Navegación por la barra inferior desde Home y validación de elementos clave por sección.
 */
public class BottomNavigationTest extends BaseTest {


    @Test
    public void homeSectionShowsExpectedContent() {
        tabBar.openHome();
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.screen().isDisplayed(), "Home-screen visible");
        Assert.assertTrue(home.webdriverTitle().isDisplayed(), "Título WEBDRIVER visible");
        WebElement sub = home.subtitle();
        Assert.assertTrue(sub.isDisplayed() && sub.getText().toLowerCase().contains("demo app"),
                "Subtítulo de la app visible");
    }


    @Test
    public void webViewSectionShowsWebView() {
        tabBar.openWebView();
        WebViewPage web = new WebViewPage(driver);
        Assert.assertTrue(web.webView().isDisplayed(), "WebView presente");
    }


    @Test
    public void loginSectionShowsFormControls() {
        tabBar.openLogin();
        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.screen().isDisplayed(), "Login-screen visible");
        login.openLoginForm();
        WebElement email = login.emailField();
        WebElement pass = login.passwordField();
        Assert.assertTrue(email.isDisplayed() && email.isEnabled(), "Email");
        Assert.assertTrue(pass.isDisplayed() && pass.isEnabled(), "Password");
        Assert.assertTrue(login.loginSubmitButton().isDisplayed(), "Botón LOGIN");
        login.openSignUpForm();
        Assert.assertTrue(login.repeatPasswordField().isDisplayed(), "Repeat password");
        Assert.assertTrue(login.signUpSubmitButton().isDisplayed(), "Botón SIGN UP");
    }


    @Test
    public void formsSectionShowsExpectedWidgets() {
        tabBar.openForms();
        FormsPage forms = new FormsPage(driver);
        Assert.assertTrue(forms.screen().isDisplayed(), "Forms-screen visible");
        Assert.assertTrue(forms.textInput().isDisplayed() && forms.textInput().isEnabled(), "Input texto");
        Assert.assertTrue(forms.toggleSwitch().isDisplayed(), "Switch");
        Assert.assertTrue(forms.switchText().isDisplayed(), "Texto del switch");
        Assert.assertTrue(forms.dropdown().isDisplayed(), "Dropdown");
        Assert.assertTrue(forms.activeButton().isDisplayed() && forms.activeButton().isEnabled(), "Active");
        Assert.assertTrue(forms.inactiveButton().isDisplayed(), "Inactive");
    }


    @Test
    public void swipeSectionShowsCarousel() {
        tabBar.openSwipe();
        SwipePage swipe = new SwipePage(driver);
        Assert.assertTrue(swipe.screen().isDisplayed(), "Swipe-screen visible");
        WaitUtils.waitVisible(driver, AppiumBy.xpath("//*[contains(@resource-id,'Carousel')]"));
        Assert.assertTrue(
                WaitUtils.waitVisible(driver, AppiumBy.xpath("//*[@text='FULLY OPEN SOURCE']")).isDisplayed(),
                "Primera tarjeta del carrusel visible");
    }


    @Test
    public void dragSectionShowsPuzzle() {
        tabBar.openDrag();
        DragPage drag = new DragPage(driver);
        Assert.assertTrue(drag.screen().isDisplayed(), "Drag-drop-screen visible");
        Assert.assertTrue(drag.pieceTopLeft().isDisplayed(), "Pieza drag-l1");
    }
}




