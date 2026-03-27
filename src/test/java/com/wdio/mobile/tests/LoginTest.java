package com.wdio.mobile.tests;

import com.wdio.mobile.base.BaseTest;
import com.wdio.mobile.pages.LoginPage;
import com.wdio.mobile.pages.NativeAlert;
import com.wdio.mobile.util.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

//Test of the login functionality, including both sign-up and login processes, with validation of success messages through native alerts.

public class LoginTest extends BaseTest {

    @Test
    public void successfulLogin() {
        tabBar.openLogin();
        LoginPage login = new LoginPage(driver);
        String email = TestDataGenerator.uniqueEmail();
        String password = TestDataGenerator.validPassword();

        login.signUp(email, password);
        NativeAlert alert = new NativeAlert(driver);
        alert.waitForVisible();
        alert.tapOk();

        login.login(email, password);
        alert.waitForVisible();
        String text = alert.fullText().toLowerCase();
        Assert.assertTrue(text.contains("success"), "Mensaje de login exitoso: " + text);
        alert.tapOk();
    }
}
