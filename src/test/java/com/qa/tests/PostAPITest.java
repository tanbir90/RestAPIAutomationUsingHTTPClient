package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.utils.TestUtils;

public class PostAPITest extends TestBase {
	TestUtils testUtils;
	HttpResponse closeablehttpResponse;
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	RestClient restClient;
	String url;
	HashMap<String, String> headerMap;
	ObjectMapper mapper;
	String DATA_PATH = "C:\\Users\\tanbir90\\eclipse-workspace\\RestAPIAutomationUsingHTTPClient\\src\\main\\java\\com\\qa\\data";

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		testUtils = new TestUtils();
		mapper = new ObjectMapper();
		restClient = new RestClient();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;
	}
	
	@Test
	public void postTest() throws ClientProtocolException, IOException {
		
		Users user = new Users("Tanbir","Client");
		
		headerMap = testUtils.generteHeaderMap("Content-Type", "application/json");
		
		// Java object Object to json file
		mapper.writeValue(new File(DATA_PATH+"\\Users.json"), user);
		
		String jsonString = mapper.writeValueAsString(user);// {"name":"morpheus","job":"leader","id":null,"createdAt":null}
		System.out.println(jsonString);
		
		
		closeablehttpResponse = restClient.post(url,jsonString, headerMap);
		
		//verify status code
		int statusCode = testUtils.getStatusCode(closeablehttpResponse);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "status code did not match :-->");
		
		
		//Json String
		// we need to convert json  to java object
		// 1. first generate json response in string format
		String responseString = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8"); //{"name":"morpheus","job":"leader","id":"880","createdAt":"2019-10-08T10:31:58.481Z"}
		// 2. convert json to java object  -->unmarshaling
		Users usersObj = mapper.readValue(responseString, Users.class);
		
		
		Assert.assertEquals(user.getName(), usersObj.getName());
		// post call done
	}

}
