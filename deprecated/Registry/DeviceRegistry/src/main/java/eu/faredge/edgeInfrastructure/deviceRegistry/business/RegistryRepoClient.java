package eu.faredge.edgeInfrastructure.deviceRegistry.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class RegistryRepoClient implements RegistryRepoInterface
{

	private Client client;
	private static Properties props = new Properties();

	public RegistryRepoClient()
	{
		// TODO change try-catch with throws exception
 //		DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
//		defaultClientConfig.getClasses().add(JacksonJsonProvider.class);
//		client = Client.create(defaultClientConfig);
		client = Client.create();
		try
		{
			props.load(RegistryRepoClient.class.getClassLoader().getResourceAsStream("RegistryRepoClient.properties"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ClientResponse getResource(String uri, String id)
	{
		ClientResponse response;// = webResource.type("application/json").get(ClientResponse.class);
		try
		{
			// create client resource for REST call
			System.out.println("RegistryRepoClient:getResource:uri=" + uri);
			System.out.println("RegistryRepoClient:getResource:id=" + id);
			WebResource webResource = client.resource(uri).queryParam("id",id );
			
			// String request = uri;// mapper.writeValueAsString(postData);

			// call resource and get Results
			response = webResource.type("application/json").get(ClientResponse.class);

			if (response.getStatus() != 201)
			{
				// throw new RuntimeException("Failed : HTTP error code : " +
				// response.getStatus());
			}

			//String result = response.getEntity(String.class);

			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}

		}

		return response;
	}
	
	@Override
	public ClientResponse getResource(String uri)
	{
		ClientResponse response;// = webResource.type("application/json").get(ClientResponse.class);
		try
		{
			
			// create client resource for REST call
			WebResource webResource = client.resource(uri);

			// call resource and get Results
			System.out.println("RegistryRepoClient:getResource:uri=" + uri);
			response = webResource.type("application/json").get(ClientResponse.class);
			

//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//			}

			//String result = response.getEntity(String.class);
			//System.out.println("RegistryRepoClient:getResource:result="+ result); 
			

			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}

		}

		return response;
	}
	
	@Override
	public ClientResponse getResource(ArrayList<String> uriList, String uri)
	{
		ClientResponse response;// = webResource.type("application/json").get(ClientResponse.class);
		try
		{
			// serialize 'uriList' Object to String
			String tmp="";
			for (int i=0;i<uriList.size();i++)
			{
				tmp +=uriList.get(i) + System.lineSeparator();
			}
			String strRequest = tmp.substring(0, tmp.length()-1);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			
			@SuppressWarnings("unused")
			String request = mapper.writeValueAsString(uriList);
			
			System.out.println("RegistryRepoClient:getResource(List<String>, string) request data=" + strRequest);

			
			// create client resource for REST call
			WebResource webResource = client.resource(uri).queryParam("uris", strRequest);

			// call resource and get Results
			System.out.println("RegistryRepoClient:getResource:uri=" + uri);
			response = webResource.type("application/json").get(ClientResponse.class);
			
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//			}

			//String result = response.getEntity(String.class);
			//System.out.println("RegistryRepoClient:getResource:result="+ result); 

			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}

		}

		return response;
	}

	@Override
	public <T> ClientResponse postResource(String uri, T postData)
	{
		ClientResponse response;
		System.out.println("RegistryRepoClient:postResource==> starting");
		try
		{

			// create client resource for REST call
			WebResource webResource = client.resource(uri);
			
			

			// serialize 'postData' Object to String
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String request = mapper.writeValueAsString(postData);
			System.out.println("RegistryRepoClient:postResource request data=" + request);

			// call resource and get Results
			response = webResource.type("application/json").post(ClientResponse.class, request);

			if (response.getStatus() != 201)
			{
				// client.destroy();
				// throw new RuntimeException("Failed : HTTP error code : " +
				// response.getStatus());
			}
			System.out.println("RegistryRepoClient:postResource==> success response = " + response.getStatus());
			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}
		}
		System.out.println("RegistryRepoClient:postResource==>fail response=" + response.getStatus());

		return response;
	}

	@Override
	public ClientResponse deleteResource(String uri, String id)
	{
		ClientResponse response;// = webResource.type("application/json").get(ClientResponse.class);
		try
		{
			// create client resource for REST call
			WebResource webResource = client.resource(uri).queryParam("uri", id);
			//webResource.path(id);
			// String request = uri;// mapper.writeValueAsString(postData);

			// call resource and get Results
			response = webResource.type("application/json").delete(ClientResponse.class);

			if (response.getStatus() != 201)
			{
				// throw new RuntimeException("Failed : HTTP error code : " +
				// response.getStatus());
			}

//			String result = response.getEntity(String.class);

			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}

		}

		return response;
	}

	@Override
	public <T> ClientResponse putResource(String uri,String id, T putData)
	{
		ClientResponse response;
		try
		{

			// create client resource for REST call
			WebResource webResource = client.resource(uri);
			webResource.queryParam("id", id);

			// serialize 'postData' Object to String
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String request = mapper.writeValueAsString(putData);

			// call resource and get Results
			response = webResource.type("application/json").put(ClientResponse.class, request);

			if (response.getStatus() != 201)
			{
				// client.destroy();
				// throw new RuntimeException("Failed : HTTP error code : " +
				// response.getStatus());
			}

			// destroy
			// client.destroy();

			return response;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String excString = e.toString();
			try
			{
				InputStream stream = new ByteArrayInputStream(excString.getBytes(StandardCharsets.UTF_8.name()));
				response = new ClientResponse(0, null, stream, null);
			}
			catch (UnsupportedEncodingException e1)
			{
				response = new ClientResponse(-1, null, null, null);
				e1.printStackTrace();
			}
		}
		return response;
	}

}
