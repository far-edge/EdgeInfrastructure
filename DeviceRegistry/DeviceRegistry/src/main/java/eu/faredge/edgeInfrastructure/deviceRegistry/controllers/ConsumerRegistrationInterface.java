package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.models.DSM;
import eu.faredge.edgeInfrastructure.registry.models.DataChannelDescriptor;
import eu.faredge.edgeInfrastructure.registry.models.DCM;


public interface ConsumerRegistrationInterface {
	@RequestMapping(value = "ConsumerRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult ConsumerRegistration(@RequestBody DCM manifest);// , Object credentials)
	
	@RequestMapping(value = "/ConsumerRegistration", method = RequestMethod.DELETE)
	public RegistrationResult ConsumerRegistration(String id); // , Object credentials)
	
	@RequestMapping(value="compatibleDSM", method = RequestMethod.GET)
	public ArrayList<DSM> getCompatibleDSM(String id);
	
	@RequestMapping(value="accessToDSM",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DataChannelDescriptor getAccessToDSM(@RequestBody DataChannelDescriptor dcd);
	

}
