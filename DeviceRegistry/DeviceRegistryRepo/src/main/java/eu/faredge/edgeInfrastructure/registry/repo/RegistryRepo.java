package eu.faredge.edgeInfrastructure.registry.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistryRepo
{
	public static Map<String, String> variables;

	public static void main(String[] args)
	{
		variables = System.getenv();
//		SpringApplication.run(RegistryRepo.class, args);
		
		SpringApplication application = new SpringApplication(RegistryRepo.class);
        Map<String, Object> map = new HashMap<>();        
		map.put("SERVER_PORT", variables.get("PORT"));
		application.setDefaultProperties(map);
		application.run(args);
	}
}