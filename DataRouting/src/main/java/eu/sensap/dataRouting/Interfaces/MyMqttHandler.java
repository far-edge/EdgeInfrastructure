




package eu.sensap.dataRouting.Interfaces;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

public class MyMqttHandler implements MyHandler{
	//Map<String, Object> props;
	MqttPahoMessageHandler handler = null;
	
	public MyMqttHandler (Map<String, Object> props, String topic )
	{
		//this.props = new HashMap<>(props);
		String client_id= (String) props.get("clientId");
		String uri[] = (String[]) props.get("serverURIs");
		String port = (String) props.get("port");
		
//		String destUri[] = {uri};
		System.out.println("props size=" + props.size() + " uri =" + uri + "port= " + port);
		
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(uri);
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(options);			
		handler = new MqttPahoMessageHandler(client_id, factory);
		
		handler.setAsync(true);
		handler.setDefaultTopic(topic);
		System.out.println("handler =" + handler.isRunning());
		
	}
	
	@Override
	public MessageHandler getHandler()
	{
		return this.handler;
	}

}
