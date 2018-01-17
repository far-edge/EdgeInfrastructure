package eu.sensap.farEdge.dataRoutingClient.ClientTest;

import java.util.Properties;


import eu.sensap.farEdge.dataRoutingClient.impl.DataRouteClient;
import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;


public class ClientTestApp 
{
//	private static final Logger logger = LoggerFactory.getLogger(ClientTestApp.class);
	public static void main(String[] args)
	{
		ConfigurationEnv env = new ConfigurationEnv();
		env.setTopic("clientTest");
		String message = "First message from client";
		Properties prop = new Properties();
		try {
			// load a properties file from class path
//			logger.info("Loading env from datarouting property file ");
			prop.load(ClientTestApp.class.getClassLoader().getResourceAsStream("dataRouting.properties"));
//			logger.info("properties loaded");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		env.setEnvironments(prop);
//		logger.info("Create and initialize client");
		DataRouteClient client = new DataRouteClient(env);

//		logger.info("publishing message");
		client.publish(message);
//		logger.info("message published");
		
//		try {
//			client.publish(message);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

	}

}
