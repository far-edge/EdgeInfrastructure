package eu.sensap.dataRouting.Interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter.ListenerMode;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.messaging.MessageHandler;

public class MessageProducerSupportFactroy {
	public MessageProducerSupport getInbound(String topic, String uri, String port, String type)
	{
		
		//TODO: read from properties file
		if (type.equals("MQTT"))
		{
			String mqttUri[] = {uri + ":" + port};
			DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
			MqttConnectOptions options = new MqttConnectOptions();
			options.setServerURIs(mqttUri);
			factory.setConnectionOptions(options);
			MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(topic+ "client",
					factory, topic);
			adapter.setCompletionTimeout(5000);
			adapter.setConverter(new DefaultPahoMessageConverter());
			adapter.setQos(1);
			return adapter;
		}
		else if (type.equals("KAFKA"))
		{
			
			//ChannelInterceptorAdapter adapter= new ChannelInterceptorAdapter() {
			//};
			Map<String, Object> props = new HashMap<>();
		    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, uri+":"+port);
		    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		    props.put(ConsumerConfig.GROUP_ID_CONFIG, "helloworld");
		    ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(props);
			ContainerProperties properties = new ContainerProperties(topic);
			KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(consumerFactory, properties);
		    KafkaMessageDrivenChannelAdapter<String, String> adapter =   new KafkaMessageDrivenChannelAdapter<>(container, ListenerMode.record);
//		    adapter.setOutputChannel(received());
		    return adapter;			
		}
		return null;		
	}
	
	
	public MessageHandler getOutbound(String topic, String uri, String port, String type)
	{
//		Properties p= new Properties();
//		String handleClass=null;
//		if (p.containsKey(type))
//			handleClass = p.getProperty(type);
//		else 
//			return null;
			
		if (type.equals("MQTT"))
		{
			
			
			String destUri[] = {uri+":"+port};
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setServerURIs(destUri);
			DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
			factory.setConnectionOptions(options);			
			MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(topic + "client", factory);
			messageHandler.setAsync(true);
			messageHandler.setDefaultTopic(topic);
			return messageHandler;
		}
		else if (type.equals("KAFKA"))
		{

			Map<String, Object> props = new HashMap<>();
		    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, uri+":"+port);
		    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);
			KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
			KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
		    handler.setTopicExpression(new LiteralExpression(topic));
		    return handler;
		    			
		}
			return null;
	}

}
