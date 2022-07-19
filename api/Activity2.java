package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {

    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 0)
    public void addNewUser() throws IOException {

        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/userInfo.json");

        String reqBody = new String(inputJSON.readAllBytes());
        Response response = given().contentType(ContentType.JSON)
                .body(reqBody)
                .when()
                .post(baseURI);

        inputJSON.close();

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("3456"));
    }
    @Test(priority = 1)
    public void getUerDetails() {
        //baseURI = "https://petstore.swagger.io/v2/pet";

        File outputJSON = new File("src/test/java/activities/userGetResponse.json");

        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("username","DeepaP")
                .get(baseURI+"/{username}");

        String resBody = response.getBody().asPrettyString();
        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

        response.then().body("id", equalTo(3456));
        response.then().body("username", equalTo("DeepaP"));
        response.then().body("firstName", equalTo("Deepa"));
        response.then().body("lastName", equalTo("Patil"));
        response.then().body("email", equalTo("deepapatil@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("1234567890"));
    }
    @Test(priority = 2)
    public void deletePetDetails(){
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("username","DeepaP")
                .delete(baseURI+"/{username}");

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("DeepaP"));
    }
}
