package eu.faredge.edgeInfrastructure.deviceRegistry.business;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

import eu.faredge.edgeInfrastructure.deviceRegistry.RegistryConfiguration;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.dcd.DCD;
import eu.faredge.edgeInfrastructure.registry.models.dcm.DCM;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;


public class BusinessImp
{
	private RegistryRepoClient registryRepoClient;
	private Properties props;
	private String registryRepoUrl =  "http://" + RegistryConfiguration.variables.get("uri");
	
	public BusinessImp()
	{
		
		props = new Properties();
		registryRepoClient = new RegistryRepoClient();
		// TODO change try-catch with throws exception
		try
		{
			props.load(RegistryConfiguration.class.getClassLoader().getResourceAsStream("RegistryRepoClient.properties"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public DSM createDsm(DSM dsm)
	{
		
		
		String dsmEndpoint = this.registryRepoUrl +"/" + props.getProperty("DSM")+ "/";
		
//		System.out.println("uri for variables = " + this.registryRepoUrl + ". dsmEndpiont = " + dsmEndpoint);
		
		String dsmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				+"/" + props.getProperty("DSM")+ "/";
		System.out.println("dsmEndpoint = " + dsmEndpoint);
		
		ClientResponse createDsmResponse = registryRepoClient.postResource(dsmEndpoint, dsm);
		int dsmStatus = createDsmResponse.getStatus();
		//TODO check if exists return error
		if ((dsmStatus == 201) || (dsmStatus == 200))
		{
			try
			{
				ObjectMapper mapper = new ObjectMapper();
				String createDsmResponseString = createDsmResponse.getEntity(String.class);
				DSM newDsm = new DSM();
				newDsm = mapper.readValue(createDsmResponseString, newDsm.getClass());
				System.out.println("BusinessImp:createDSM==>newDsm.uri=" + newDsm.getUri());
				return newDsm;
			}
			catch (ClientHandlerException | UniformInterfaceException | IOException e)
			{
				System.out.println("BusinessImp:createDSM==>newDsm retieval failed");
				e.printStackTrace();
				createDsmResponse.close();
				return null;
			}
		}
		else
		{
			System.out.println("REgistry:BusinessImp:createDSM==>Datasource Manifest creation failed with status:" + createDsmResponse.getStatus());
			return null;
		}
	}

	public DCM createDcm(DCM dcm)
	{
		
		String dsmEndpoint = this.registryRepoUrl	 + "/" + props.getProperty("DCM") + "/";

		String dsmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				 + "/" + props.getProperty("DCM") + "/";
	
		System.out.println("BusinessImp:createDCM==> dcm=" + dcm.getMacAddress());
		System.out.println("BusinessImp:createDCM==> uend point= " + dsmEndpoint);
		ClientResponse createDcmResponse = registryRepoClient.postResource(dsmEndpoint, dcm);
		
		int dcmStatus = createDcmResponse.getStatus();
		//TODO check if exists return error
		if ((dcmStatus == 201) || (dcmStatus == 200))
		{
			try
			{
				ObjectMapper mapper = new ObjectMapper();
				String createDsmResponseString = createDcmResponse.getEntity(String.class);
				DCM newDcm = new DCM();
				newDcm = mapper.readValue(createDsmResponseString, newDcm.getClass());
				System.out.println("BusinessImp:createDCM==>newDsm.id=" + newDcm.getUri());
				return newDcm;
			}
			catch (ClientHandlerException | UniformInterfaceException | IOException e)
			{
				System.out.println("BusinessImp:createDCM==>newDsm retieval failed");
				e.printStackTrace();
				createDcmResponse.close();
				return null;
			}

		}
		else
		{
			System.out.println(
					"BusinessImp:createDCM==>Data consumer Manifest creation failed " + createDcmResponse.getStatus());
			return null;
		}
	}

	public DCD ctreateDcD(DCD dcd)
	{
		String dsmEndpoint = this.registryRepoUrl	 + "/" + props.getProperty("DCD") + "/";
		
		String dsmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				 + "/" + props.getProperty("DCD") + "/";
		
		//check if dcd is null
		if (dcd==null)
		{
			System.out.println("BusinessImp:createDCD==> dcd is null");
			return null;
		}
		// dcd request
		ClientResponse createDcdResponse = registryRepoClient.postResource(dsmEndpoint, dcd);
		int dcdStatus = createDcdResponse.getStatus();
		
		// if not saved
		if (dcdStatus!= 201)
		{
			System.out.println("BusinessImp:createDCD==>create newDcd failed status=" +createDcdResponse.getStatus());
			return null;
		}
		
		// if saved retrieve dcd
		if (dcdStatus == 201)
		{

			try
			{
				ObjectMapper mapper = new ObjectMapper();
				String createDsdResponseString = createDcdResponse.getEntity(String.class);
				DCD newDcd = new DCD();
				newDcd = mapper.readValue(createDsdResponseString, newDcd.getClass());
				System.out.println("BusinessImp:createDCD==>newDcd.id=" + newDcd.getUri());
				return newDcd;
			}
			catch (ClientHandlerException | UniformInterfaceException | IOException e)
			{
				System.out.println("BusinessImp:createDCD==>newDcd retieval failed");
				e.printStackTrace();
				createDcdResponse.close();
				return null;
			}

		}
		else
		{
			System.out.println(
					"BusinessImp:createDCM==>DCD creation failed " + createDcdResponse.getStatus());
			return null;
		}

	}

	public boolean deleteDsm (String id)
	{
		boolean result=false;
		RegistrationResult res = new RegistrationResult();
		
		String dsmEndpoint = this.registryRepoUrl	 + "/" + props.getProperty("DSM") + "/id/";
		
		String dsmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
		 + "/" + props.getProperty("DSM") + "/id/";

		
		System.out.println("Registry:BusinessImp:deleteDSM==> dsmEndpoint= " + dsmEndpoint);
		System.out.println("Registry:BusinessImp:deleteDSM==> dsmId= " + id);
		
		ClientResponse deleteDsmResponse = registryRepoClient.deleteResource(dsmEndpoint, id);
		
		int dsmStatus = deleteDsmResponse.getStatus();
		
		if ((dsmStatus==400)||(dsmStatus==404))
		{
			System.out.println("BusinessImp:deleteDSM==>Datasource Manifest deletion failed or not exists " + dsmStatus);
			res.setStatus(RegistrationResultStatusEnum.FAIL);
			res.setStatusMessage("Failed to delete DSM" + dsmStatus);
			deleteDsmResponse.close();
			return false;
		}
		
		if (dsmStatus == 200)
		{
			try
			{				
				String createDsmResponseString = deleteDsmResponse.getEntity(String.class);
				res.setStatus(RegistrationResultStatusEnum.SUCCESS);
				res.setStatusMessage(createDsmResponseString + " " + dsmStatus);
				result= true;
			}
			catch (ClientHandlerException | UniformInterfaceException e)
			{
				System.out.println("BusinessImp:deleteDSM==>deleteDsm retieval failed");
				e.printStackTrace();
				res.setBody(e.toString());
				res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
				res.setStatusMessage("Failed to parse registry repo response" + dsmStatus);
				result=false;
			}
			finally
			{
				
				deleteDsmResponse.close();
				
			}
			return result;
		}
		else
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("unexcpected response number:" + dsmStatus);
			deleteDsmResponse.close();
			return false;
		}
	}
	
	public boolean deleteDcm (String dcmUri)
	{
		boolean result=false;
		RegistrationResult res = new RegistrationResult();
		
		String dcmEndpoint = this.registryRepoUrl + "/" + props.getProperty("DCM") + "/uri";
		
		String dcmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
		 + "/" + props.getProperty("DCM") + "/uri";
		
		//TODO: check uris
		
		ClientResponse deleteDcmResponse = registryRepoClient.deleteResource(dcmEndpoint, dcmUri);
		
		int dcmStatus = deleteDcmResponse.getStatus();
		
		if ((dcmStatus==400)||(dcmStatus==404))
		{
			System.out.println("BusinessImp:deleteDCM==>Data consumer Manifest deletion failed or not exists " + deleteDcmResponse.getStatus());
			res.setStatus(RegistrationResultStatusEnum.FAIL);
			res.setStatusMessage("Failed to delete DCM" + dcmStatus);
			deleteDcmResponse.close();
			return false;
		}
		
		if (dcmStatus == 200)
		{
			try
			{
				
				String createDsmResponseString = deleteDcmResponse.getEntity(String.class);
				res.setStatus(RegistrationResultStatusEnum.SUCCESS);
				res.setStatusMessage(createDsmResponseString + + dcmStatus);
				System.out.println("BusinessImp:deleteDCM==>deleteDcm status=" + dcmStatus);
				result= true;
			}
			catch (ClientHandlerException | UniformInterfaceException e)
			{
				System.out.println("BusinessImp:deleteDCM==>deleteDcm retieval failed");
				e.printStackTrace();
				res.setBody(e.toString());
				res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
				res.setStatusMessage("Failed to parse registry repo response" + dcmStatus);
				result=false;
			}
			finally
			{
				
				deleteDcmResponse.close();
				
			}
			return result;
		}
		else
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Failed to parse registry repo response" + dcmStatus);
			deleteDcmResponse.close();
			return false;
		}
	}
	
	public ArrayList<DSM> getCompatibleDsM(String dsd)
	{
		RegistrationResult res = new RegistrationResult();
		ObjectMapper mapper = new ObjectMapper();
		String dsmEndpoint = this.registryRepoUrl + "/" + props.getProperty("DSM")+ "/" +props.getProperty("DSD") +"/";
		
		String dsmEndpoint2 = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
		 + "/" + props.getProperty("DSM")+ "/" +props.getProperty("DSD") +"/";

		System.out.println("BussinesImpl:getCompatibleDsM:endpoint=" + dsmEndpoint);
		//TODO Is this necessary?
		if (dsd==null)
		{
			res.setStatus(RegistrationResultStatusEnum.NOTFOUND);
			res.setStatusMessage("There is no list");
			return null;			
		}
		
		ClientResponse response = registryRepoClient.getResource(dsmEndpoint,dsd);
//		System.out.println("BussinesImpl:getCompatibleDsM:response=" + response.toString());
		
		if (response.getStatus()==200)
		{
			String output = response.getEntity(String.class);
//			ArrayList<DataSourceManifest> dsmList = new ArrayList<DataSourceManifest>();
			
			try
			{
				DSM[] dsmTable =mapper.readValue(output,DSM[].class);
				ArrayList<DSM> dsmList = new ArrayList<DSM>();
				dsmList.addAll(Arrays.asList(dsmTable));
				return dsmList; 
			}
			catch (IOException e)
			{
				System.out.println("Error retrieving Data Source Manifests");
				e.printStackTrace();
			}
			
		}		
		return null;
	}

	public boolean authorizeAccesstoDSM (String consumerId, String dataSourceId)
	{
		//TODO: connect with Mauro's Ledger client
		return true;
	}
	
 	public boolean configureAccess (String consumerMac, String producerMac)
	{
		//TODO: connect with Mauro's Configuration service
		return true;
	}
	
	public boolean authorizeToLedger(LedgerCredentials credentials) {

		//TODO: connect with Mauro's Ledger client
		
		
		return true;
	}

	public String createDsmToLedger(DSM manifest) {
		// TODO Auto-generated method stub
		UUID id = UUID.randomUUID();		
		return "dsm://"+id.toString();
	}

/*	//TODO possible to be deleted
	public ArrayList<DataSourceDefinition> getDataSourceDefinition ()
	{
		ObjectMapper mapper = new ObjectMapper();
		String finalUri ="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DSD");
		ClientResponse response = registryRepoClient.getResource(finalUri);

		System.out.println("BussinesImpl:getDataTopics:response=" + response.toString());
		
		if (response.getStatus()==200)
		{
			String output = response.getEntity(String.class);
			try
			{
				DataSourceDefinition[] dt = mapper.readValue(output, DataSourceDefinition[].class);
				ArrayList<DataSourceDefinition> dsdList = new ArrayList<DataSourceDefinition>();
				dsdList.addAll(Arrays.asList(dt));
				return dsdList;
			}
			catch (IOException e)
			{
				System.out.println("Error retrieving Data Source Definitions");
				e.printStackTrace();
			}			
		}				
		return null;
	}
	
	
	//TODO possible to be deleted
	public ArrayList<DataTopic> getDataTopics ()
	{
		ObjectMapper mapper = new ObjectMapper();
		String finalUri ="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DT");
		ClientResponse response = registryRepoClient.getResource(finalUri);

		System.out.println("BussinesImpl:getDataTopics:response=" + response.toString());
		
		if (response.getStatus()==200)
		{
			String output = response.getEntity(String.class);
			try
			{
				DataTopic[] dt = mapper.readValue(output, DataTopic[].class);
				ArrayList<DataTopic> dtList = new ArrayList<DataTopic>();
				dtList.addAll(Arrays.asList(dt));
				return dtList;
			}
			catch (IOException e)
			{
				System.out.println("Error retrieving Data Topics");				
				e.printStackTrace();
			}
			
		}				
		return null;
	}

*/
}
