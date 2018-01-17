/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.consumerClient.models;

import java.util.Properties;
/***
 * This class stores configuration environments for data routing connections 
 * It is part of DataRouteClient data model
 */
public class ConfigurationEnv 
{	
	private String topic;
	private Properties environments;
	
	// Getters and setters
	 
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public Properties getEnvironments()
	{
		return environments;
	}
	public void setEnvironments(Properties environments)
	{
		this.environments = environments;
	}
}