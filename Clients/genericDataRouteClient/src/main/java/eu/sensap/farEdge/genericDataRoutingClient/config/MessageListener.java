package eu.sensap.farEdge.genericDataRoutingClient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.sensap.farEdge.genericDataRoutingClient.consumer.MQTTSubscriberBase;

@Component
public class MessageListener implements Runnable{

	@Autowired
	MQTTSubscriberBase subscriber;
	
	@Override
	public void run() {
		while(true) {
			subscriber.subscribeMessage("demoTopic2017");
		}
		
	}

}