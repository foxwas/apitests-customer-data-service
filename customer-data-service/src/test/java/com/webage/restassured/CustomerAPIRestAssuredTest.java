package com.webage.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

public class CustomerAPIRestAssuredTest {
	
	
	@Test
    public void testGetAPIRequest() {
        given()
            .when()
                .get("http://localhost:9000/api")
            .then()
                .statusCode(200);
    }
	
	@Test
    public void testGetAllCustomersRequest() {
        given()
            .when()
                .get("http://localhost:9000/api/customers")
            .then()
                .statusCode(200);
        
    }
	
	@Test
    public void testPostRequest() {
        given()
            .contentType("application/json")
            .body("{\r\n"
            		+ "        \"id\": 0,\r\n"
            		+ "        \"name\": \"fox\",\r\n"
            		+ "        \"password\": \"pass123\",\r\n"
            		+ "        \"email\": \"fox@webage.com"
            		+ "\"\r\n"
            		+ "}")
            
        .when()
            .post("http://localhost:9000/api/customers")
        .then()
            .statusCode(201)
            .header("Location", notNullValue());    
    }
	
	
	@Test
    public void testPutRequest() {
        given()
            .contentType("application/json")
            .body("{\r\n"
            		+ "        \"id\": 1,\r\n"
            		+ "        \"name\": \"steve\",\r\n"		
            		+ "        \"password\": \"steve123\",\r\n"		//password update
            		+ "        \"email\": \"steve@webage.com"		//email update
            		+ "\"\r\n"
            		+ "}")
            
        .when()
            .put("http://localhost:9000/api/customers/1")
        .then()
            .statusCode(200);    
    }
	
	@Test
    public void testStatusCodeHandling() {
        given()
            .when()
                .get("http://localhost:9000/api/customers/1")
            .then()
                .statusCode(
                    anyOf(
                        is(200), // Success
                        is(404), // Not Found
                        is(500)  // Server Error
                    )
                );
    }
	
	@Test
    public void testRetrieveResponseBody() {
        Response response =
            given()
            .when()
                .get("http://localhost:9000/api/customers/1")
            .then()
                .statusCode(200)
            .extract()
                .response();
        
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
    }
	
	@Test
    public void testDeleteRequest() {
            given()
            .when()
                .delete("http://localhost:9000/api/customers/2")
            .then()
                .statusCode(204);
            
    }
	
	@Test
    public void testEvaluateJSONContent() {
        given()
            .when()
                .get("http://localhost:9000/api/customers/1")
            .then()
                .statusCode(200)
                .body("name", equalTo("steve"))
                .body("email", equalTo("steve@webage.com"));
    }
}
