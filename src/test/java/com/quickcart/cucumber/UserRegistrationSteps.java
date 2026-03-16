package com.quickcart.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.boot.test.web.server.LocalServerPort;

public class UserRegistrationSteps {

    @LocalServerPort
    private int port;

    private RequestSpecification request;
    private Response response;

    @Given("the user provides a valid name {string}, email {string}, and password {string}")
    public void setupValidUserPayload(String name, String email, String password) {
        String jsonPayload = String.format(
                "{ \"name\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                name, email, password
        );

        request = RestAssured.given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(jsonPayload);
    }

    @When("the user sends a POST request to register")
    public void sendPostRequest() {
        response = request.post("/api/users/register");
    }

    @Then("the API should respond with status code {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response body should contain the email {string}")
    public void verifyEmailInResponse(String expectedEmail) {
        response.then().body("email", equalTo(expectedEmail));
    }
}

