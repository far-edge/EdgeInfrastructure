package eu.faredge.edgeInfrastructure.deviceRegistry.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.faredge.edgeInfrastructure.deviceRegistry.business.BusinessImp;
import eu.faredge.edgeInfrastructure.deviceRegistry.business.LedgerCredentials;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/registry")
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class DeviceRegistrationController implements DeviceRegistrationInterface
{

	private BusinessImp bimpl = new BusinessImp();

	@RequestMapping(value = "DeviceRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult deviceRegistration(@RequestBody DSM manifest)// , Object credentials)
	{
		
		//TODO delete these 4 lines
		System.out.println("Controller:deviceRegistration==> dmsid= " + manifest.getId() + " uri=" + manifest.getUri());
		System.out.println("Controller:deviceRegistration==> dsduri=" + manifest.getDataSourceDefinitionReferenceID());
		System.out.println("Controller:deviceRegistration==> params=" + manifest.getDataSourceDefinitionInterfaceParameters().getParameter().size());
		String user="george";
		String password="george123";
		
		//manifest.setUri(manifest.getId());

		try 
		{
			RegistrationResult res = new RegistrationResult();
			res.setBody("");
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
			//create dsm to ledger
			String dsmId = bimpl.createDsmToLedger(manifest);
			manifest.setId(dsmId);

			// if authorized and authenticated creates the data source manifest to the
			// registry repo
			
			DSM registeredDsm = bimpl.createDsm(manifest);
			if (registeredDsm!=null)
			{
				registered = true;
			}
			
			if (!registered)
			{
				res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
				res.setStatusMessage("Error creating dsm");
				
				return res;
			}
			
			// Successful authentication, authorization and registration. Create response message		
			res.setStatus(RegistrationResultStatusEnum.SUCCESS);			
			res.setStatusMessage("Succes uuid=" + registeredDsm.getUri());
			res.setBody(registeredDsm.getId());
			System.out.println("Controller:deviceRegistration==> registered!=" + registeredDsm.getId());
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

	@RequestMapping(value = "/DeviceRegistration", method = RequestMethod.DELETE)
	public RegistrationResult deviceRegistration( String id) // , Object credentials)
	{
		String user="george";
		String password="george123";
		System.out.println("Registry:Controller:deviceRegistration unregister==> id= " + id);
		
		boolean loggin = true;
		boolean authorized = false;
		boolean deleteStatus=false;
		RegistrationResult res = new RegistrationResult();
		res.setBody("");
		
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
		deleteStatus= bimpl.deleteDsm(id);
		
		if (!deleteStatus)
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Error deleting dsm or not exists");
			return res;
		}
		
		String statusMessage = "Unregister Succesfull of id=" + id;
		RegistrationResultStatusEnum status = RegistrationResultStatusEnum.SUCCESS;
		res.setStatus(status);
		res.setStatusMessage(statusMessage);
		res.setBody(id);
		System.out.println("Controller:deviceRegistration unregister succesfull==> id= " + id);
		return res;
	}
	
	
	
	// TODO delete after this
/*
	@RequestMapping(value = "/getDataSourceDefinition", method = RequestMethod.GET)
	public ArrayList<DataSourceDefinition> getDataSourceDefinition()
	{
		return bimpl.getDataSourceDefinition();
		// return null;

	}
*/
//	@RequestMapping(value = "/unRegister", method = RequestMethod.DELETE)
//	public RegistrationResult unRegister(String id)
//	{
//		String statusMessage = "Unregister Succesfull of id=" + id;
//		RegistrationResultStatusEnum status = RegistrationResultStatusEnum.SUCCESS;
//		RegistrationResult res = new RegistrationResult();
//		res.setStatus(status);
//		res.setStatusMessage(statusMessage);
//		return res;
//	}
	
}
