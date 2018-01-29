package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;

public interface DeviceRegistrationInterface {

	//@RequestMapping(value = "DeviceRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult deviceRegistration(@RequestBody DataSourceManifest manifest); // , Object credentials)
	
	//@RequestMapping(value = "/DeviceRegistration", method = RequestMethod.DELETE)
	public RegistrationResult deviceRegistration(String id); // , Object credentials)	
	
}
