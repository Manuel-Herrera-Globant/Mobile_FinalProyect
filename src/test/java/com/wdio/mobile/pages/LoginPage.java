package com.wdio.mobile.pages;


import com.wdio.mobile.util.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
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
        clickFirstDisplayed(
                AppiumBy.accessibilityId("login-container"),
                AppiumBy.accessibilityId("button-login-container"));
    }


    public void openSignUpForm() {
        clickFirstDisplayed(
                AppiumBy.accessibilityId("sign-up-container"),
                AppiumBy.accessibilityId("button-sign-up-container"));
    }


    private void clickFirstDisplayed(By primary, By fallback) {
        try {
            WaitUtils.waitClickable(driver, primary, 8).click();
        } catch (Exception e) {
            WaitUtils.waitClickable(driver, fallback).click();
        }
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
        try {
            return WaitUtils.waitClickable(driver,
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")"), 8);
        } catch (Exception e) {
            return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-LOGIN"));
        }
    }


    public WebElement signUpSubmitButton() {
        try {
            return WaitUtils.waitClickable(driver,
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"SIGN UP\")"), 8);
        } catch (Exception e) {
            return WaitUtils.waitVisible(driver, AppiumBy.accessibilityId("button-SIGN UP"));
        }
    }


    private void tapSubmit(String text, String legacyId) {
        try {
            WaitUtils.waitClickable(driver,
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"), 10).click();
        } catch (Exception e) {
            WaitUtils.waitClickable(driver, AppiumBy.accessibilityId(legacyId)).click();
        }
    }


    public void dismissKeyboardByTappingScreen() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            screen().click();
        }
    }


    public void signUp(String email, String password) {
        openSignUpForm();
        emailField().clear();
        emailField().sendKeys(email);
        passwordField().clear();
        passwordField().sendKeys(password);
        repeatPasswordField().clear();
        repeatPasswordField().sendKeys(password);
        dismissKeyboardByTappingScreen();
        tapSubmit("SIGN UP", "button-SIGN UP");
        waitForSubmitProcessing();
    }


    public void login(String email, String password) {
        openLoginForm();
        emailField().clear();
        emailField().sendKeys(email);
        passwordField().clear();
        passwordField().sendKeys(password);
        dismissKeyboardByTappingScreen();
        tapSubmit("LOGIN", "button-LOGIN");
        waitForSubmitProcessing();
    }

    private void waitForSubmitProcessing() {
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}




