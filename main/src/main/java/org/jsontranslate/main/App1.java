package org.jsontranslate.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsontranslate.topology.EdgePropertiesContainer;
import org.jsontranslate.topology.TopologyClass;
import org.jsontranslate.topology.TopologyEdge;
import org.jsontranslate.topology.TopologyNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App1 {

	public static void main(String[] args) {
		 
		try {
	 
			BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Marti\\git\\JsonTranslate\\main\\fromcontroller.json"));
	 			
			System.out.println(br.toString());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			EdgePropertiesContainer edgepropertiescontainer = gson.fromJson(br, EdgePropertiesContainer.class);
	 
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
			
			
			System.out.println(json);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
