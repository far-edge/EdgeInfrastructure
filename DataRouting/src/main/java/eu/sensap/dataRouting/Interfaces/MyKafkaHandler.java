package eu.sensap.dataRouting.Interfaces;

import java.util.HashMap;
import java.util.Map;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.MessageHandler;

public class MyKafkaHandler implements MyHandler {

	Map<String, Object> props = new HashMap<>();
	KafkaProducerMessageHandler<String, String> handler;
	
	public MyKafkaHandler(Map<String, Object> props, String topic )
	{	
		this.props = props;
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
	    handler.setTopicExpression(new LiteralExpression(topic));		
	}
	
	@Override
	public MessageHandler getHandler()
	{
		return this.handler;
	}

}
