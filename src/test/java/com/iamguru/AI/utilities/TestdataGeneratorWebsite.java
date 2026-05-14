package com.iamguru.AI.utilities;

import java.util.UUID;
import java.util.Random;

public class TestdataGeneratorWebsite {

    private static final Random random = new Random();

    public static String getRandomFirstName() {
        return "smith" + random.nextInt(1000);
    }

    public static String getRandomLastName() {
        return "john" + random.nextInt(1000);
    }

    public static String getRandomEmail() {
        return "smith" + System.currentTimeMillis() + "@mail.com";
    }

    public static String getRandomPhone() {
        return "9" + (100000000 + random.nextInt(900000000));
    }

    public static String getRandomAgentName() {
        return "Agent_" + UUID.randomUUID().toString().substring(0, 5);
    }
}