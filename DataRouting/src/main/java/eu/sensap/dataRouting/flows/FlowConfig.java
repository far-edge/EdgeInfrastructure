package eu.sensap.dataRouting.flows;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowRegistration;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.MessageHandler;

import eu.sensap.dataRouting.Interfaces.HandlerFactory;
import eu.sensap.dataRouting.Interfaces.MessageProducerSupportFactroy;
import eu.sensap.dataRouting.model.RequestPojo;

@Configuration
public class FlowConfig {

	@Autowired
	private IntegrationFlowContext flowContext;
	
	public IntegrationFlow createFlow(RequestPojo request)
	{
		Map<String, Object> props = new HashMap<>();		
		if (request.getDestType().equals("KAFKA"))
		{			 
		    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,request.getDestUrl() +":"+request.getDestPort());
		    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		}
		else if (request.getDestType().equals("MQTT"))
		{
			String destUri[] = {request.getDestUrl()+":"+request.getDestPort()};
//			props.put("username", user);
//			props.put("password", password);			
			props.put("serverURIs",destUri);
			props.put("clientId",request.getDestTopic() + "client");
			props.put("async",true);			
		}
		
		IntegrationFlowBuilder flow = IntegrationFlows.from(inbound(request.getSourceTopic(),request.getSourceUrl(),request.getSourcePort(), request.getSourceType()));
		flow.transform(p -> p + ", received from MQTT");
		flow.handle(outbound2(props, request.getDestTopic(),request.getDestType()));
//		flow.handle(outbound(request.getDestTopic(), request.getDestUrl(), request.getDestPort(), request.getDestType()));
		return flow.get();
	}
	
	
	public MessageProducerSupport inbound(String topic, String uri, String port, String type)
	{
		System.out.println("topic=" + topic +" | uri=" + uri + " | port=" +port +" | type=" +type);
		MessageProducerSupport adapter = new MessageProducerSupportFactroy().getInbound(topic, uri, port, type);
		return adapter;		
	}
	
	public MessageHandler outbound(String topic, String uri, String port, String type) {
		MessageHandler messageHandler = new MessageProducerSupportFactroy().getOutbound(topic, uri, port, type);		
		return messageHandler;
	}
	
	public MessageHandler outbound2 (Map<String, Object> props, String topic,String type)
	{
		return new HandlerFactory().gethandler(props, topic, type);		
	}
	
	public IntegrationFlowRegistration addAnAdapter(RequestPojo request) {
		IntegrationFlow flow = createFlow(request);
		return this.flowContext.registration(flow).id(request.getSourceTopic()+":"+request.getDestTopic()).register();
	}

	public void removeAdapter(IntegrationFlowRegistration flowReg) {
		this.flowContext.remove(flowReg.getId());
	}
	
//old TODO: to be deleted if everythink is ok 
	
//	// @Bean
//	public IntegrationFlow buildFlow(String recieveTopic, String sendTopic, String uri[]) {
//		return IntegrationFlows.from(mqttInbound(recieveTopic, uri)).transform(p -> p + ", received from MQTT")
//				.handle(mqttOutbound(sendTopic, uri)).get();
//
//	}
//
//	// @Bean
//	public MessageProducerSupport mqttInbound(String recieveTopic, String uri[]) {
//		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(recieveTopic+ "client",
//				mqttClientFactory(uri), recieveTopic);
//		adapter.setCompletionTimeout(5000);
//		adapter.setConverter(new DefaultPahoMessageConverter());
//		adapter.setQos(1);
//		System.out.println("loop in mqttInbound\n");
//		return adapter;
//	}
//	
//
//
//	// @Bean
//	public MessageHandler mqttOutbound(String sendTopic, String uri[]) {
//		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(sendTopic + "client", mqttClientFactory(uri));
//		messageHandler.setAsync(true);
//		messageHandler.setDefaultTopic(sendTopic);
//		return messageHandler;
//	}
//
//	// @Bean
//	public MqttPahoClientFactory mqttClientFactory(String uri[]) {
//		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//		MqttConnectOptions options = new MqttConnectOptions();
//		options.setServerURIs(uri);
//		factory.setConnectionOptions(options);
//		return factory;
//	}


	

}
