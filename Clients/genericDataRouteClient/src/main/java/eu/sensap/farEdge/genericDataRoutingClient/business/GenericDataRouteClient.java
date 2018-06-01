package eu.sensap.farEdge.genericDataRoutingClient.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenericDataRouteClient {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(GenericDataRouteClient.class);
//		application.setDefaultProperties(map);
		application.run(args);
	}
}