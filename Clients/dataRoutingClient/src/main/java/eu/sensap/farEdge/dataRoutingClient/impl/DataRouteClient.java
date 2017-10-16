/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.impl;

import eu.sensap.farEdge.dataRoutingClient.interfaces.MessageBusInterface;
import eu.sensap.farEdge.dataRoutingClient.interfaces.RegisterInterface;
import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;
import eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient;
import eu.sensap.farEdge.dataRoutingClient.messageBus.KafkaJavaProducer;


/**
 * This class provides the data route client for data producers.
 * Implements the MessageBus and the Register Interfaces 
 * Provides the following operations <br/>
 * 	1. register operation:   registers to the registry 
 * 	2. unRegister operation: unregisters from the registry
 * 	3. isRegistered operation: Gets the registration status
 * 	4. publish method: publishes (in a sync way) a message to Message bus
 * 	5. publishAsync method: publishes (in an Async way) a message to Message bus
 *
 */
public class DataRouteClient implements MessageBusInterface<String>, RegisterInterface
{
	
	private Credentials credentials;					//Required credentials for registration
	private static ConfigurationEnv configurationEnv;	//Required environmental parameters for connecting to Registry and Message bus
	private RegistryClient registryClient;				//Client for registry
	private KafkaJavaProducer kafkaClient;				//Client for Message bus
	
	public DataRouteClient ()
	{
		registryClient = new RegistryClient();
	}
	public DataRouteClient (ConfigurationEnv configurationEnv) 
	{		
		this.setConfigurationEnv(configurationEnv);
		registryClient = new RegistryClient();
	}

	public RegistrationResult register(String id, Credentials credentials, ConfigurationEnv configurationEnv)
	{
		// Store local variables
		this.setConfigurationEnv(configurationEnv);
		this.setCredentials(credentials);
		
		//Call registry client	to register	
		RegistrationResult RegistrationResult = registryClient.register(id, credentials, configurationEnv);			
		
		return RegistrationResult;
	}

	public RegistrationResult unRegister()
	{
		
		// Call registry client to unregister 
		RegistrationResult RegistrationResult = registryClient.unRegister();
		
		return RegistrationResult ;
	}

	public boolean isRegistered()
	{
		// Call registry client to check status and return status		
		return  registryClient.isRegistered();
	}

	public void publish(String message)
	{
//		Thread.currentThread().setContextClassLoader(null);
//		boolean status=false;
		try
		{
            KafkaJavaProducer producer = new KafkaJavaProducer();

            producer.runSyncProducer(configurationEnv.getTopic(), message, configurationEnv.getEnvironments());
            
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }	
	}

	public void publishAsync(String message)
	{
		// TODO Auto-generated method stub
		
	}
	

	
	//Getters and setters
	
	public Credentials getCredentials()
	{
		return credentials;
	}

	public void setCredentials(Credentials credentials)
	{
		this.credentials = credentials;
	}

	public ConfigurationEnv getConfigurationEnv()
	{
		return configurationEnv;
	}

	public void setConfigurationEnv(ConfigurationEnv configurationEnv)
	{
		DataRouteClient.configurationEnv = configurationEnv;
	}
	
	public RegistryClient getRegistryClient()
	{
		return registryClient;
	}
	
	public void setRegistryClient(RegistryClient registry)
	{
		this.registryClient = registry;
	}
	
	public KafkaJavaProducer getKafka()
	{
		return kafkaClient;
	}
	
	public void setKafka(KafkaJavaProducer kafka)
	{
		this.kafkaClient = kafka;
	}

}
