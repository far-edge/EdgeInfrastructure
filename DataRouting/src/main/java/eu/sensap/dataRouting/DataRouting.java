package eu.sensap.dataRouting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//@Configuration
//@EnableAutoConfiguration
//@IntegrationComponentScan
@SpringBootApplication
public class DataRouting {

	public static void main(String[] args)
	{
		
//		ConfigurableApplicationContext context = SpringApplication.run(DataRouteClient.class, args);
		ApplicationContext context = SpringApplication.run(DataRouting.class, args);		
		//SpringApplication application = new SpringApplication(DataRouteClient.class);
//        Map<String, Object> map = new HashMap<>();        
//		map.put("SERVER_PORT", variables.get("PORT"));
//		application.setDefaultProperties(map);
//		application.run(args);
		
		
	}
}