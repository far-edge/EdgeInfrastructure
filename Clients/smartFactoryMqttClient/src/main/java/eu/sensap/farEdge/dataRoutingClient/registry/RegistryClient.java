/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.registry;


import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;
import eu.sensap.farEdge.dataRoutingClient.interfaces.DeviceRegisterInterface;
import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;

/***
 * This class supports the basic registry operations 
 * There are three operations:
 * 	1. Registers a device with a specific UUID, Credentials, and Configuration Environments
 *  2. Unregisters e registered device
 *  3. Asks if a device is registered   
  */
public class RegistryClient implements DeviceRegisterInterface
{
	private Credentials credentials;			//Credentials for registry connection
	private ConfigurationEnv configurationEnv;	//configuration environmental values for registry and message bus connection
	private DataSourceManifest dsm;				//Data source Manifest for the device
	private String uuid;						// device uuid

	public RegistryClient(Credentials credentials, String uuid) 
	{
		this.setCredentials(credentials);
		this.setUuid(uuid);
	}
	
	
	
	public RegistryClient() 
	{
		
	}


	// The public registration method
	public RegistrationResult registerDevice(String uuid, Credentials credentials, ConfigurationEnv configurationEnv) 
	{
		
		// Initialize local variables
		this.setConfigurationEnv(configurationEnv);
		this.setCredentials(credentials);
		this.setUuid(uuid);
		
		// TODO: define the registry service URI and property name. If no exist we must define a default URI 
		String registryUri = configurationEnv.getEnvironments().getProperty("register.uri");
		
		//call post Method
		RegistrationResult result= this.postResource(registryUri, this.getCredentials());	
		
		
		return result;

	}

	//the public method for unregister
	public RegistrationResult unRegisterDevice()
	{
		
		// TODO: define the registry service URI and property name. If no exist we must define a default URI 
		String unRegisterUri = configurationEnv.getEnvironments().getProperty("unregister.uri");

		// call post Method
		RegistrationResult result = this.postResource(unRegisterUri, this.getCredentials());

		return result;

	}

	// public method for returning the registration status (true false)
	public boolean isRegistered()
	{
		// TODO: define the registry service URI and property name. If no exist we must define a default URI 
		String unRegisterUri = configurationEnv.getEnvironments().getProperty("register.uri");

		// call post Method
		RegistrationResult result = this.postResource(unRegisterUri, this.getCredentials());
	
		return result.isStatus();

	}
	
	
	// postResource posts postData data to the specific Rest (URI)
	private  <T> RegistrationResult postResource (String uri, T postData)
	{
		try
		{
			
			// create and initialize client for REST call
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			
			// serialize 'postData' Object to String
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);			
			String request = mapper.writeValueAsString(postData);

			
			// call resource  and get Results
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class,request);

			if (response.getStatus() != 201)
			{
				client.destroy();
				//throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			
			 RegistrationResult results = this.getRegistrationResults(response);


			//destroy 
			client.destroy();
			
			return results;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}



	
	private RegistrationResult getRegistrationResults(ClientResponse response)
	{
		// TODO: Transformations from Client Response to Registration Result Class
		RegistrationResult res = new RegistrationResult();
		
		if (response.getStatus()== 400) {
			res.setStatus(false);
			
		}
		else {
			res.setStatus(true);
		}
		res.setErrorMessage(Integer.toString(response.getStatus()));

		return res;
	}
	
	
	
	// Getters and setters

	public ConfigurationEnv getConfigurationEnv()
	{
		return configurationEnv;
	}

	public void setConfigurationEnv(ConfigurationEnv configurationEnv)
	{
		this.configurationEnv = configurationEnv;
	}

	public String getUuid()
	{
		return uuid;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public  void setUuid(String uuid) {
		this.uuid = uuid;
	}


}
