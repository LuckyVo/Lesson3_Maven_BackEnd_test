package org.Lesson3_Maven_BackEnd_test;

import io.restassured.path.json.JsonPath;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public abstract class Initializalization {

    final static java.util.Properties prop = new java.util.Properties();

    private static void loadProperties() throws IOException {
        try (FileInputStream configFile = new FileInputStream("src/test/resources/properties.properties")) {
            prop.load(configFile);
        }
    }


    public static String getURL() throws IOException {
        loadProperties();
        return prop.getProperty("baseURL");
    }

    public static String getApiKey() throws IOException {
        loadProperties();
        return prop.getProperty("apiKey");
    }

    public static String getHash() throws IOException {
        loadProperties();
        return prop.getProperty("hash");
    }

    public static String getUserName() throws IOException {
        loadProperties();
        return prop.getProperty("userName");
    }

    static void tearDown(String url) throws IOException {
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .delete(url)
                .then()
                .statusCode(200);
    }

    static Integer getMaxIndex(JsonPath response) {
        return new ArrayList<>(response.get("results")).size() - 1;
    }

}
