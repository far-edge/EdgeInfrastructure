package eu.sensap.farEdge.dataRoutingClient.smartFactory.mqttClient;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
////import org.apache.commons.logging.Log;
////import org.apache.commons.logging.LogFactory;
////
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.integration.endpoint.MessageProducerSupport;
//import org.springframework.integration.handler.LoggingHandler;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

/**
 * Starts the Spring Context and will initialize the Spring Integration message flow.
 *
 * @author Gary Russell
 *
 */
//@SpringBootApplication
public class pahoMqtt {

	//private static final Log LOGGER = LogFactory.getLog(pahoMqtt.class);

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args - command line arguments
	 */
	public static void main(final String... args) {

//		LOGGER.info("\n========================================================="
//				  + "\n                                                         "
//				  + "\n          Welcome to Spring Integration!                 "
//				  + "\n                                                         "
//				  + "\n    For more information please visit:                   "
//				  + "\n    https://spring.io/projects/spring-integration        "
//				  + "\n                                                         "
//				  + "\n=========================================================" );
//
//		LOGGER.info("\n========================================================="
//				  + "\n                                                          "
//				  + "\n    This is the MQTT Sample -                             "
//				  + "\n                                                          "
//				  + "\n    Please enter some text and press return. The entered  "
//				  + "\n    Message will be sent to the configured MQTT topic,    "
//				  + "\n    then again immediately retrieved from the Message     "
//				  + "\n    Broker and ultimately printed to the command line.    "
//				  + "\n                                                          "
//				  + "\n=========================================================" );

//		SpringApplication.run(pahoMqtt.class, args);
	}

//	@Bean
//	public MqttPahoClientFactory mqttClientFactory() {
//		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//		factory.setServerURIs("tcp://localhost:1883");
//		factory.setUserName("guest");
//		factory.setPassword("guest");
//		return factory;
//	}

	// publisher
//
//	@Bean
//	public IntegrationFlow mqttOutFlow() {
//		return IntegrationFlows.from(CharacterStreamReadingMessageSource.stdin(),
//						e -> e.poller(Pollers.fixedDelay(1000)))
//				.transform(p -> p + " sent to MQTT")
//				.handle(mqttOutbound())
//				.get();
//	}
//
//	@Bean
//	public MessageHandler mqttOutbound() {
//		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("siSamplePublisher", mqttClientFactory());
//		messageHandler.setAsync(true);
//		messageHandler.setDefaultTopic("siSampleTopic");
//		return messageHandler;
//	}


	// consumer

	/*@Bean
	public IntegrationFlow mqttInFlow() {
		return IntegrationFlows.from(mqttInbound())
				.transform(p -> p + ", received from MQTT")
				.handle(logger())
				.get();
	}

	private LoggingHandler logger() {
		LoggingHandler loggingHandler = new LoggingHandler("INFO");
		loggingHandler.setLoggerName("siSample");
		return loggingHandler;
	}

	@Bean
	public MessageProducerSupport mqttInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("siSampleConsumer",
				mqttClientFactory(), "siSampleTopic");
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		return adapter;
	}*/

}