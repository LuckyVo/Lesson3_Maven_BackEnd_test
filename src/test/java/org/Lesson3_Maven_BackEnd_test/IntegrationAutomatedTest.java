package org.Lesson3_Maven_BackEnd_test;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class IntegrationAutomatedTest extends Initializalization{

    @Test
    void mealPlanTest() {
        String id = given()
                .queryParam("hash", getApiHash())
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
                .post("https://api.spoonacular.com/mealplanner/Vladimir1990/items/")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .prettyPeek()
                .get("id")
                .toString();

        given()
                .queryParam("hash", getApiHash())
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/Vladimir1990/items/" + id)
                .then()
                .statusCode(200);
    }
}
