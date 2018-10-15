package eu.sensap.farEdge.genericDataRoutingClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

//@Configuration
//@EnableIntegration
//@IntegrationComponentScan("eu.sensap.farEdge.genericDataRoutingClient.impl")
//@ComponentScan("eu.sensap.farEdge.genericDataRoutingClient.impl")
@Component
public class AppConfiguration {
	
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
  
}
