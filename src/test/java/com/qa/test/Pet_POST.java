package com.qa.test;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class Pet_POST {
	public String baseURL;

	@BeforeMethod
	public void setUP() {
		baseURL = "https://petstore.swagger.io/v2";
	}

	@Test(enabled=false)
	public void testGET() {
		baseURI = baseURL;

		RequestSpecification httpRequest = given(); //STatic Import
		

		Response response = httpRequest.request(Method.GET, "/pet/findByStatus?status=sold");

		System.out.println("Response Body is =>  " + response.getBody().asString());
		System.out.println("Response Time =>  " + response.getTime());
		System.out.println("Response StatusCode =>  " + response.getStatusCode());
		
		response.then().body("name", hasItem("Parrot"));
		
	}
	
	@Test(enabled=false)
	public void a(){
		baseURI = baseURL;
		given().when().
			get("/pet/findByStatus?status=sold").
		then().
			statusCode(200);
	}
	
	@Test(enabled= true)
	public void testPOST(){
		baseURI = baseURL;
		
		/*JSONObject request= new JSONObject();
		request.put("id", "100102");
		request.put("category.id", "0");
		request.put("category.name", "string");
		request.put("name", "Carrot");
		request.put("status	", "available");*/
		String request= "{\"id\":100103,\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"sekh\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(request).
		when().
			post("/pet").
		then().
			statusCode(200).
			log().all();
	}
	
	
}
