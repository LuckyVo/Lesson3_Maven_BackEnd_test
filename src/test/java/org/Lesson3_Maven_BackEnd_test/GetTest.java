package org.Lesson3_Maven_BackEnd_test;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetTest extends Initializalization{


    @Test
    @Tag("Negative")
    @DisplayName("GET. No apiKey")
    void getRecipeBurgerNegativeTest() throws IOException {
        given()
                .log()
                .all()
                .queryParam("query", "burger")
                .expect()
                .body("status", equalTo("failure"))
                .body("code", equalTo(401))
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(401);
    }


    @Test
    @Tag("Positive")
    @DisplayName("GET. Search recipe pasta")
    void getRecipePastaPositiveTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].title"), containsString("Pasta"));
            index--;
        }
    }


    @Test
    @Tag("Positive")
    @DisplayName("GET. Search recipe salad")
    void getRecipeSaladPositiveTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "salad")
                .queryParam("sort","calories")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].nutrition.nutrients[0].amount"), lessThanOrEqualTo(1029.68F));
            index--;
        }
    }


    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by cooking time")
    void getSearchByCookingTimeTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", 5)
                .log()
                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].readyInMinutes"), lessThanOrEqualTo(5));
            index--;
        }
    }


    @Test
    @Tag("Positive")
    @DisplayName("GET. Search recipe steak")
    void getRecipeSteakPositiveTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "steak")
                .queryParam("sort","calories")
                .queryParam("sortDirection","asc")
                .queryParam("minCalories", 100)
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int maxIndex = getMaxIndex(response);
        int i = 0;
        while (i <= maxIndex) {
            assertThat(response.get("results[" + i + "].nutrition.nutrients[0].name"), equalToIgnoringCase("Calories"));
            if (i == 0) {
                assertThat(response.get("results[" + i + "].nutrition.nutrients[0].amount"), greaterThanOrEqualTo(100f));
            }
            if (i != maxIndex) {
                assertThat((Float) response.get("results[" + i + "].nutrition.nutrients[0].amount") <=
                        (Float) response.get("results[" + (i + 1) + "].nutrition.nutrients[0].amount"), is (true));
            }
            i++;
        }
    }


    @Test
    @Tag("Positive")
    @DisplayName("GET. Search recipe soup")
    void getRecipeSoupPositiveTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "soup")
                .queryParam("addRecipeInformation", true)
                .queryParam("includeIngredients", "tomato")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int maxIndex = getMaxIndex(response);
        int i = 0;
        while (i <= maxIndex) {
            assertThat(response.get("results[" + i + "].title"), containsString("Soup"));
            }
            i++;
    }




}
