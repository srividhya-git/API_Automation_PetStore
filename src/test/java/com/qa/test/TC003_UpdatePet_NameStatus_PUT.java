package com.qa.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

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

public class TC003_UpdatePet_NameStatus_PUT extends TestBase{
  
	
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String requestJSON;
	
	@BeforeMethod
	public void setUp(){
		testBase=new TestBase();
		serviceURL=prop.getProperty("serviceURL");
		apiURL= prop.getProperty("updatePet_apiURL");
	}
	
	
	//dataProvider= "PetDataProvider"
	//String Id, String name, String status
	
	@Test(dataProvider= "PetDataProvider")
	public void updatePet(String Id, String name, String status){
		baseURI = serviceURL;
		
		requestJSON= "{\"id\":"+Id+",\"category\":{\"id\":0,\"name\":\"string\"},"
				+ "\"name\":\""+name+"\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],"
				+ "\"status\":\""+status+"\"}";
		
		RequestSpecification httpRequest = given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.contentType(ContentType.JSON);
		httpRequest.accept(ContentType.JSON);
		httpRequest.body(requestJSON);
		
		Response response= httpRequest.request(Method.PUT, apiURL);
		ValidatableResponse valResponse= response.then();
		
		System.out.println("Response Body -->" +response.getBody().asPrettyString());
		
		if(response.getStatusCode()==200){
			System.out.println("Pet Updated");
		}else if (response.getStatusCode()==400) {
			System.out.println("Status Code --> "+ response.getStatusCode()+" --> Invalid ID supplied");
		}else if (response.getStatusCode()==404) {
			System.out.println("Status Code --> "+ response.getStatusCode()+" --> Pet Not Found");
		}else if (response.getStatusCode()==405) {
			System.out.println("Status Code --> "+ response.getStatusCode()+" --> Validation exception");
		}
			
	}
  
	@DataProvider(name= "PetDataProvider")
	String [][] getPetData() throws IOException{
		
		int rownum= XLUtils.getRowCount(AutomationConstatnt.DataFilePath, "UpdatePet");
		int colnum= XLUtils.getCellCount(AutomationConstatnt.DataFilePath, "UpdatePet", 1);
		System.out.println("rn -->"+rownum);
		System.out.println(("clnum-->"+colnum));
		String petData[][]= new String[rownum][colnum];
		
		for(int i=1; i < rownum; i++){
			for (int j=0; j<colnum; j++){
				petData[i-1][j]=XLUtils.getCellData(AutomationConstatnt.DataFilePath, "UpdatePet", i, j);
			}
		}
		return petData;
	}
	
	
}
