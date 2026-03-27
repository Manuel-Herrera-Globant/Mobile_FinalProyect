package com.wdio.mobile.util;

import java.util.UUID;

//Dynamic Data Generator for tests 

public final class TestDataGenerator {

    private TestDataGenerator() {
    }

    public static String uniqueEmail() {
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        return "user." + token + "@test.local";
    }

    public static String validPassword() {
        return "Test1234!";
    }
}
