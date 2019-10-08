package com.qa.utils;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qa.data.Users;
import com.qa.data.Users;

public class TestUtils {

	public static String getValueByJPath(JSONObject responsejson, String jpath) {
		Object obj = responsejson;
		for (String s : jpath.split("/"))
			if (!s.isEmpty())
				if (!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if (s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
							.get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	
	//to generate hashmap of headers
	public HashMap<String, String> generteHeaderMap(String key, String value) {
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put(key, value);
		return headerMap;
	}
	
	// get status code
	public Integer getStatusCode(HttpResponse response) {
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode;
	}
	
	// verify json response
	
	public Object verifyJsonResponse(HttpResponse response, ObjectMapper mapper) throws ParseException, IOException {
		// 1. first generate json response in string format
		Users objectClass = null;
		try {
			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			objectClass = mapper.readValue(responseString, Users.class);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return objectClass;
		
	}
}
