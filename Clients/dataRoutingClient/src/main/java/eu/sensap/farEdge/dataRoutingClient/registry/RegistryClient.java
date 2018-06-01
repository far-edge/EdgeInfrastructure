/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.registry;


import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
//import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;
import eu.sensap.farEdge.dataRoutingClient.interfaces.DeviceRegisterInterface;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
//import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;

/***
 * This class supports the basic registry operations 
 * There are three operations:
 * 	1. Registers a device with a specific UUID, Credentials, and Configuration Environments
 *  2. Unregisters e registered device
 *  3. Asks if a device is registered   
  */
public class RegistryClient implements DeviceRegisterInterface
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Credentials credentials;			//Credentials for registry connection
	private String registryUri;					//the end point from registry service
	private DSM dsm;							//Data source Manifest for the device

	//TODO to be deleted	
//	private ConfigurationEnv configurationEnv;	//configuration environmental values for registry and message bus connection
//	private String dsd;							//Data source definition for the data source
//	private String macAddress;					// device macAddress


	
	@Override
	public void create(String registryUri)
	{		
		this.setRegistryUri(registryUri);
//		this.setCredentials(credentials);
	}
	
	public RegistryClient(String registryUri) 
	{
		create(registryUri);		
	}
	

	// The public registration method
	@Override
	public RegistrationResult registerDevice(DSM dsm, Credentials credentials) 
	{
		log.debug("  +--Request for registration for DSM with URI =" + dsm.getUri());
		
		this.setDsm(dsm);
		this.setCredentials(credentials);
		
		//call post Method to connect with registry
		RegistrationResult result= this.postResource(this.registryUri, dsm, credentials);
		
		log.debug("  +--Registration returned status " + result.getStatus());		
		
		return result;
	}

	//the public method for unregister
	@Override
	public RegistrationResult unRegisterDevice(String id, Credentials credentials)
	{
		log.debug("Request for registration for DSM with id =" + id);
//		System.out.println("client:registryclient:unRegisterDevice==>dsmUri=" + uri + " registryUri=" + this.getRegistryUri());

		// call post Method
		RegistrationResult result = this.deleteResource(this.registryUri, id,credentials);

//		System.out.println("Client:RegistryCLient:unregister response=" + result.getResult());
		log.debug("Unregister returned status " + result.getStatus());

		return result;
	}

	// public method for returning the registration status (true false)
	@Override
	public boolean isRegistered(DSM dsm, Credentials credentials)
	{
		log.debug("check if DSM is registered with id=" + dsm.getId());

		// call post Method
		RegistrationResult result = this.postResource(this.registryUri, dsm,credentials);
		
		log.debug("Registration status for dsm_id=" + dsm.getId() + " is " + result.getStatus());
		if (result.getStatus()==RegistrationResultStatusEnum.SUCCESS)
			return true;
		else 
			return false;		

	}	
	
	// postResource posts postData data to the specific Rest (URI)
	private  <T> RegistrationResult postResource (String uri, T postData, Credentials credentials)
	{
		log.debug("    +--Request for post to uri=" + uri);
		try
		{			
			// create and initialize client for REST call
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(credentials.getUser(),credentials.getPassword()));
			WebResource webResource = client.resource(uri);
			
			
			// serialize 'postData' Object to String
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);			
			String request = mapper.writeValueAsString(postData);
			
			log.debug("    +--dsm=" + request);
			
			// call resource  and get Results
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class,request);
			
			

			//TODO why do I need this?
			if (response.getStatus() != 201)
			{
				log.debug("Response from rest{" + uri + "} has status " + response.getStatus());
				client.destroy();
			}
			
			
			//Get results as registration results
			RegistrationResult results = this.getRegistrationResults(response);

			//destroy client 
			client.destroy();
			
			log.debug("    +--Response from rest{" + uri + "} has status " + results.getStatus());
			
			return results;

		}
		catch (Exception e)
		{
//			TODO only for skipping registry			
			//e.printStackTrace();
			RegistrationResult results = new RegistrationResult();
			results.setStatus(RegistrationResultStatusEnum.SUCCESS);
			results.setBody(this.getFakeDsmId());
			results.setStatusMessage("Get a fake registration");			

			log.debug("    +--Getting Fake registration");
			
			return results;
		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			RegistrationResult results = new RegistrationResult();
//			results.setStatus(RegistrationResultStatusEnum.FAIL);
//			results.setStatusMessage("Error creating-initializing-calling resource or parsing the response");			
//
//			log.debug("Error creating-initializing-calling resource or parsing the response");
//			
//			return results;
//		}

		
	}
	
	private String getFakeDsmId()
	{
		UUID id = UUID.randomUUID();		
		return "dsm://"+id.toString();
	}

	private  <T> RegistrationResult deleteResource (String uri, String postData, Credentials credentials)
	{
		log.debug("Request for delete to uri=" + uri + ". Delete the id:" + postData);
		try
		{
			
			// create and initialize client for REST call			
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter(credentials.getUser(),credentials.getPassword()));
			WebResource webResource = client.resource(uri).queryParam("id", postData);
			
			// call resource  and get Results
			ClientResponse response = webResource.type("application/json").delete(ClientResponse.class);

			//TODO why do I need this?  
			if (response.getStatus() != 200)
			{
				client.destroy();
				//throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}	
			
			//Get results as registration results
			 RegistrationResult results = this.getRegistrationResults(response);

			//destroy 
			client.destroy();
			
			log.debug("Response from rest{" + uri + "} has status " + results.getStatus());
			
			return results;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			RegistrationResult results = new RegistrationResult();
			results.setStatus(RegistrationResultStatusEnum.FAIL);
			results.setStatusMessage("Error creating-initializing-calling resource or parsing the response");			

			log.debug("Error creating-initializing-calling resource or parsing the response");
			
			return results;
		}		
	}

	
	private RegistrationResult getRegistrationResults(ClientResponse response)
	{
		
		ObjectMapper mapper = new ObjectMapper();
		String createresponseString = response.getEntity(String.class);
		RegistrationResult res = new RegistrationResult();
		try {
			res = mapper.readValue(createresponseString, res.getClass());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		// TODO: Transformations from Client Response to Registration Result Class
//		RegistrationResult res = new RegistrationResult();
		
		if (response.getStatus()== 400)
		{
			res.setStatus(RegistrationResultStatusEnum.NOTFOUND);
		}
		else
		{
			res.setStatus(RegistrationResultStatusEnum.SUCCESS);
		}
		res.setStatusMessage(Integer.toString(response.getStatus()));
		

		return res;
	}
	
	
//	
//	private void createDsm()
//	{
//		dsm = new DSM();		
//		dsm.setDataSourceDefinitionReferenceID(dsd);
//		dsm.setMacAddress(macAddress);
//		dsm.setUri(configurationEnv.getEdgeUri()+ macAddress);		
//		dsm.setDataSourceDefinitionInterfaceParameters(createDsdIp());		
//	}
//	
//
//	private DataSourceDefinitionInterfaceParameters createDsdIp ()
//	{
//		DataSourceDefinitionInterfaceParameters dsdip = new DataSourceDefinitionInterfaceParameters();
//		Set<Parameter> paramSet = null;
//		
//		dsdip.setDescr(configurationEnv.getTopic());
//		
//		Parameter top = new Parameter();
//		top.setKey("topic");
//		top.setValue(configurationEnv.getTopic());
//		paramSet.add(top);
//		
//		Set<String> keys = configurationEnv.getKafkaProps().stringPropertyNames();
//	    for (String key : keys) {
//	    	Parameter e = new Parameter();
//	    	e.setKey(key);
//	    	e.setValue(configurationEnv.getKafkaProps().getProperty(key));
//	    	paramSet.add(e);
//	    	System.out.println(key + " : " + configurationEnv.getKafkaProps().getProperty(key));
//	    }
//
//		dsdip.setParameter(paramSet);
//	
//		return dsdip;
//	}
//	
	
	
	// Getters and setters
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public DSM getDsm() {
		return dsm;
	}

	public void setDsm(DSM dsm) {
		this.dsm = dsm;
	}

	public String getRegistryUri() {
		return registryUri;
	}

	public void setRegistryUri(String registryUri) {
		this.registryUri = registryUri;
	}


	

//	public String getDsd() {
//		return dsd;
//	}
//
//	public void setDsd(String dsd) {
//		this.dsd = dsd;
//	}
//
//	public String getMacAddress() {
//		return macAddress;
//	}
//
//	public void setMacAddress(String macAddress) {
//		this.macAddress = macAddress;
//	}
//
//	public ConfigurationEnv getConfigurationEnv()
//	{
//		return configurationEnv;
//	}
//
//	public void setConfigurationEnv(ConfigurationEnv configurationEnv)
//	{
//		this.configurationEnv = configurationEnv;
//	}


	


}
