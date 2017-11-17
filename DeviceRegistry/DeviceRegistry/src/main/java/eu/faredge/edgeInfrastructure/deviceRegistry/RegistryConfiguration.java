package eu.faredge.edgeInfrastructure.deviceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RegistryConfiguration {
	
	private static Class applicationClass = RegistryConfiguration.class;

	public static void main(String[] args) {
		SpringApplication.run(RegistryConfiguration.class, args);
	}

}
