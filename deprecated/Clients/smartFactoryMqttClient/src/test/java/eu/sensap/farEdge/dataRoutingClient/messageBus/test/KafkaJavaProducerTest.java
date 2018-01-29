/**
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.messageBus.test;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.sensap.farEdge.dataRoutingClient.messageBus.KafkaJavaProducer;


/**
 * @author george
 *
 */
public class KafkaJavaProducerTest {
	private Properties prop = new Properties();
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpConnection() throws Exception {
	
      try {
          //load a properties file from class path, inside static method    	  
          prop.load(KafkaJavaProducerTest.class.getClassLoader().getResourceAsStream("dataRouting.properties"));
      } 
      catch (Exception ex) {
          ex.printStackTrace();
//          fail("Failed to open property File"); 
      }
		
	}

	/**
	 * Test method for {@link eu.sensap.farEdge.dataRoutingClient.messageBus.KafkaJavaProducer#runSyncProducer(java.lang.String, java.lang.String, java.util.Properties)}.
	 */
	@Test
	public final void testRunSyncProducer()
	{
		String topic = "Uni-Test-Topic1";			//
		//String macAdress ="DEVICETest"; //"00:33:13:AC:1F:CB";
		String message= "Unit Test Message1";		//
		//String dsm = "a89eab82-ca07-4023-8a66-540905af4a5a";
		//topic = macAdress +"\\" + dsm;

        try {
            
            KafkaJavaProducer producer = new KafkaJavaProducer();
//            producer.runSyncProducer(100,"farEdge-analytics", "Hello FAR EDGE!");
            producer.runSyncProducer(topic, message, this.prop);
            
        } catch (Exception e) {
            e.printStackTrace();
//            fail("Fail to produce");
        }
        
		
	}

	/**
	 * Test method for {@link eu.sensap.farEdge.dataRoutingClient.messageBus.KafkaJavaProducer#runAsyncProducer(int, java.lang.String, java.lang.String, java.util.Properties)}.
	 */
	@Test
	public final void testRunAsyncProducer() {
		fail("Not yet implemented"); // TODO
	}

}
