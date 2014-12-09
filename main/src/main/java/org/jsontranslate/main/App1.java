package org.jsontranslate.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.google.gson.Gson;

public class App1 {

	public static void main(String[] args) {
		 
		try {
	 
			BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Marti\\DXAT\\main\\prop.json"));
	 
			//convert the json string back to object
			String xxx="{\"name\": {\"value\": \"s3-eth3\"}}";
			System.out.println(xxx);
			Gson gson = new Gson();
			Name obj = gson.fromJson(xxx, Name.class);
	 
			System.out.println(obj);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
