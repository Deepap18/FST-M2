package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> headers= new HashMap<>();
    //API Path
    String userResourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider="UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        //Set headers
        headers.put("Content-Type","application/json");

        //Set body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");
        //Create Pact
        return builder.given("A request to create user")
                .uponReceiving("A request to create user")
                    .method("POST")
                    .headers(headers)
                    .path(userResourcePath)
                    .body(requestResponseBody)
                .willRespondWith()
                    .status(201)
                    .body(requestResponseBody)
                .toPact();


    }
    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerSideTest(){
        //Set BaseURI
        final String baseURI = "http://localhost:8282";

        //Create request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id",100);
        reqBody.put("firstName","Deepa");
        reqBody.put("lastName","Patil");
        reqBody.put("email","deepap@example.com");

        //Generate response
        Response response = given().headers(headers).when().body(reqBody).post(baseURI + userResourcePath);

        //Assertion
        response.then().statusCode(201);
    }
}
