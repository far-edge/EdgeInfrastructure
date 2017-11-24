package eu.faredge.edgeInfrastructure.deviceRegistry.business;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

import eu.faredge.edgeInfrastructure.deviceRegistry.RegistryConfiguration;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.DataChannelDescriptor;
import eu.faredge.edgeInfrastructure.registry.models.DataConsumerManifest;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceDefinition;
import eu.faredge.edgeInfrastructure.registry.models.DataSourceManifest;
import eu.faredge.edgeInfrastructure.registry.models.DataTopic;


public class BusinessImp {
	private RegistryRepoClient registryRepoClient;
	private Properties props;
	
	public BusinessImp() {
		
		props = new Properties();
		registryRepoClient = new RegistryRepoClient();
		// TODO change try-catch with throws exception
		try {
			props.load(RegistryConfiguration.class.getClassLoader().getResourceAsStream("RegistryRepoClient.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DataSourceManifest createDsm(DataSourceManifest dsm) {

		String dsmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				+ "/" + props.getProperty("root") + "/" + props.getProperty("DSM");
		
		ClientResponse createDsmResponse = registryRepoClient.postResource(dsmEndpoint, dsm);
		int dsmStatus = createDsmResponse.getStatus();
		//TODO check if exists return error
		if ((dsmStatus == 201) || (dsmStatus == 200)) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				String createDsmResponseString = createDsmResponse.getEntity(String.class);
				DataSourceManifest newDsm = new DataSourceManifest();
				newDsm = mapper.readValue(createDsmResponseString, newDsm.getClass());
				System.out.println("BusinessImp:createDSM==>newDsm.id=" + newDsm.getDataSourceManifestId());
				return newDsm;
			} catch (ClientHandlerException | UniformInterfaceException | IOException e) {
				System.out.println("BusinessImp:createDSM==>newDsm retieval failed");
				e.printStackTrace();
				createDsmResponse.close();
				return null;
			}

		} else {
			System.out.println("BusinessImp:createDSM==>Datasource Manifest creation failed " + createDsmResponse.getStatus());
			return null;
		}

	}

	public DataConsumerManifest createDcm(DataConsumerManifest dcm) {

		String dsmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				+ "/" + props.getProperty("root") + "/" + props.getProperty("DCM");

		ClientResponse createDcmResponse = registryRepoClient.postResource(dsmEndpoint, dcm);
		int dcmStatus = createDcmResponse.getStatus();
		//TODO check if exists return error
		if ((dcmStatus == 201) || (dcmStatus == 200)) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				String createDsmResponseString = createDcmResponse.getEntity(String.class);
				DataConsumerManifest newDcm = new DataConsumerManifest();
				newDcm = mapper.readValue(createDsmResponseString, newDcm.getClass());
				System.out.println("BusinessImp:createDSM==>newDsm.id=" + newDcm.getDataConsumerManifestId());
				return newDcm;
			} catch (ClientHandlerException | UniformInterfaceException | IOException e) {
				System.out.println("BusinessImp:createDSM==>newDsm retieval failed");
				e.printStackTrace();
				createDcmResponse.close();
				return null;
			}

		} else {
			System.out.println(
					"BusinessImp:createDCM==>Data consumer Manifest creation failed " + createDcmResponse.getStatus());
			return null;
		}
	}

	public DataChannelDescriptor ctreateDcD(DataChannelDescriptor dcd) {
		String dsmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				+ "/" + props.getProperty("root") + "/" + props.getProperty("DCD");
		ClientResponse createDcdResponse = registryRepoClient.postResource(dsmEndpoint, dcd);
		int dcdStatus = createDcdResponse.getStatus();
		//TODO check if exists return error
		if ((dcdStatus == 201) || (dcdStatus == 200)) {

			try {
				ObjectMapper mapper = new ObjectMapper();
				String createDsdResponseString = createDcdResponse.getEntity(String.class);
				DataChannelDescriptor newDcd = new DataChannelDescriptor();
				newDcd = mapper.readValue(createDsdResponseString, newDcd.getClass());
				System.out.println("BusinessImp:createDSM==>newDsm.id=" + newDcd.getDataChannelDescriptorId());
				return newDcd;
			} catch (ClientHandlerException | UniformInterfaceException | IOException e) {
				System.out.println("BusinessImp:createDSM==>newDsm retieval failed");
				e.printStackTrace();
				createDcdResponse.close();
				return null;
			}

		} else {
			System.out.println(
					"BusinessImp:createDCM==>Data consumer Manifest creation failed " + createDcdResponse.getStatus());
			return null;
		}

	}

	public boolean deleteDsm (String dsmUri)
	{
		boolean result=false;
		RegistrationResult res = new RegistrationResult();
		
		String dsmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
		+ "/" + props.getProperty("root") + "/" + props.getProperty("DSM") + "/uri";
		
		System.out.println("BusinessImp:deleteDSM==> dsmEndpoint= " + dsmEndpoint);
		System.out.println("BusinessImp:deleteDSM==> dsmUri= " + dsmUri);
		
		ClientResponse deleteDsmResponse = registryRepoClient.deleteResource(dsmEndpoint, dsmUri);
		
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
		
		String dcmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
		+ "/" + props.getProperty("root") + "/" + props.getProperty("DCM") + "/uri";
		
		ClientResponse deleteDcmResponse = registryRepoClient.deleteResource(dcmEndpoint, dcmUri);
		
		int dcmStatus = deleteDcmResponse.getStatus();
		
		if ((dcmStatus==400)||(dcmStatus==404))
		{
			System.out.println("BusinessImp:deleteDSM==>Datasource Manifest deletion failed or not exists " + deleteDcmResponse.getStatus());
			res.setStatus(RegistrationResultStatusEnum.FAIL);
			res.setStatusMessage("Failed to delete DSM" + dcmStatus);
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
				result= true;
			}
			catch (ClientHandlerException | UniformInterfaceException e)
			{
				System.out.println("BusinessImp:deleteDSM==>deleteDsm retieval failed");
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
	
	public DataSourceManifest getCompatibleDsM(DataSourceDefinition dsd)
	{
		//TODO fill getCOmpatible DSM
		return null;
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

	public ArrayList<DataSourceDefinition> getDataSourceDefinition ()
	{
		ObjectMapper mapper = new ObjectMapper();
		String finalUri ="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DSD");
		ClientResponse response = registryRepoClient.getResource(finalUri);

		System.out.println("BussinesImpl:getDataTopics:response=" + response.toString());
		
		if (response.getStatus()==200)
		{
			String output = response.getEntity(String.class);
			ArrayList<DataSourceDefinition> dt = new ArrayList<DataSourceDefinition>();
			try
			{
				dt = mapper.readValue(output, dt.getClass());
				return dt;
			} catch (IOException e) {
				System.out.println("Error retrieving Data Source Definitions");
				e.printStackTrace();
			}			
		}				
		return null;
	}
	
	public ArrayList<DataTopic> getDataTopics ()
	{
		ObjectMapper mapper = new ObjectMapper();
		String finalUri ="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DT");
		ClientResponse response = registryRepoClient.getResource(finalUri);

		System.out.println("BussinesImpl:getDataTopics:response=" + response.toString());
		
		if (response.getStatus()==200)
		{
			String output = response.getEntity(String.class);
			ArrayList<DataTopic> dt = new ArrayList<DataTopic>();
			try
			{
				dt = mapper.readValue(output, dt.getClass());
				return dt;
			} catch (IOException e) {
				System.out.println("Error retrieving Data Topics");				
				e.printStackTrace();
			}
			
		}				
		return null;
	}

}
