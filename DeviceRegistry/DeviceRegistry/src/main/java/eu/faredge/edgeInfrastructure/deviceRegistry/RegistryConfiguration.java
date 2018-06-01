package eu.faredge.edgeInfrastructure.deviceRegistry;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class RegistryConfiguration {
	public static Map<String, String> variables;
	
//	private static Class applicationClass = RegistryConfiguration.class;

	public static void main(String[] args) {
		variables = System.getenv();
		System.out.println("Registry Service env uri=" + variables.get("uri"));
		System.out.println("Registry Service env uri=" + variables.get("port"));
		SpringApplication application = new SpringApplication(RegistryConfiguration.class);
        Map<String, Object> map = new HashMap<>();        
		map.put("SERVER_PORT", variables.get("PORT"));
		application.setDefaultProperties(map);
		application.run(args);
		
		System.out.println(variables.get("uri"));
//		SpringApplication.run(RegistryConfiguration.class, args);
	}

}
