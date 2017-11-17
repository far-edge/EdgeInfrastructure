package eu.faredge.edgeInfrastructure.deviceRegistry.business;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

import eu.faredge.edgeInfrastructure.deviceRegistry.RegistryConfiguration;
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
	
//	public DataSourceManifest createDSM(DataSourceManifest dsm)
//	{
//		String dsmUri,dsdUri;
//		String dsmId,dsdId;
//		String dsmEndpoint="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DSM");
//		String dsdEndPoint="http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port") + "/" + props.getProperty("root") + "/" + props.getProperty("DSD");
//		DataSourceDefinition dsd = dsm.getDataSourceDefinition();
////		ObjectMapper mp = new ObjectMapper();
////		mp.setSerializationInclusion(Include.NON_NULL);
////		try {
////			String dsdString = mp.writeValueAsString(dsd);
////			System.out.println("BusinessImp:createDSM==>Datasource Definition dsd=" + dsdString);
////		} catch (JsonProcessingException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
//		//insert dsd
//		ClientResponse createDsdResponse = registryRepoClient.postResource(dsdEndPoint, dsm.getDataSourceDefinition());
//		
//
//		// if status =201 created if status=200 exists if status 400 get error otherwise exception
//		int st = createDsdResponse.getStatus();
//		if ((st==200)||(st==201))
//		{			
//			
//			try {
//				ObjectMapper mapper = new ObjectMapper();
//				String createDsdResponseString = createDsdResponse.getEntity(String.class);
//				
//				DataSourceDefinition newdsd = new DataSourceDefinition();
//				newdsd= mapper.readValue(createDsdResponseString,newdsd.getClass());
//				System.out.println("BusinessImp:createDSM==>newdsd.id=" + newdsd.getDataSourceDefinitionId());
//				dsm.setDataSourceDefinition(newdsd);
//
//				
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				System.out.println("BusinessImp:createDSM==>newdsd retieval failed");
//
//				return null;
//			}
//			
//			ClientResponse createDsmResponse = registryRepoClient.postResource(dsmEndpoint, dsm);
//			int dsmStatus = createDsmResponse.getStatus();
//			if ((dsmStatus==201)||(dsmStatus==200))
//			{
//								
//				try {
//					ObjectMapper mapper = new ObjectMapper();
//					String createDsmResponseString = createDsmResponse.getEntity(String.class);
//					DataSourceManifest newDsm = new DataSourceManifest();
//					newDsm= mapper.readValue(createDsmResponseString,newDsm.getClass());
//					System.out.println("BusinessImp:createDSM==>newDsm.id=" + newDsm.getDataSourceManifestId());
//					return newDsm;
//				} catch (ClientHandlerException | UniformInterfaceException | IOException e) {
//					// TODO Auto-generated catch block
//					System.out.println("BusinessImp:createDSM==>newDsm retieval failed");
//					e.printStackTrace();	
//					createDsmResponse.close();
//					return null;
//					
//				}
//				
//			}
//			else
//			{
//				System.out.println("BusinessImp:createDSM==>Datasource Manifest creation failed " + createDsmResponse.getStatus());
//
//				return null;
//			}
//			
//			
//			
//			
//		}
//		else
//		{
//			System.out.println("BusinessImp:createDSM==>Datasource Definition cration failed" + createDsdResponse.getStatus());
//
//		}
//
//
//		return null;		
//	}

	public DataSourceManifest createDSM(DataSourceManifest dsm) {

		String dsmEndpoint = "http://" + props.getProperty("default_host") + ":" + props.getProperty("default_port")
				+ "/" + props.getProperty("root") + "/" + props.getProperty("DSM");
		
		ClientResponse createDsmResponse = registryRepoClient.postResource(dsmEndpoint, dsm);
		int dsmStatus = createDsmResponse.getStatus();
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
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}
			
		}				
		return null;
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
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}
			
		}				
		return null;
	}


}
