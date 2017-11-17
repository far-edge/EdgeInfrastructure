package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.faredge.edgeInfrastructure.deviceRegistry.business.BusinessImp;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceDefinition;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;
import eu.faredge.edgeInfrastructure.registry.models.DataTopic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/registry")
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class DeviceRegistrationController 
{
	private BusinessImp bimpl= new BusinessImp();

	
	@RequestMapping(value ="/unRegister", method=RequestMethod.DELETE)
	public RegistrationResult unRegister(String id)
	{
		String statusMessage = "Unregister Succesfull of id=" + id;
		RegistrationResultStatusEnum status = RegistrationResultStatusEnum.SUCCESS;
		RegistrationResult res = new RegistrationResult();		
		res.setStatus(status );
		res.setStatusMessage(statusMessage);
		return res;
	}
	
	@RequestMapping(value ="/getCompatibleDSD", method=RequestMethod.GET)
	public String getCompatibleDSD()
	{
		return null;

	}
	
	@RequestMapping(value ="/getDataTopics", method=RequestMethod.GET)
	public ArrayList<DataTopic> getDataTopics()
	{
		return bimpl.getDataTopics();
//		return null;

	}
	
	
	@RequestMapping(value ="/getDataSourceDefinition", method=RequestMethod.GET)
	public ArrayList<DataSourceDefinition> getDataSourceDefinition()
	{
		return bimpl.getDataSourceDefinition();
//		return null;

	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ArrayList<DataSourceManifest> getCompatibleDSM()
	{
		return new ArrayList<DataSourceManifest>();
	}

	
	@RequestMapping(value="deviceRegistration" , method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public RegistrationResult deviceRegistration(@RequestBody DataSourceManifest manifest)//, Object credentials)
	{
		System.out.println("Controller:deviceRegistration==> dmsuri= " + manifest.getUri());
		System.out.println("Controller:deviceRegistration==> dsduri=" + manifest.getDataSourceDefinition().getUri());
		
		try {
			boolean loggin = true;
			boolean authorized = true;
			boolean registered;
			
		
			//Login to service based on credentials
			if(!loggin)
			{
				RegistrationResult res =  new RegistrationResult();
				res.setStatus(RegistrationResultStatusEnum.LOGINFAILURE);
				res.setStatusMessage("Could not login"); //or other message coming from Authentication system
				
				return res;
			}
			//If logged-in check authorization
			if (!authorized)
			{
				RegistrationResult res = new RegistrationResult();
				res.setStatus(RegistrationResultStatusEnum.DENIED);
				res.setStatusMessage("Authorization Failure");
				
				return res;
			}
			

			
			//if authorized and authenticated the create registry 
			//rest client
			DataSourceManifest registeredDsm = bimpl.createDSM(manifest);
			if (registeredDsm!=null)
			{
			RegistrationResult res =  new RegistrationResult();
			res.setStatus(RegistrationResultStatusEnum.SUCCESS);
			res.setStatusMessage("Succes uuid=" + registeredDsm.getDataSourceManifestId());			
			
			return res;
			}
			else {
				RegistrationResult res = new RegistrationResult();
				res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
				res.setStatusMessage("Error creating dsm");
				return res;
			}
			
			
		} catch (Exception e) {
			RegistrationResult res = new RegistrationResult();
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage(e.getMessage());
			
			return res;
		}
		
		
	}
	
	@RequestMapping(value ="/getSomething", method=RequestMethod.GET)	
	public String getSomething() {
		return "12344567";
	}
	

//	
//    @RequestMapping("/")
//    public String welcome() {//Welcome page, non-rest
//        return "Welcome to RestTemplate Example.";
//    }
//
//
//    
//    @RequestMapping("/hello/{name}")
//    public Greeting message(@PathVariable String name) {
//
//        Greeting  msg = new Greeting(name, "Hello " + name);
//        return msg;
//    }
	
}
