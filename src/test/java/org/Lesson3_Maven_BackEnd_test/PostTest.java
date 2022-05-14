package org.Lesson3_Maven_BackEnd_test;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostTest extends Initializalization{


    @Test
    void classifyCuisineWithoutQueryParametersTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .assertThat().body("cuisine", equalTo("Mediterranean"))
                .assertThat().body("confidence", equalTo(0.0F))
                .statusCode(200);
    }

    @Test
    void classifyCuisineMediterraneanTypeTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Italian Seafood Stew")
                .param("language", "en")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .assertThat().body("cuisine", equalTo("Mediterranean"))
                .assertThat().body("confidence", equalTo(0.95F))
                .statusCode(200);
    }

    @Test
    void classifyCuisineAfricanTypeTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "African Bean Soup")
                .param("ingredientList", "")
                .param("language", "en")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .assertThat().body("cuisine", equalTo("African"))
                .assertThat().body("confidence", equalTo(0.85F))
                .statusCode(200);
    }

    @Test
    void classifyCuisineKoreanTypeTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Winter Kimchi")
                .param("language", "en")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .assertThat().body("cuisine", equalTo("Korean"))
                .assertThat().body("confidence", equalTo(0.85F))
                .statusCode(200);
    }

    @Test
    void classifyCuisineSeafoodNewburgTest() {
        given()
                .log()
                .all()
                .queryParam("apiKey", getApiKey())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .param("title", "Seafood Newburg")
                .param("ingredientList", "¼ lb Scallops, ⅓ lb Shrimp, ½ lb white cod")
                .param("language", "en")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .assertThat().body("cuisine", equalTo("Mediterranean"))
                .statusCode(200);
    }


}
