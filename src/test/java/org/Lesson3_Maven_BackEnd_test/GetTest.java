package org.Lesson3_Maven_BackEnd_test;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest extends Initializalization{


    @Test
    void getRecipeBurgerNegativeTest() {
        given()
                .log()
                .all()
                .queryParam("query", "burger")
                .expect()
                .body("status", equalTo("failure"))
                .body("code", equalTo(401))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @Test
    void getRecipePastaPositiveTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .expect()
                .body("results[0].title", containsString("Pasta"))
                .body("results[0].title", containsString("Tuna"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getRecipeSaladPositiveTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "salad")
                .queryParam("sort","calories")
                .expect()
                .body("results[0].id", equalTo(655447))
                .body("results[0].nutrition.nutrients[0].amount", equalTo(1029.68F))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getRecipeSteakPositiveTest() {
        JsonPath response = given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "steak")
                .queryParam("sort","calories")
                .queryParam("sortDirection","desc")
                .queryParam("cuisine","italian")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("results[0].id"), equalTo(649077));
        assertThat(response.get("results[0].title"), containsString("Steak"));
        assertThat(response.get("results[0].nutrition.nutrients[0].name"), equalTo("Calories"));
        assertThat(response.get("results[0].nutrition.nutrients[0].amount"), equalTo(1111.31F));
        assertThat(response.get("results[0].nutrition.nutrients[0].unit"), equalTo("kcal"));
    }

    @Test
    void getRecipeSoupPositiveTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "soup")
                .queryParam("includeIngredients", "tomato, cheese")
                .expect()
                .body("results[1].title", containsString("Soup"))
                .body("results[1].title", containsString("Veggie"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
