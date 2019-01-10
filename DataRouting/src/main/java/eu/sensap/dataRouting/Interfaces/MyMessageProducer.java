package eu.sensap.dataRouting.Interfaces;

import org.springframework.integration.endpoint.MessageProducerSupport;

public interface MyMessageProducer {
	public MessageProducerSupport getMessageProducer();

}
