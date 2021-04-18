package com.qa.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.ptg.LessThanPtg;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers;


public class TC001_AddPets_POST extends TestBase{
  
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String requestJSON;
	
	@BeforeMethod
	public void setUp(){
		testBase=new TestBase();
		serviceURL=prop.getProperty("serviceURL");
		apiURL= prop.getProperty("addPet_apiURL");
	}
	
	
	//dataProvider= "PetDataProvider"
	//String Id, String name, String status
	
	@Test(dataProvider= "PetDataProvider")
	public void addPet(String Id, String name, String status){
		baseURI = serviceURL;
		
		requestJSON= "{\"id\":"+Id+",\"category\":{\"id\":0,\"name\":\"string\"},"
				+ "\"name\":\""+name+"\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],"
				+ "\"status\":\""+status+"\"}";
		
		RequestSpecification httpRequest = given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.contentType(ContentType.JSON);
		httpRequest.accept(ContentType.JSON);
		httpRequest.body(requestJSON);
		
		Response response= httpRequest.request(Method.POST, apiURL);
		ValidatableResponse valResponse= response.then();
		
		valResponse.statusCode(200);
		valResponse.time(Matchers.lessThan(20L), TimeUnit.SECONDS);
		
		System.out.println("Response Body -->" +response.getBody().asPrettyString());
	}
  
	@DataProvider(name= "PetDataProvider")
	String [][] getPetData(){
		String petData[][]= {{"100105", "Lab", "available"}, {"100107", "Nan", "pending"}, {"100106", "Alc", "sold"}};
		
		return petData;
	}
}
