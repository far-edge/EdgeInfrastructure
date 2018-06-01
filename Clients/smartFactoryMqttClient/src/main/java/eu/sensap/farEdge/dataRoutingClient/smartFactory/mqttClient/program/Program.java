package eu.sensap.farEdge.dataRoutingClient.smartFactory.mqttClient.program;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.faredge.edgeInfrastructure.registry.models.LiveDataSet;
import eu.faredge.edgeInfrastructure.registry.models.di.DI;
import eu.faredge.edgeInfrastructure.registry.models.dk.DK;
import eu.faredge.edgeInfrastructure.registry.models.dsd.DSD;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import eu.sensap.farEdge.dataRoutingClient.smartFactory.DataSourceClient;


public class Program {
	
//	MQTT client = new MQTT();

	private final static Logger log = LoggerFactory.getLogger(Program.class);
	
	public static void main(String[] args) {

//		log.debug("read dsm file");
		
		// load files 
		DSM dsm = readJSONFile("dsm",DSM.class);								// read init dsm for MQTT topic		
		//System.out.println(dsm.getDataSourceDefinitionInterfaceParameters().getParameter().get(2).getKey());
		LiveDataSet dataSet = readJSONFile("liveData",LiveDataSet.class);		// read a liveDataSet Template. Contains location and data kind
		//System.out.println(dataSet.isMobile());
		DI di = readJSONFile("di",DI.class);
		//System.out.println(di.getParameters().getParameter().get(3).getName());
		DK dk = readJSONFile("dk",DK.class);
		//System.out.println(dk.getId());
		DSD dsd = readJSONFile("dsd",DSD.class);
		//System.out.println(dsd.getId());
		
		///propably we do not need this /////////
		Properties clientProps = new Properties();
		try 
		{
			clientProps.load(Program.class.getClassLoader().getResourceAsStream("mqttClient.properties"));
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		////////////////////////////////////////////
		
		DataSourceClient dsc = new DataSourceClient(dsm,dsd,di,dk,dataSet);
		dsc.publish();

	}
	
	public static <T> T readJSONFile (String fileName, Class<T> a)
	{
        try
        {
        	ObjectMapper mapper = new ObjectMapper();
        	return  mapper.readValue(Program.class.getClassLoader().getResourceAsStream(fileName), a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
