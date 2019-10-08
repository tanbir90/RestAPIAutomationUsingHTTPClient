package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.utils.TestUtils;

public class GetAPITest extends TestBase {
	HttpResponse closeablehttpResponse;
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	RestClient restClient;
	String url;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;
	}

	@Test
	public void getTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeablehttpResponse = restClient.get(url);

		// a. status code
		int statusCode = closeablehttpResponse.getStatusLine().getStatusCode();
		System.out.println("status cod is -->" + statusCode);
		// b.JSON String
		String responseString = EntityUtils.toString(closeablehttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("response is -> " + responseJson);
		
		String perPageValue = TestUtils.getValueByJPath(responseJson, "/per_page");
		System.out.println("per page value : "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6, "per_page value did not match :--> ");
		
		//validate JSON Array
		String lastName = TestUtils.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtils.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtils.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtils.getValueByJPath(responseJson, "/data[0]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
		
		

		// c. All Headers
		Header[] heardersArrey = closeablehttpResponse.getAllHeaders();

		// 3. get all headers
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : heardersArrey) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("allheaders --> " + allHeaders);

	}

}
