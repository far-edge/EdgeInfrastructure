/**
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.registry.test;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;
import eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient;

/**
 * @author george
 *
 */
public class RegistryClientTest
{
	private static Credentials credentials;		
	private ConfigurationEnv configurationEnv ;	
	private static String uuid;	
	private RegistryClient client;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Properties prop= new Properties();
		configurationEnv = new ConfigurationEnv();
		client = new RegistryClient();
		uuid="test-uuid";
		try {
			// load a properties file from class path, inside static method			
			prop.load(RegistryClientTest.class.getClassLoader().getResourceAsStream("dataRouting.properties"));
			configurationEnv.setEnvironments(prop);
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		
	}

	/**
	 * Test method for {@link eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient#register(java.lang.String, eu.sensap.farEdge.dataRoutingClient.models.Credentials, eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv)}.
	 */
	@Test
	public final void testRegister()
	{
		RegistrationResult res =client.register(uuid, credentials, configurationEnv);
		System.out.println("Message=" +res.getErrorMessage() +" succes=" + res.isStatus());		
		// TODO : should define  required data for registration
		fail("Not yet implemented"); 
		
	}

	/**
	 * Test method for {@link eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient#unRegister()}.
	 */
	@Test
	public final void testUnRegister() {
		RegistrationResult res = client.unRegister();
		System.out.println("Message=" +res.getErrorMessage() +" succes=" + res.isStatus());
		// TODO : should define  required data for registration
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient#isRegistered()}.
	 */
	@Test
	public final void testIsRegistered() {
		boolean res = client.isRegistered();
		System.out.println(" is registered=" + res);
		// TODO : should define  required data for registration
		fail("Not yet implemented");
	}

}
