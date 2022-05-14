package org.Lesson3_Maven_BackEnd_test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


public abstract class Initializalization {

    private final String apiHash = "3b4454bd81660db89dd0a7406e4dce5702fc4f60";
    private final String apiKey = "8aa096497e0b48e08e8468aa3a1d62a4";

    @BeforeAll
    static void beforeAll() {
        RestAssured.filters(new AllureRestAssured());
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getApiHash() {
        return apiHash;
    }
}
