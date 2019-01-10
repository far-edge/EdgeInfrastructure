package eu.sensap.farEdge.dataRoutingClient.smartFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.LiveDataSet;
import eu.faredge.edgeInfrastructure.registry.models.Observation;
import eu.faredge.edgeInfrastructure.registry.models.di.DI;
import eu.faredge.edgeInfrastructure.registry.models.dk.DK;
import eu.faredge.edgeInfrastructure.registry.models.dsd.DSD;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import eu.sensap.farEdge.dataRoutingClient.impl.DataRouteClient;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;
//import eu.sensap.farEdge.dataRoutingClient.models.RegistrationResult;
import eu.sensap.farEdge.dataRoutingClient.smartFactory.mqttClient.MqttClient;


public class DataSourceClient implements Runnable
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	static BufferedReader in ;  static int quit=0;
	private DSM dsm;
	private DSD dsd;
	private DI di;
	private DK dk;
	@SuppressWarnings("unused")
	private LiveDataSet data;
	private DataRouteClient dataRoute;
	private MqttClient mqttClient;
	private String topic;
		
	public DataSourceClient(DSM dsm, DSD dsd, DI di, DK dk, LiveDataSet data )
	{
			
		log.debug("Init datasource client");
		this.dsm = dsm;
		this.dsd=dsd;
		this.di=di;
		this.dk=dk;
		this.data = data;
		dataRoute = new DataRouteClient();
	}
	
	public DataSourceClient() {
		
	}
	
	public void publish ()
	{
//		log.debug("publish values");
		
		in=new BufferedReader(new InputStreamReader(System.in));
        Thread t1=new Thread(new DataSourceClient());
        t1.start();


		boolean conectionStatus = false;
		boolean registrationStatus = false;
		
		//connect to MQTT
		log.debug("preparing for MQTT connection .....");
		conectionStatus = this.connectMqtt();
		if (conectionStatus==false)
		{
			return;
		}
//		else
//		{
//			log.debug("Connected to MQTT");
//		}
		
		//register the data source
		log.debug("trying to connect to register service........");
		log.debug("---------------------------------------------");
		registrationStatus = this.register();
		if (registrationStatus==false)
		{		
			return;
		}
		
		while (true)
		{
			log.debug("_____________________________________________");
			log.debug("--+ Waiting for values from topic...");
			String message="";			
			try
			{
				
				String value = this.readValues();
				log.debug("  +- value from topic=" + value);
				message = createLiveData(value);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.debug("  +- Error creating live Data message");
				
				continue;
			}			
			log.debug("  --+ Sending message to dataRouteClient for publishing to KAFKA...");
			//dataRoute.publish(message, new_dsm. );
			dataRoute.publish(message, topic.replace('/', '-'));
			
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            if(quit==1)
            {
            	dataRoute.unRegisterDevice();
            	break;
            }            
            log.debug("_____________________________________________");
			
		}
	}
	
	//ok
	private String createLiveData(String value) throws Exception {
//		log.debug("Creating liveData");
		LiveDataSet data = new LiveDataSet();
		UUID id = UUID.randomUUID();			
		data.setId(id.toString());
		data.setMobile(false);
		data.setDataSourceManifestReferenceID(dsm.getId());		
		Observation observation = new Observation();
		observation.setId(UUID.randomUUID().toString());
		
		//TODO set real value for dk
		observation.setDataKindReferenceID("dk");				
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());		
		observation.setTimestamp(timestamp.toString());
		observation.setName(this.topic.replace('/', '-'));
		observation.setValue( NumberFormat.getInstance().parse(value));			
		data.addObservation(observation);
		data.setTimestamp(timestamp.toString());
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString="";
		jsonInString = mapper.writeValueAsString(data);
		log.debug("  +- Created liveData = " + jsonInString);		
		return jsonInString;
		}

	//ok
	private String readValues() throws Exception
	{
		return mqttClient.recieve();		
	}
	
	
	//ok
	private boolean connectMqtt()
	{		
		ArrayList<String> params = extractParameters();		
		boolean status=false;
		log.debug("trying to connect to MQTT.....");
		try {
			mqttClient = new MqttClient(params.get(0), params.get(1),params.get(2),params.get(3));
			mqttClient.subscribe(params.get(4));
			this.topic = params.get(4);
			status=true;
			log.debug("Connected and subscibed!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			log.debug("Error!! Not Connected to MQTT");
		}
		
		return status;		
	}
	
	//ok
	private boolean register()
	{
		// TODO remove credentials
		boolean status=false;
		Credentials credentials = new Credentials();
		credentials.setUser("george");
		credentials.setPassword("george123");
//		RegistrationResult res = dataRoute.registerDevice(this.dsm, credentials);
		
		RegistrationResult res = dataRoute.registerDevice(this.dsm, credentials);
//		RegistrationResult res = dataRoute.registerDevice(this.dsm, this.dsd, this.dk, credentials);
		if (res.getStatus()==RegistrationResultStatusEnum.SUCCESS)
			status=true;
		else {
			RegistrationResult res1 = dataRoute.unRegisterDevice();
			if (res1.getStatus()==RegistrationResultStatusEnum.SUCCESS)
			{
				
				res= dataRoute.registerDevice(this.dsm,credentials);
				
			}
		}
		if (status==true)
		{
			log.debug("---------------------------------------------");
			log.debug("Registered through dataRoute client");
		}
		else
		{
			log.debug("---------------------------------------------");
			log.debug("Error registering through dataRoute client");
		}
		return status;		
	}
	
//	ok
	private ArrayList<String> extractParameters()
	{
		log.debug("extract connection values to MQTT from dsm");
		String host="", port="", user="", pasw="", topic="";
		int count=dsm.getDataSourceDefinitionInterfaceParameters().getParameter().size();		
		for (int i=0;i<count;i++)
		{
			if (dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getKey().equals("host"))
			{
				host = dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue();
			}
			else if (dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getKey().equals("port"))
			{
				port = dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue();
			}
			else if (dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getKey().equals("username"))
			{
				user = dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue();
			}
			else if (dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getKey().equals("password"))
			{
				pasw = dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue();
			}
			else if (dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getKey().equals("topic"))
			{
				topic = dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue();
				String topic2 = topic.replace('/', '-');				
				dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).setValue(topic2);
				//System.out.println("new topic is ="+dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(i).getValue());
			}			
		}
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(host);
		parameters.add(port);
		parameters.add(user);
		parameters.add(pasw);
		parameters.add(topic);
		log.debug("MQTT conection params are host="+ host + ":" + port + " topic=" + topic);
		return parameters;		
	}

	@Override
    public void run(){
        String msg = null;
        while(true){
            try{
            msg=in.readLine();
            }catch(Exception e)
            {
            	
            }             
            if(msg.equals("	"))
            {
            	quit=1;
            	break;
            }
        }
    }
	
}
