package com.qa.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.generic.AutomationConstatnt;
import com.qa.generic.XLUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TC004_UploadPetImage_POST extends TestBase{
 
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String requestJSON;
	String apiURL_without_qryParam;
	
	@BeforeMethod
	public void setUp(){
		testBase=new TestBase();
		serviceURL=prop.getProperty("serviceURL");
		apiURL_without_qryParam=prop.getProperty("uploadPetImage_apiURL");
	}
	
	@Test(dataProvider="PetDataProvider")
	public void uploadPetImage(String petId, String imageTitle){
		baseURI = serviceURL;
		apiURL="/" + petId + apiURL_without_qryParam;
		
		File file= new File(AutomationConstatnt.FileUploadFolderPath+ imageTitle + ".png");
		

		RequestSpecification httpRequest = given();
		httpRequest.multiPart("file", file, "multipart/form-data");
		
		Response response= httpRequest.request(Method.POST, apiURL);
		ValidatableResponse valResponse= response.then();
		
		valResponse.statusCode(200);
		
	}
  
	@DataProvider(name= "PetDataProvider")
	String [][] getPetData() throws IOException{
		
		int rownum= XLUtils.getRowCount(AutomationConstatnt.DataFilePath, "ImageUpload");
		int colnum= XLUtils.getCellCount(AutomationConstatnt.DataFilePath, "ImageUpload", 1);
		System.out.println("rn -->"+rownum);
		System.out.println(("clnum-->"+colnum));
		String petData[][]= new String[rownum][colnum];
		
		for(int i=1; i < rownum; i++){
			for (int j=0; j<colnum; j++){
				petData[i-1][j]=XLUtils.getCellData(AutomationConstatnt.DataFilePath, "ImageUpload", i, j);
			}
		}
		
		return petData;
	}
}
