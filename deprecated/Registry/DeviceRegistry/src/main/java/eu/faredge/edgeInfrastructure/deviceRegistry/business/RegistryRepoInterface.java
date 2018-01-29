package eu.faredge.edgeInfrastructure.deviceRegistry.business;

import java.util.ArrayList;

import com.sun.jersey.api.client.ClientResponse;

public interface RegistryRepoInterface
{
	public ClientResponse  getResource(String uri);
	public ClientResponse  getResource(ArrayList<String> uriList,String uri);
	public ClientResponse  getResource(String uri,String id);
	public ClientResponse  deleteResource(String uri, String id);
	public  <T> ClientResponse postResource (String uri, T postData);	 
	public  <T> ClientResponse putResource (String uri,String id, T putData);	
}
