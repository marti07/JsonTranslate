package org.jsontranslate.topology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Clienthttp{

	private String ws = "http://192.168.235.131:8080/controller/nb/v2/topology/default";
	
	HttpResponse response;
	DefaultHttpClient httpClient;
	HttpGet getRequest;
	String result="";
	HttpPost postRequest;
	HttpDelete delRequest;
	HttpDeleteWithBody httpDeleteWithBody;
	HttpGetWithBody httpGetWithBody;
	HttpPut putRequest;

	
	public Clienthttp(){
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection=0;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		int timeOutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeOutSocket);
		httpClient = new DefaultHttpClient(httpParameters);
		System.out.println("Conexion preparada rr");
		System.out.println("Request a: "+ws+"myresource");
	}
	

	public String httpGet(String objetoEnJson) throws ClientProtocolException, IOException{
		System.out.println("Estoy en el método httpGet()");
		httpGetWithBody = new HttpGetWithBody(ws);
		StringEntity entity = new StringEntity(objetoEnJson);
		httpGetWithBody.setHeader("content-type","application/json");
		httpGetWithBody.setEntity(entity);
		response = httpClient.execute(httpGetWithBody);
		result = getResult(response).toString();

		System.out.println(response.getStatusLine());
				
		getRequest= new HttpGet(ws+"myresource");
		getRequest.setHeader("content-type","application/json");
		response = httpClient.execute(getRequest);
		entity = new StringEntity(objetoEnJson);
		result= getResult(response).toString();
		return result;
	}
	
	public StatusLine httpPost(String objetoEnJson) throws ClientProtocolException, IOException{
		System.out.println("Estoy en el método httpPost()");
		postRequest = new HttpPost(ws);
		postRequest.setHeader("content-type","application/json");
		StringEntity entity = new StringEntity("admin:admin");
		postRequest.setEntity(entity);
		response = httpClient.execute(postRequest);
		return response.getStatusLine();
	}

//	public StatusLine httpDelete(String objetoEnJson) throws ClientProtocolException, IOException{
//		System.out.println("Estoy en el método Delete()");
//		httpDeleteWithBody = new HttpDeleteWithBody(ws+"myresource");
//		StringEntity entity = new StringEntity(objetoEnJson);
//		httpDeleteWithBody.setHeader("content-type","application/json");
//		httpDeleteWithBody.setEntity(entity);
//		response = httpClient.execute(httpDeleteWithBody);
//		return response.getStatusLine();
//		
//	}
	
	public StatusLine httpPut(String objetoEnJsonViejo, String objetoEnJsonNuevo) throws ClientProtocolException, IOException{
		System.out.println("Estoy en el método httpPut()");
		
		putRequest = new HttpPut(ws+"myresource");
		putRequest.addHeader("content-type","application/json");
		List <String> parObjetos = new ArrayList ();
		parObjetos.add(objetoEnJsonViejo);
		parObjetos.add(objetoEnJsonNuevo);
//		System.out.println("Se envia: "+parObjetos.get(0)+" y "+parObjetos.get(1));
		String listEnJson = new Gson().toJson(parObjetos);
		System.out.println("listenJson= "+listEnJson);
		StringEntity entity = new StringEntity(listEnJson);
		putRequest.setEntity(entity);
		response = httpClient.execute(putRequest);
		System.out.println("se ha realizado el putrequest");
		return response.getStatusLine();
		
	}


	private StringBuilder getResult(HttpResponse response) throws IllegalStateException, IOException{
		StringBuilder result = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())),1024);
		String output;
		while ((output=br.readLine()) != null){
			result.append(output);
		}
		
		
		return result;
		
	}
}