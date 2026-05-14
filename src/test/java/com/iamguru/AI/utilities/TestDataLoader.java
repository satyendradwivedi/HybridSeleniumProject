package com.iamguru.AI.utilities;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamguru.AI.testdata.User;

public class TestDataLoader {

    public static User getUserData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                new File("src/test/resources/testdata/userData.json"),
                User.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data: " + e.getMessage());
        }
    }
}