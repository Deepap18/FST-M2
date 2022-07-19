package activities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Activity1 {
    String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 0)
    public void addPetDetails(){
        String reqBody = "{"
                + "\"id\": 77232,"
                + "\"name\": \"Riley\","
                + " \"status\": \"alive\""
                + "}";

        Response response = given().contentType(ContentType.JSON)
                            .body(reqBody)
                            .when()
                            .post(baseURI);

        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority = 1)
    public void getPetDetails() {
        //baseURI = "https://petstore.swagger.io/v2/pet";

        Response response = given().contentType(ContentType.JSON)
                            .when().pathParam("PetID","77232")
                            .get(baseURI+"/{PetID}");

        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }
    @Test(priority = 2)
    public void deletePetDetails(){
        Response response = given().contentType(ContentType.JSON)
                            .when().pathParam("PetID","77232")
                            .delete(baseURI+"/{PetID}");

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }
}
