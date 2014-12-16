package org.jsontranslate.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.jsontranslate.topology.EdgePropertiesContainer;
import org.jsontranslate.topology.TopologyClass;
import org.jsontranslate.topology.TopologyEdge;
import org.jsontranslate.topology.TopologyNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App2 {

	public static void main(String[] args) {

		try {
			String webPage = "http://192.168.235.133:8080/controller/nb/v2/topology/default";
			String name = "admin";
			String password = "admin";

			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			System.out.println(result);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			EdgePropertiesContainer edgepropertiescontainer = gson.fromJson(result, EdgePropertiesContainer.class);
			 
			TopologyClass topo = new TopologyClass();
			ArrayList<TopologyNode> nodes = new ArrayList<TopologyNode>();
			ArrayList<TopologyEdge> edges = new ArrayList<TopologyEdge>();
			
			//Fill Nodes
			
			for(int i=0; i < edgepropertiescontainer.edgeProperties.size(); i++)
			{
				TopologyNode node = new TopologyNode();
				
				if(!nodes.contains(edgepropertiescontainer.edgeProperties.get(i).edge.headNodeConnector.id))
				{
					node.id = edgepropertiescontainer.edgeProperties.get(i).edge.headNodeConnector.id;
					node.label = node.id;
					node.x=0;
					node.y=0;
					node.size=0;				
					nodes.add(node);
				}
			}
			
			topo.nodes = nodes;
			int i=0;
			for( i=0; i < edgepropertiescontainer.edgeProperties.size(); i++)
			{
				TopologyEdge edge = new TopologyEdge();
				edge.id = String.valueOf(i);
				edge.source = edgepropertiescontainer.edgeProperties.get(i).edge.headNodeConnector.id;
				edge.target = edgepropertiescontainer.edgeProperties.get(i).edge.tailNodeConnector.id;				
				edges.add(edge);
			}
			
			topo.edges=edges;
			
			String json = gson.toJson(topo);
			
			FileWriter fichero = new FileWriter("C:\\Users\\Marti\\git\\JsonTranslate\\main\\output.json");

			fichero.write(json);

			fichero.close();
			
			FileWriter fichero2 = new FileWriter("C:\\Users\\Marti\\git\\JsonTranslate\\main\\topologycontroller.json");

			fichero2.write(result);

			fichero2.close();
			System.out.println("end");
			
    	}
    	catch(IOException e)
    	{
    		System.out.println("Catch");
    	}
	}
}
