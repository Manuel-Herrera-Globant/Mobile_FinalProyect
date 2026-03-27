package com.wdio.mobile.pages;

import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {

    private final AndroidDriver driver;

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement screen() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("Login-screen"));
    }

    public void openLoginForm() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("button-login-container")).click();
    }

    public void openSignUpForm() {
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("button-sign-up-container")).click();
    }

    public WebElement emailField() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("input-email"));
    }

    public WebElement passwordField() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("input-password"));
    }

    public WebElement repeatPasswordField() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("input-repeat-password"));
    }

    public WebElement loginSubmitButton() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-LOGIN"));
    }

    public WebElement signUpSubmitButton() {
        return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-SIGN UP"));
    }

    public void dismissKeyboardByTappingScreen() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            screen().click();
        }
    }

    /**
     * Flujo completo de registro reutilizable desde tests de login y signup.
     */
    public void signUp(String email, String password) {
        openSignUpForm();
        emailField().clear();
        emailField().sendKeys(email);
        passwordField().clear();
        passwordField().sendKeys(password);
        repeatPasswordField().clear();
        repeatPasswordField().sendKeys(password);
        dismissKeyboardByTappingScreen();
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("button-SIGN UP")).click();
    }

    public void login(String email, String password) {
        openLoginForm();
        emailField().clear();
        emailField().sendKeys(email);
        passwordField().clear();
        passwordField().sendKeys(password);
        dismissKeyboardByTappingScreen();
        WaitUtils.waitClickable(driver, AppiumBy.accessibilityId("button-LOGIN")).click();
    }
}
