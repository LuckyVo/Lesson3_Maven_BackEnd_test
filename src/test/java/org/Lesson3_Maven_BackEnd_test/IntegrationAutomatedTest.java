package org.Lesson3_Maven_BackEnd_test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class IntegrationAutomatedTest extends Initializalization{


    @Test
    @Tag("Positive")
    @DisplayName("POST. Add to Shopping List")
    void mealPlanTest() throws IOException {
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1589500800,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"RECIPE\",\n"
                        + " \"value\": {\n"
                        + " \"id\": 1171485,\n"
                        + " \"servings\": 8,\n"
                        + " \"title\": \"Chocolate & Pistachio Naked Layer Cake\",\n"
                        + " \"imageType\": \"jpg\"\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getURL() + "/mealplanner/" + getUserName() + "/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .prettyPeek()
                .get("id")
                .toString();

        tearDown(getURL() + "/mealplanner/" + getUserName() + "/items/" + id);
    }
}
