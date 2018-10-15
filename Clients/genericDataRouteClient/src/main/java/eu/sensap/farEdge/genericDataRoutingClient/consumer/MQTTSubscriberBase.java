package eu.sensap.farEdge.genericDataRoutingClient.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MQTTSubscriberBase {

	public static final Logger logger = LoggerFactory.getLogger(MQTTSubscriberBase.class);

	/**
	 * Subscribe message
	 * 
	 * @param topic
	 * @param jasonMessage
	 */
	public void subscribeMessage(String topic);

	/**
	 * Disconnect MQTT Client
	 */
	public void disconnect();
}