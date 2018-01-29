package eu.faredge.edgeInfrastructure.deviceRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
@EnableWebSecurity
public class DeviceRegistrationConfiguration extends WebSecurityConfigurerAdapter 
{
	@Autowired  
	private BasicAuthenticationPoint basicAuthenticationPoint;  
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)  
		          .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build();     
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable();
	        http.authorizeRequests().antMatchers("/", "/api/**").permitAll()
	        .anyRequest().authenticated();
	        http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("george").password("george123").roles("USER");
    }
	
	

}
