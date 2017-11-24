package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.faredge.edgeInfrastructure.deviceRegistry.business.BusinessImp;
import eu.faredge.edgeInfrastructure.deviceRegistry.business.LedgerCredentials;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.DataChannelDescriptor;
import eu.faredge.edgeInfrastructure.registry.models.DataConsumerManifest;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/registry")
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class ConsumerRegistrationController {
	private BusinessImp bimpl = new BusinessImp();


	@RequestMapping(value = "ConsumerRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult ConsumerRegistration(@RequestBody DataConsumerManifest manifest)// , Object credentials)
	{		
		//TODO delete these 3 lines
		System.out.println("Controller:ConsumerRegistration==> dcmuri= " + manifest.getUri());
		String user="george";
		String password="george123";

		try 
		{
			RegistrationResult res = new RegistrationResult();
			boolean loggin = true;								//local authentication
			boolean authorized = false;							//Ledger authorization
			boolean registered=false;							//Register to registry repo
			
			// Login to service based on credentials
			if (!loggin)
			{
				res.setStatus(RegistrationResultStatusEnum.LOGINFAILURE);
				res.setStatusMessage("Could not login"); // or other message coming from Authentication system
				return res;
			}
			
			// If logged-in check authorization
			//authorization through Ledger Client 
			authorized = bimpl.authorizeToLedger(new LedgerCredentials(user, password));
			if (!authorized)
			{
				res.setStatus(RegistrationResultStatusEnum.DENIED);
				res.setStatusMessage("Authorization Failure"); 
				return res;
			}

			// if authorized and authenticated creates the data source manifest to the
			// registry repo
			DataConsumerManifest registeredDcm = bimpl.createDcm(manifest);
			if (registeredDcm!=null)
			{
				registered = true;
			}
			
			if (!registered)
			{
				res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
				res.setStatusMessage("Error creating dcm");
				return res;
			}
			
			// Successful authentication, authorization and registration. Create response message		
			res.setStatus(RegistrationResultStatusEnum.SUCCESS);			
			res.setStatusMessage("Succes uuid=" + registeredDcm.getDataConsumerManifestId());
			System.out.println("Controller:ConsumerRegistration==> registered!=" + registeredDcm.getDataConsumerManifestId());
			return res;
			

		}
		catch (Exception e)
		{
			RegistrationResult res = new RegistrationResult();
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage(e.getMessage());
			return res;
		}

	}
	
	@RequestMapping(value = "/ConsumerRegistration", method = RequestMethod.DELETE)
	public RegistrationResult ConsumerRegistration(String id) // , Object credentials)
	{
		String user="george";
		String password="george123";
		
		boolean loggin = true;
		boolean authorized = false;
		boolean deleteStatus=false;
		RegistrationResult res = new RegistrationResult();
		
		//TODO is this necessary?
		// Login to service based on credentials
		if (!loggin)
		{
			res.setStatus(RegistrationResultStatusEnum.LOGINFAILURE);
			res.setStatusMessage("Could not login"); // or other message coming from Authentication system
			return res;
		}
		
		// If logged-in check authorization
		//authorization through Ledger Client 
		authorized = bimpl.authorizeToLedger(new LedgerCredentials(user, password));
		if (!authorized)
		{
			res.setStatus(RegistrationResultStatusEnum.DENIED);
			res.setStatusMessage("Authorization Failure"); 
			return res;
		}
		
		// if authorized and authenticated calls registry repo to delete the data source manifest 
		deleteStatus = bimpl.deleteDcm(id);
		if (!deleteStatus)
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Error deleting dcm or not exists");
			return res;
		}
		
		String statusMessage = "Unregister Succesfull of id=" + id;
		RegistrationResultStatusEnum status = RegistrationResultStatusEnum.SUCCESS;
		res.setStatus(status);
		res.setStatusMessage(statusMessage);
		return res;
	}
	
	@RequestMapping(value="compatibleDSM", method = RequestMethod.GET)
	public ArrayList<DataSourceManifest> getCompatibleDSM(String consumerId) {
		return new ArrayList<DataSourceManifest>();
	}
	
	@RequestMapping(value="accessToDSM", method = RequestMethod.GET)
	public DataChannelDescriptor getAccessToDSM(String consumerId, String dsmId) {
		
		return new DataChannelDescriptor();
	}

}