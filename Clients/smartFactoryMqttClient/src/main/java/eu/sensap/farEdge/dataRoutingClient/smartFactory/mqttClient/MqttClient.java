package eu.sensap.farEdge.dataRoutingClient.smartFactory.mqttClient;


import java.util.ArrayList;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
public class MqttClient {

	private MQTT client = new MQTT();
	private BlockingConnection connection;
	
	public MqttClient(String host,String port, String userName, String password) throws Exception
	{
//		System.out.println("set host=" + host);
		//client.setHost(host);
		client.setHost(host, Integer.parseInt(port));		
//		System.out.println("set user=" + userName);
		client.setUserName(userName);
//		System.out.println("set pass=" + password);
		client.setPassword(password);
	}
	
	public MqttClient(String host) throws Exception
	{
		System.out.println("set host");
		client.setHost(host);		
	}	

	
	public ArrayList<String> subscribe(Topic[] topics) throws Exception
	{
		ArrayList<String> msgs= new ArrayList<String>();
		connection =client.blockingConnection();
		connection.connect();
		System.out.println("created");
		int count = topics.length; 
		Topic[] tpcs = {new Topic("Festo/#", QoS.EXACTLY_ONCE)};
		System.out.println("count=" +count);
		connection.subscribe(tpcs);
//		connection.subscribe(topics);
		for (int i=0;i<count;i++) {
			Message msg = connection.receive();
			String value = msg.getTopic() + "|" + new String(msg.getPayload());
			msgs.add(value);
		}
		return msgs;
	}
	
	public void subscribe(String topic) throws Exception
	{
		@SuppressWarnings("unused")
		String msgs="";
		connection =client.blockingConnection();
		connection.connect();
		Topic[] topics = {new Topic(topic, QoS.EXACTLY_ONCE)};
		connection.subscribe(topics);

	}
	
	public String recieve() throws Exception
	{
		Message msg = connection.receive();
		String value = new String(msg.getPayload());
		return value;
	}
	
	
}
