package com.wdio.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;


public final class AppConfig {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            Objects.requireNonNull(in, "config.properties no encontrado en classpath");
            PROPS.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private AppConfig() {
    }

    public static String get(String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }
        return PROPS.getProperty(key, defaultValue);
    }

    public static String require(String key) {
        String v = get(key, null);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Falta configuración: " + key + " (config.properties o -D" + key + ")");
        }
        return v.trim();
    }

    public static Path appPath() {
        return Path.of(require("app.path"));
    }

    public static String appiumServerUrl() {
        return require("appium.server.url");
    }

    public static String appPackage() {
        return require("app.package");
    }

    public static String deviceName() {
        return get("device.name", "Android Emulator");
    }

    public static String deviceUdid() {
        return get("device.udid", "");
    }
}
