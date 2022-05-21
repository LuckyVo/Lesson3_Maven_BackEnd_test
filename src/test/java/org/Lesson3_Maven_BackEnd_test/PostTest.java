package org.Lesson3_Maven_BackEnd_test;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostTest extends Initializalization{


    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (American)")
    void classifyCuisineWithoutQueryParametersTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasEntry("cuisine", "American"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("american"));
        assertThat(response.get("cuisines"), hasItem("American"));
    }


    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (Italian)")
    void classifyCuisineMediterraneanTypeTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Italian Seafood Stew")
                .param("language", "en")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasEntry("cuisine", "Mediterranean"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("mediterranean"));
        assertThat(response.get("cuisines"), hasItem("Italian"));
        assertThat(response.get("confidence"), not(equalTo(0f)));

    }


    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (African)")
    void classifyCuisineAfricanTypeTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "African Bean Soup")
                .param("ingredientList", "")
                .param("language", "en")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasKey("confidence"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("african"));
        assertThat(response.get("cuisines"), hasItem("African"));
        assertThat(response.get("confidence"), equalTo(0.85F));
        assertThat(response.get("cuisines") instanceof ArrayList, is(true));
    }


    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (Korean)")
    void classifyCuisineKoreanTypeTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Winter Kimchi")
                .param("language", "en")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasKey("confidence"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("korean"));
        assertThat(response.get("cuisines"), hasItem("Korean"));
        assertThat(response.get("confidence"), equalTo(0.85F));
        assertThat(response.get("cuisines") instanceof ArrayList, is(true));
    }


    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (Seafood)")
    void classifyCuisineSeafoodNewBurgTest() throws IOException {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Seafood Newburg")
                .param("ingredientList", "¼ lb Scallops, ⅓ lb Shrimp, ½ lb white cod")
                .param("language", "en")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasEntry("cuisine", "Mediterranean"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("mediterranean"));
        assertThat(response.get("cuisines"), hasItem("Italian"));
        assertThat(response.get("confidence"), equalTo(0f));
    }
}
