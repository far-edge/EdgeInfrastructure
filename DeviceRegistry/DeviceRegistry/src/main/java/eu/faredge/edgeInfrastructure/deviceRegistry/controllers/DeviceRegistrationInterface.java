package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;


import org.springframework.web.bind.annotation.RequestBody;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;

public interface DeviceRegistrationInterface {

	//@RequestMapping(value = "DeviceRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult deviceRegistration(@RequestBody DSM manifest); // , Object credentials)
	
	//@RequestMapping(value = "/DeviceRegistration", method = RequestMethod.DELETE)
	public RegistrationResult deviceRegistration(String id); // , Object credentials)	
	
}
