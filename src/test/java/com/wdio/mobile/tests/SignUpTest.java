package com.wdio.mobile.tests;

import com.wdio.mobile.base.BaseTest;
import com.wdio.mobile.pages.LoginPage;
import com.wdio.mobile.pages.NativeAlert;
import com.wdio.mobile.util.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

//Test of the sign-up functionality, validating the success message through a native alert.

public class SignUpTest extends BaseTest {

    @Test
    public void successfulSignUp() {
        tabBar.openLogin();
        LoginPage login = new LoginPage(driver);
        String email = TestDataGenerator.uniqueEmail();
        String password = TestDataGenerator.validPassword();

        login.signUp(email, password);

        NativeAlert alert = new NativeAlert(driver);
        alert.waitForVisible();
        String text = alert.fullText().toLowerCase();
        Assert.assertTrue(text.contains("signed"), "Mensaje de registro exitoso: " + text);
        alert.tapOk();
    }
}
