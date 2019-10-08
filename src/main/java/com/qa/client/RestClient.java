package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	//1. Get Method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);  //http get request
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httpget);
		return closeablehttpResponse;
	}
	
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {  //entityString is jasonpayload
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new StringEntity(entityString)); // for creating new entity using json payload
		
		
		//for headers:
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closeablehttpResponse = httpClient.execute(httppost);
		return closeablehttpResponse;
		
	}

}
