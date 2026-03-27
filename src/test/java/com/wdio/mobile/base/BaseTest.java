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


/**
 * Sesión Appium compartida; cada método de prueba reinicia la app para independencia.
 * TestNG ejecuta {@code @BeforeSuite} por cada clase de test: una sola sesión (guard).
 * <p>
 * Si UiAutomator2 en el dispositivo se cae (p. ej. tras muchos {@code swipeGesture}),
 * {@link #resetAppToFreshState} intenta recrear el driver una vez.
 */
public abstract class BaseTest {


    protected static AndroidDriver driver;
    protected static TabBarPage tabBar;


    @BeforeSuite(alwaysRun = true)
    public void startAppiumSession() throws MalformedURLException {
        synchronized (BaseTest.class) {
            if (driver == null) {
                initDriverLocked();
            }
        }
    }


    private static UiAutomator2Options buildOptions() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(AppConfig.deviceName())
                .setApp(AppConfig.appPath().toAbsolutePath().toString())
                .setNewCommandTimeout(Duration.ofSeconds(180))
                .setAutoGrantPermissions(true);


        String udid = AppConfig.deviceUdid();
        if (!udid.isBlank()) {
            options.setUdid(udid);
        }
        return options;
    }


    private static void initDriverLocked() throws MalformedURLException {
        driver = new AndroidDriver(
                URI.create(AppConfig.appiumServerUrl()).toURL(),
                buildOptions());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        tabBar = new TabBarPage(driver);
    }


    private static void quitDriverQuietly() {
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } catch (Exception ignored) {
            // sesión ya rota
        }
        driver = null;
        tabBar = null;
    }


    private static boolean isRecoverableDriverFailure(Throwable e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            String m = String.valueOf(t.getMessage()).toLowerCase();
            if (m.contains("instrumentation")
                    || m.contains("socket hang up")
                    || m.contains("cannot be proxied")
                    || m.contains("probably crashed")) {
                return true;
            }
        }
        return false;
    }


    private void resetAppBody() throws Exception {
        String pkg = AppConfig.appPackage();
        try {
            driver.terminateApp(pkg);
        } catch (Exception ignored) {
            // app ya cerrada o sesión inestable
        }
        Thread.sleep(500);
        driver.activateApp(pkg);
        Thread.sleep(1000);
        tabBar.waitForTabBarShown();
    }


    @BeforeMethod(alwaysRun = true)
    public void resetAppToFreshState() throws Exception {
        synchronized (BaseTest.class) {
            if (driver == null) {
                initDriverLocked();
            } else {
                try {
                    driver.getPageSource();
                } catch (RuntimeException ping) {
                    if (isRecoverableDriverFailure(ping)) {
                        quitDriverQuietly();
                        initDriverLocked();
                    } else {
                        throw ping;
                    }
                }
            }
        }
        try {
            resetAppBody();
        } catch (Exception e) {
            if (!isRecoverableDriverFailure(e)) {
                throw e;
            }
            synchronized (BaseTest.class) {
                quitDriverQuietly();
                initDriverLocked();
            }
            resetAppBody();
        }
    }


    @AfterSuite(alwaysRun = true)
    public void quitSession() {
        synchronized (BaseTest.class) {
            quitDriverQuietly();
        }
    }
}





