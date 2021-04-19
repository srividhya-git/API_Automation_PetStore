package com.qa.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.generic.AutomationConstatnt;
import com.qa.generic.XLUtils;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC002_FindPet_Status_GET extends TestBase {
  
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String requestJSON;
	String apiURL_without_qryParam;
	String qryParam;
	
	@BeforeMethod
	public void setUp(){
		testBase=new TestBase();
		serviceURL=prop.getProperty("serviceURL");
		apiURL_without_qryParam= prop.getProperty("findPetsbyStatus_apiURL");
	}
	
	@Test(dataProvider = "PetDataProvider")
	public void findPet_withStatus(String expectedPetName, String status){
		baseURI = serviceURL;
		
		qryParam=status;
		apiURL=apiURL_without_qryParam+ qryParam;
		
		
		RequestSpecification httpRequest = given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.accept(ContentType.JSON);
		
		
		Response response= httpRequest.request(Method.GET, apiURL);
		ValidatableResponse valResponse= response.then();
		
		System.out.println("Response Body -->" +response.getBody().asPrettyString());
		response.then().body("name", hasItem(expectedPetName));
	}
  
	@DataProvider(name= "PetDataProvider")
	String [][] getPetData() throws IOException{
		
		int rownum= XLUtils.getRowCount(AutomationConstatnt.DataFilePath, "FindPet");
		int colnum= XLUtils.getCellCount(AutomationConstatnt.DataFilePath, "FindPet", 1);
		System.out.println("rn -->"+rownum);
		System.out.println(("clnum-->"+colnum));
		String petData[][]= new String[rownum][colnum];
		
		for(int i=1; i < rownum; i++){
			for (int j=0; j<colnum; j++){
				petData[i-1][j]=XLUtils.getCellData(AutomationConstatnt.DataFilePath, "FindPet", i, j);
				System.out.println(XLUtils.getCellData(AutomationConstatnt.DataFilePath, "FindPet", i, j));
			}
		}
		
		
		return petData;
	}
	
}
