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
import eu.faredge.edgeInfrastructure.registry.models.DCM;
import eu.faredge.edgeInfrastructure.registry.models.DSM;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/registry")
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class ConsumerRegistrationController implements ConsumerRegistrationInterface
{
	private BusinessImp bimpl = new BusinessImp();

	@RequestMapping(value = "ConsumerRegistration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult ConsumerRegistration(@RequestBody DCM manifest)// , Object credentials)
	{		
		//TODO delete these 3 lines
		System.out.println("Controller:ConsumerRegistration==> dcmuri= " + manifest.getUri());
		System.out.println("Controller:ConsumerRegistration==> dcmMac= " + manifest.getMacAddress());
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
			
			// If logged-in check authorization. Authorization through Ledger Client 
			authorized = bimpl.authorizeToLedger(new LedgerCredentials(user, password));
			if (!authorized)
			{
				res.setStatus(RegistrationResultStatusEnum.DENIED);
				res.setStatusMessage("Authorization Failure"); 
				return res;
			}

			//If authorized and authenticated creates the data source manifest to the registry-repo
			DCM registeredDcm = bimpl.createDcm(manifest);
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
			res.setStatusMessage("Succes uri=" + registeredDcm.getUri());
			System.out.println("Controller:ConsumerRegistration==> registered!=" + registeredDcm.getUri());
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
	public RegistrationResult ConsumerRegistration(String dsd) // , Object credentials)
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
		
		// If logged-in check authorization. Authorization through Ledger Client 
		authorized = bimpl.authorizeToLedger(new LedgerCredentials(user, password));
		if (!authorized)
		{
			res.setStatus(RegistrationResultStatusEnum.DENIED);
			res.setStatusMessage("Authorization Failure"); 
			return res;
		}
		
		// if authorized and authenticated calls registry-repo to delete the DCM 
		deleteStatus = bimpl.deleteDcm(dsd);
		if (!deleteStatus)
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Error deleting dcm or not exists");
			return res;
		}
		
		String statusMessage = "Unregister Succesfull of id=" + dsd;
		RegistrationResultStatusEnum status = RegistrationResultStatusEnum.SUCCESS;
		res.setStatus(status);
		res.setStatusMessage(statusMessage);
		System.out.println("Controller:ConsumerRegistration==> unregistered! id=" + dsd);
		return res;
	}
	
	@RequestMapping(value="compatibleDSM", method = RequestMethod.GET)
	public ArrayList<DSM> getCompatibleDSM(String id)
	{		
//		System.out.println("Controller:getCompatibleDSM==> dcm id="+id);
		//check if id is null
		if (id==null)
		{
			System.out.println("Controller:getCompatibleDSM==> id does not exist =");
			return null;
		}
		
		ArrayList<DSM> dsmList = new ArrayList<DSM>();
		
		// Call registry-repo to retrieve compatible DSM 
		dsmList = bimpl.getCompatibleDsM(id);
		
		if ((dsmList==null)||(dsmList.size()==0))
		{
			System.out.println("Controller:getCompatibleDSM==> return no DSM");
			return null;
		}
		
		System.out.println("Controller:getCompatibleDSM==> dsmList returned size="+ dsmList.size());
		return dsmList;
	}
	
	@RequestMapping(value="accessToDSM",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DataChannelDescriptor getAccessToDSM(@RequestBody DataChannelDescriptor dcd)
	{
		DataChannelDescriptor finalDcd =new DataChannelDescriptor();
		//String user="george";
		//String password="george123";
		
		boolean loggin = true;								//local authentication
		boolean authorized = false;							//Ledger authorization
		boolean configure=false;
		RegistrationResult res = new RegistrationResult();
		
		//check if dcd, consumerId or datasourceId is null
		if (dcd==null)
		{
			return null;
		}
		if ((dcd.getDataConsumerManifest().getUri()==null)||(dcd.getDataSourceManifest().getUri()==null))
		{
			System.out.println("Controller:getAccessToDSM==> ids do not exist ");
			return null;
		}

		// Login to service based on credentials
		if (!loggin)
		{
			res.setStatus(RegistrationResultStatusEnum.LOGINFAILURE);
			res.setStatusMessage("Could not login"); // or other message coming from Authentication system
			System.out.println("Controller:getAccessToDSM==> failed to login ");
			return null;
		}
		
		// If logged-in check access authorization. Authorization through Ledger Client 
		authorized = bimpl.authorizeAccesstoDSM(dcd.getDataConsumerManifest().getUri(), dcd.getDataSourceManifest().getUri());
		if (!authorized)
		{
			res.setStatus(RegistrationResultStatusEnum.DENIED);
			res.setStatusMessage("Authorization Failure"); 
			System.out.println("Controller:getAccessToDSM==> failed to authorize");
			return null;
		}
		
		// if authorized and authenticated calls registry-repo to create the DCD
		finalDcd= bimpl.ctreateDcD(dcd);
		if(finalDcd==null)
		{
			res.setStatus(RegistrationResultStatusEnum.FAIL);
			res.setStatusMessage("Fail creating DCD");
			System.out.println("Controller:getAccessToDSM==> failed dreating DCD ");
			return null;
		}
		
		// request access to data source
		configure = bimpl.configureAccess(finalDcd.getDataConsumerManifest().getMacAddress(), finalDcd.getDataSourceManifest().getMacAddress());
		if (configure==false)
		{
			res.setStatus(RegistrationResultStatusEnum.DENIED);
			res.setStatusMessage("Configuration Failure");
			System.out.println("Controller:getAccessToDSM==> failed to configure access");
			return null;
		}
		
		res.setStatus(RegistrationResultStatusEnum.SUCCESS);
		res.setStatusMessage("Access granted"); 
		System.out.println("Controller:getAccessToDSM==> Access granted!!!! ");
		return finalDcd;
	}

}