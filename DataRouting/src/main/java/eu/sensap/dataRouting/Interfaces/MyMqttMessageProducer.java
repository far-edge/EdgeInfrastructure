package eu.sensap.dataRouting.Interfaces;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

public class MyMqttMessageProducer implements MyMessageProducer{
	Map<String, Object> props = new HashMap<>();
	MqttPahoMessageDrivenChannelAdapter adapter;
	
	public MyMqttMessageProducer(Map<String, Object> props, String topic) {
		
		this.props = props;
		String client_id= (String) this.props.get("client_id");
		String uri = (String) this.props.get("uri");
		String port = (String) this.props.get("port");
		
		String mqttUri[] = {uri + ":" + port};
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(mqttUri);
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();		
		factory.setConnectionOptions(options);
		adapter = new MqttPahoMessageDrivenChannelAdapter(client_id,factory, topic);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
	}

	@Override
	public MessageProducerSupport getMessageProducer() {
		return this.adapter;
	}

}
