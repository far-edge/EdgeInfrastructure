/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.models.LiveDataSet;
import eu.faredge.edgeInfrastructure.registry.models.di.DI;
import eu.faredge.edgeInfrastructure.registry.models.dk.DK;
import eu.faredge.edgeInfrastructure.registry.models.dsd.DSD;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import eu.faredge.edgeInfrastructure.registry.models.dsm.Parameter;
import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
//import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;
import eu.sensap.farEdge.dataRoutingClient.registry.RegistryClient;
import eu.sensap.farEdge.dataRoutingClient.messageBus.KafkaJavaProducer;


/**
 * This class provides the data route client for data producers.
 * Implements the MessageBus and the Register Interfaces 
 * Provides the following operations <br/>
 * 	1. register operation:   registers to the registry 
 * 	2. unRegister operation: unregisters from the registry
 * 	3. isRegistered operation: Gets the registration status
 * 	4. publish method: publishes (in a sync way) a message to Message bus
 * 	5. publishAsync method: publishes (in an Async way) a message to Message bus
 *
 */
public class DataRouteClient 
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Credentials credentials;					//Required credentials for registration
	private static ConfigurationEnv configurationEnv = new ConfigurationEnv();	//Required environmental parameters for connecting to Registry and Message bus
	private String registryUri;
	private String edgeUri;
	private String edgeName="edgeGateway1";
	
//	private String macAddress;
	private DSM new_dsm;
	private DSD new_dsd;
	private DK new_dk;
	private DI new_di;
	
	private RegistryClient registryClient;				//Client for registry
	private KafkaJavaProducer kafkaClient;				//Client for Message bus
	
		
	public DataRouteClient ()
	{	
		log.debug("Init and configure dataroute client props");
		this.initDataRouteClient();
		registryClient = new RegistryClient(this.getRegistryUri());	
		log.debug("registry client initialized");
	}
	
	private void initDataRouteClient()
	{
		log.debug("Init and configure kafka props");
		this.initKafkaProps();
		log.debug("Init and configure registry props");
		this.initRegistryProps();
		//TODO probably remove from here after registration
		kafkaClient = new KafkaJavaProducer(configurationEnv);
	}

	//register when final dsm is known
	public RegistrationResult registerDevice(DSM dsm, Credentials credentials)
	{
		log.debug("--+Request for registering Known data SOURCE with id = " + dsm.getId());
		
		this.setCredentials(credentials);
		this.createDK();
		this.createDI();
		this.createDSD();
		this.createDSM();
//		this.setDsm(dsm);
		
		//Call registry client	to register	
		RegistrationResult result = registryClient.registerDevice(this.getDsm(), this.getCredentials());
		
		log.debug("Response of registration has status=" + result.getStatus() + " and dsmid==" + result.getBody());
		
		return result;
	}
	
	// registration from old dsm, old dsd, old dk and credentials. This method is used if final dsm is not known
	public RegistrationResult registerDevice(DSM old_dsm,DSD old_dsd,DK old_dk, Credentials credentials)
	{
		log.debug("--+ Request for registering data SOURCE with id = " + old_dsm.getId());
		// Store local variables
		this.setCredentials(credentials);
		//create and store
		this.createDK(old_dk);
		this.createDI();
		this.createDSD(old_dsd);
		this.createDSM(old_dsm);
		
		storeToModelRepository(this.new_dk, this.new_di, this.new_dsd);
		
		
		//Call registry client	to register	
		RegistrationResult result = registryClient.registerDevice(this.getDsm(), this.getCredentials());
		
		// update dsm with id
		this.new_dsm.setId(result.getBody());

		log.debug("Response of registration has status=" + result.getStatus() + " and dsmid==" + result.getBody()); 
		
		return result;
	}
	
	private void storeToModelRepository(DK new_dk2, DI new_di2, DSD new_dsd2) {
		// TODO connect with model repository		
	}

	// TODO registration without credentials. Probable with default credentials
	public RegistrationResult registerDevice (DSM dsm)
	{
		return null;		
	}

	public RegistrationResult unRegisterDevice()
	{
		log.debug("Request for unregister DSM with id=" + this.getDsm().getId());

		// Call registry client to unregister and close kafka connection 
		RegistrationResult result = registryClient.unRegisterDevice(this.getDsm().getId(), this.getCredentials());
		kafkaClient.close();
		
		log.debug("Unregister returned status=" + result.getStatus() );
				
		return result ;
	}
	
	
	public boolean isRegistered()
	{		
		// Call registry client to check status and return status		
		return  registryClient.isRegistered(this.getDsm(), this.getCredentials());
	}
	
	// probably we do not need topic here
	public boolean publish(String message,String topic) 
	{
		log.debug("    +-Request for publishing to the topic:" + topic + " message ="+message);
		//trying to correct livedata
		
		String new_message = updateLiveData(message);
		try {
			//change kafka topic to be the same with dsm_id. We cut dsm:// because contains characters are invalid for Kafka topic
			//String new_topic= new_dsm.getId();
			//new_topic = new_topic.replace("dsm://", "");
			
			/// different topic from DSM
			String new_topic =extractTopic();
			
			kafkaClient.runSyncProducer(new_message, new_topic);
			log.debug("    +-message published!!!" );
			return true;
		} catch (Exception e) {
			// TODO see catch block
			e.printStackTrace();
			log.debug("    +-message did not published");
			return false;
		}
	}
	


	private String updateLiveData(String message) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		LiveDataSet liveData = new LiveDataSet();
		String jsonInString="";
		
		try {
			liveData = mapper.readValue(message, LiveDataSet.class);
			liveData.setDataSourceManifestReferenceID(this.new_dsm.getId());
			liveData.getObservation().get(0).setDataKindReferenceID(this.new_dk.getId());
			
			jsonInString = mapper.writeValueAsString(liveData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug("live data did not updated!!!");
			
		}
		return jsonInString;
	}

	public void publishOne(String message,String topic)
	{
		configurationEnv.setTopic(topic);
//		Thread.currentThread().setContextClassLoader(null);
//		boolean status=false;
		try
		{
			kafkaClient.runSyncProducer(configurationEnv, message);
//            KafkaJavaProducer producer = new KafkaJavaProducer();

//            producer.runSyncProducer(configurationEnv.getTopic(), message, configurationEnv.getKafkaProps());
            
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }	
	}

	public void publishAsync(String message)
	{
		// TODO see async publishing
	}
	
	
	
	//create new dsm from EDGE template and the previous one
	private void createDSM()
	{		
		new_dsm = DataRouteClient.readJSONFile("new_dsm", DSM.class);
	}
	
	//create new dsm from EDGE template and the previous one
	private void createDSM(DSM old_dsm)
	{		
		new_dsm = DataRouteClient.readJSONFile("new_dsm", DSM.class);
		
//		TODO delete this!!!!!!
		//////// fake id 
		new_dsm.setId(old_dsm.getId());
		//////// fake id

		String uri = this.edgeUri.replace("sys://", "dsm://") + old_dsm.getUri().replace("dsm://", "/");
		new_dsm.setUri(uri);
		new_dsm.setDataSourceDefinitionReferenceID(new_dsd.getId());
		new_dsm.setMacAddress(old_dsm.getMacAddress());
			
		//find which parameter is the topic
		List<Parameter> params=old_dsm.getDataSourceDefinitionInterfaceParameters().getParameter();
		int count = params.size();
		for (int i=0;i<count;i++)
		{
			if (params.get(i).getKey().equals("topic"))
			{
				params.get(i).getValue().replace('/', '-');
				new_dsm.getDataSourceDefinitionInterfaceParameters().getParameter().add(params.get(i));
				
			}
		}
		
		
	}

	//load the dsd from EDGE template directly
	private void createDSD()
	{
		new_dsd = DataRouteClient.readJSONFile("new_dsd", DSD.class);		
	}
	
	//create new dsd from EDGE template and the previous one
	private void createDSD(DSD old_dsd)
	{
		new_dsd = DataRouteClient.readJSONFile("new_dsd", DSD.class);
		new_dsd.setDataInterfaceReferenceID(new_di.getId());
		new_dsd.getDataKindReferenceIDs().getDataKindReferenceID().clear();
		new_dsd.getDataKindReferenceIDs().getDataKindReferenceID().add(new_dk.getId());
		//create new id based on URI
//		String id = this.edgeUri.replace("sys://", "dsd://") + old_dsd.getId().replace("dsd://", "/");
		//create new id based on previous one
		String id = old_dsd.getId() + "-" + this.edgeName;
		new_dsd.setId(id);
	}

	//create new di from EDGE template 
	private void createDI()
	{
		//load template
		this.new_di = DataRouteClient.readJSONFile("new_di",DI.class);		
	}
	

	//load new dk from EDGE template directly
	private void createDK()
	{
		//load template. We have the same modelType and format for every dk in dataroute liveData and JSON 
		this.new_dk = DataRouteClient.readJSONFile("new_dk", DK.class);		
	}
	
	//create new dk from EDGE template and old dk
	private void createDK(DK old_dk)
	{
		//load template. We have the same modelType and format for every dk in dataroute liveData and JSON 
		this.new_dk = DataRouteClient.readJSONFile("new_dk", DK.class);
		// quantity kind is the same with previous
		this.new_dk.setQuantityKind(old_dk.getQuantityKind());
		// TODO is the description the same with previous???
		this.new_dk.setDescription(old_dk.getDescription());
//		log.debug("template new_dk.id=" + new_dk.getId());
		//generate new id based on qk enchanced with format and modelType
		String id = old_dk.getId() + "-" + this.edgeName;
		//dk://" + new_dk.getModelType() + "/" + old_dk.getQuantityKind().replace("qk://", "")+ "/" + new_dk.getFormat();
//		String id = old_dk.getId() + "-" + new_dk.getModelType();
		this.new_dk.setId(id);
	}
	
	private void initKafkaProps()
	{
		log.debug("Initializing kafka props");
		System.out.println("Initializing kafka props");

		Properties prop = new Properties();
		try
		{
			// load a properties file from class path, inside static method
			prop.load(DataRouteClient.class.getClassLoader().getResourceAsStream("kafka.properties"));
			DSM dsm = DataRouteClient.readJSONFile("new_dsm", DSM.class);
			String host="", port="";
			for (int i=0;i<dsm.getDataSourceDefinitionInterfaceParameters().getParameter().size();i++)
			{
				Parameter param= dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i);
				if (param.getKey().equals("host"))
				{
					host=param.getValue();
					System.out.println("host=" + host);
				}
				else if (param.getKey().equals("port"))
				{
					port = param.getValue();
				}				
			}
			prop.setProperty("bootstrap.servers",host+":"+port );
			//prop.put("bootstrap.servers", host+":"+port);
			configurationEnv.setKafkaProps(prop);
			
			log.debug("DONE!");
//			System.out.println("DONE");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			log.debug("ERROR");
			System.out.println("ERROR");
		}
	}
		
	private void initRegistryProps()
	{
		log.debug("Initializing registry props");
		
		Properties prop = new Properties();
		
		try
		{
			// load a properties file from class path, inside static method
			prop.load(DataRouteClient.class.getClassLoader().getResourceAsStream("registry.properties"));
			registryUri = prop.getProperty("registry");
			edgeUri = prop.getProperty("edge.uri");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private String extractTopic()
	{
		int size = new_dsm.getDataSourceDefinitionInterfaceParameters().getParameter().size();
		String topic="";
		
		//first we look at dsm parameters. if we have topic param the ze extract topic from there
		for(int i=0;i<size;i++)
		{
			Parameter param = this.new_dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i);
			if (param.getKey().equals("topic"))
			{
				topic=param.getValue();
			}			
		}
		// if no topic exists then we create topic with the dsm_id 
		if (topic=="")
		{
			topic= new_dsm.getId();
			topic = topic.replace("dsm://", "");
		}
		log.debug("new kafka topic=" + topic);
		return topic;
	}
	
	public static <T> T readJSONFile (String fileName, Class<T> a)
	{
        try
        {
        	ObjectMapper mapper = new ObjectMapper();
        	return  mapper.readValue(DataRouteClient.class.getClassLoader().getResourceAsStream(fileName), a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
	
//	private DataSourceDefinitionInterfaceParameters createDsdIp ()
//	{
//		DataSourceDefinitionInterfaceParameters dsdip = new DataSourceDefinitionInterfaceParameters();
////		Set<Parameter> paramSet = new HashSet<Parameter>();
//		List<Parameter> paramSet = new ArrayList<Parameter>();
//		
//		
//		dsdip.setDescr(configurationEnv.getTopic());
//		
//		Parameter top = new Parameter();
//		top.setKey("topic");
//		top.setValue(configurationEnv.getTopic());
//
//		paramSet.add(top);
//		
//		Set<String> keys = configurationEnv.getKafkaProps().stringPropertyNames();
//	    for (String key : keys) {
//	    	Parameter e = new Parameter();
//	    	e.setKey(key);
//	    	e.setValue(configurationEnv.getKafkaProps().getProperty(key));
//	    	paramSet.add(e);
//	    	System.out.println(key + " : " + configurationEnv.getKafkaProps().getProperty(key));
//	    }
//
//		dsdip.setParameter(paramSet);
//	
//		return dsdip;
//	}
	
	
	
	
	//Getters and setters

	public Credentials getCredentials()
	{
		return credentials;
	}
	
	public void setCredentials(Credentials credentials)
	{
		this.credentials = credentials;
	}
	
	public ConfigurationEnv getConfigurationEnv()
	{
		return configurationEnv;
	}

	public void setConfigurationEnv(ConfigurationEnv configurationEnv)
	{
		DataRouteClient.configurationEnv = configurationEnv;
	}
	
	public String getRegistryUri()
	{
		return registryUri;
	}
	public void setRegistryUri(String registryUri)
	{
		this.registryUri = registryUri;
	}
	
	public String getEdgeUri()
	{
		return edgeUri;
	}
	public void setEdgeUri(String edgeUri)
	{
		this.edgeUri = edgeUri;
	}

	public DSM getDsm()
	{
		return new_dsm;
	}
	public void setDsm(DSM dsm)
	{
		this.new_dsm = dsm;
	}
	
	public RegistryClient getRegistryClient()
	{
		return registryClient;
	}
	
	public void setRegistryClient(RegistryClient registry)
	{
		this.registryClient = registry;
	}
	
	public KafkaJavaProducer getKafkaClient()
	{
		return kafkaClient;
	}
	
	public void setKafkaClient(KafkaJavaProducer kafkaClient)
	{
		this.kafkaClient = kafkaClient;
	}
}
