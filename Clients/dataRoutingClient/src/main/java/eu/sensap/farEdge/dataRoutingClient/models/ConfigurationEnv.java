/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.models;

import java.util.Properties;
/***
 * This class stores configuration environments for data routing connections 
 * It is part of DataRouteClient data model
 */
public class ConfigurationEnv 
{	
//	private Properties kafkaProps;
	private String topic;
//	private String registryUri;
//	private String edgeUri;
	private Properties kafkaProps;
	
	// Getters and setters
	 
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public Properties getKafkaProps()
	{
		return kafkaProps;
	}
	public void setKafkaProps(Properties kafkaProps)
	{
		this.kafkaProps = kafkaProps;
	}
//	public String getRegistryUri() {
//		return registryUri;
//	}
//	public void setRegistryUri(String registryUri) {
//		this.registryUri = registryUri;
//	}
//	public String getEdgeUri() {
//		return edgeUri;
//	}
//	public void setEdgeUri(String edgeUri) {
//		this.edgeUri = edgeUri;
//	}

}