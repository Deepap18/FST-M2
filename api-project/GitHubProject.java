package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GitHubProject {
    //Headers
    RequestSpecification requestspec;
    String sshkey;
    int id;
    String userResourcePath = "/user/keys";
    String tokenID = "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";
    @BeforeClass
    public void setUp(){
        requestspec = new RequestSpecBuilder()
                .setAuth(oauth2("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"))
                .setContentType(ContentType.JSON)
         //       .addHeader("Authorization",tokenID)
                .setBaseUri("https://api.github.com")
                .build();

    }
    @Test(priority = 0)
    public void addSSHKey(){
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCbCLzzhQLINUMd/q6MftrDglUQOoLaCRbv4qtIz1/eyCyzKlU5fqJDCkaVunzdMzZvDNJIGTPOVSh6VXiTizDpueXW5HKGfS/G4EDluJORWzPpJPq/viVOWP5YJ1qCY25TSK/270XFtas5tp6ryz80qgyMmfpH0BNtyFeNF4rYb0KpFrvdrGRNYb6/DtPAeNf8Od4Gw95+mXloVmskiDqENPS/CLghzCS/27eE5ZrOVIkDbqjclaOhCbpwAI02DdB4yyfl1i9UphQz6YDxk07ErqZcxCQ9pvUkzMpO9GJiUsiaWOrD8dIG8YF/uvF7dLh82Y43NKScKYurlsI66kNp");
        //generate response
        Response response = given().spec(requestspec).when().body(reqBody).post(userResourcePath);
        id = response.then().extract().path("id");
        //System.out.println(response.asPrettyString());
        System.out.println(id);
        //Assertions
        response.then().statusCode(201);

    }
    @Test(priority = 1)
    public void getSSHKey(){
        Response response = given().spec(requestspec).pathParam("Keyid",id).when().get(userResourcePath+"/{Keyid}");
        //System.out.println(response.asPrettyString());
        //Assertions
        response.then().statusCode(200);
    }
    @Test(priority = 2)
    public void deleteSSHKey(){
        Response response = given().spec(requestspec).pathParam("Keyid",id).when().delete(userResourcePath+"/{Keyid}");
        //System.out.println(response.asPrettyString());
        //Assertions
        response.then().statusCode(204);
    }


}
