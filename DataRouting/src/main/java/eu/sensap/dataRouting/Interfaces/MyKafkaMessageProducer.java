package eu.sensap.dataRouting.Interfaces;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter.ListenerMode;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

public class MyKafkaMessageProducer implements MyMessageProducer{
	Map<String, Object> props = new HashMap<>();
	KafkaMessageDrivenChannelAdapter<String, String> adapter;
	
	public MyKafkaMessageProducer(Map<String, Object> props, String topic)
	{
		this.props = props;
		
//		this.props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, uri+":"+port);
//		this.props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		this.props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		this.props.put(ConsumerConfig.GROUP_ID_CONFIG, "helloworld");
	    ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(this.props);
		ContainerProperties properties = new ContainerProperties(topic);
		KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(consumerFactory, properties);
	    this.adapter =   new KafkaMessageDrivenChannelAdapter<>(container, ListenerMode.record);
		
	}

	@Override
	public MessageProducerSupport getMessageProducer() {		
		return this.adapter;
	}

}
