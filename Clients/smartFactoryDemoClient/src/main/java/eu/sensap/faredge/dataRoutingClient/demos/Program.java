package eu.sensap.faredge.dataRoutingClient.demos;


import java.util.Properties;


public class Program
{

	public static void main(String[] args) {
		Properties datRoutingProps = new Properties();
		Properties parsingProps = new Properties();
		try {
			// load a properties file from class path. These properties are for KAFKA configuration
			datRoutingProps.load(Program.class.getClassLoader().getResourceAsStream("dataRouting.properties"));
			
			// load a properties file from class path. These properties are for KAFKA configuration
			parsingProps.load(Program.class.getClassLoader().getResourceAsStream("parser.properties"));
			
			SmartFactoryDemo demo = new SmartFactoryDemo(parsingProps, datRoutingProps);
			demo.excecute();

			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		


	}
	
}
