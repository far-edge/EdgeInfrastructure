package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/registry")
public class DeviceRegistrationController 
{
	@RequestMapping(method=RequestMethod.POST)
	public RegistrationResult deviceRegistration(DataSourceManifest manifest, Object credentials)
	{
		try {
			boolean loggin = false;
			boolean authorized = false;
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
			
			
			return null;
			
			
		} catch (Exception e) {
			RegistrationResult res = new RegistrationResult();
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage(e.getMessage());
			
			return res;
		}
		
		
	}
	
}
