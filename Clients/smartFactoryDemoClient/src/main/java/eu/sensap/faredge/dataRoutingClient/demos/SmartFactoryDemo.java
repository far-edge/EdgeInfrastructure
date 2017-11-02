package eu.sensap.faredge.dataRoutingClient.demos;

import java.util.ArrayList;
import java.util.Properties;

import eu.sensap.farEdge.dataRoutingClient.impl.DataRouteClient;
import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;
import eu.sensap.faredge.dataRoutingClient.demos.Processors.SmartFactoryDemoProcessor;
import eu.sensap.faredge.dataRoutingClient.senml.SenmlPack;

public class SmartFactoryDemo
{
	private String fileName; // The location of demo file
	private String splitter; // Use semicolon(;) as column splitter
	private int sleepTime; // Sleep time for each packet
	private String genericTopic ; // A generic topic to modify demo topics name. Demo has 9
															// modules [0,1..9]
	private String genericBaseName = "00:00:00:00:00:00:00:"; // A generic baseName more similar to mac address for each
																// sensor
	private Properties routingProps;

	public SmartFactoryDemo(Properties parsingProps, Properties dataRoutingProps)
	{
		// load properties values from property files
		fileName = parsingProps.getProperty("fileName");
		splitter = parsingProps.getProperty("splitter");
		sleepTime = Integer.parseInt(parsingProps.getProperty("sleepTime"));
		genericTopic = parsingProps.getProperty("genericTopic");
		genericBaseName = parsingProps.getProperty("genericBaseName");
		setRoutingProps(dataRoutingProps);

	}

	public void excecute()
	{
		// call processor for demo file extraction and get all records from file as
		// SenmlPacks. Each pack is a record of demo file. We are doing nothing with the
		// list yet
		SmartFactoryDemoProcessor demo = new SmartFactoryDemoProcessor(fileName, splitter);
		 
		demo.parseFile();
		ArrayList<SenmlPack> senmlPacks = demo.getSenmlPacks();

		int listLength = senmlPacks.size(); 

		// for each SenmlPack we run some modifications and publish to kafka as SenML
		// record
		for (int i = 0; i < listLength; i=i+2)
		{
			// new time for next two records
			Number time = System.currentTimeMillis();
			
			
			for (int j=0;j<2;j++)
			{

			String topic; // the new topic for each SenmlPack topic is the conc of generic + baseName (or
							// module_id of demo file)
			String message; // the message is the SenmlJson of each SenmlPack

			// corect the baseName from [1..9] to something like mac address and update it
			// to current SenmlPack
			String baseName = senmlPacks.get(i+j).getListEntries().get(0).getBaseName();
			String newbaseName = genericBaseName + baseName;
			senmlPacks.get(i+j).getListEntries().get(0).setBaseName(newbaseName);

			// create topic string
			topic = genericTopic + "-" + baseName + "-" + senmlPacks.get(i+j).getListEntries().get(0).getName();

			// change baseTime for each SenmlPack to current time in milliseconds
			senmlPacks.get(i+j).getListEntries().get(0).setBaseTime(time);

			// convert current SenmlPack to SenmlJson and store it as a message
			message = senmlPacks.get(i+j).convertToJson();

			System.out.println("message=" + message);

			// configuration environments for Kafka. Fill configuration environments "env"
			// with topic and properties
			ConfigurationEnv env = new ConfigurationEnv();
			env.setTopic(topic);
			env.setEnvironments(getRoutingProps());

			// call data route client to connect to the kafka with "env" and publish the
			// message "message" to the topic "topic"
			DataRouteClient client = new DataRouteClient(env);
			client.publish(message);
			}

			// Demo data are an iterative raw sequence of 9 different modules. Each
			// iteration has the same time
			// We pause system for a "sleepTime" time every 9 raws
			int letancy = randomLetancy(500, 1000);
			System.out.println("Letancy= " + letancy);
			sleep(sleepTime, i);

		}

		System.out.println("url=" + fileName);

	}

	private static void sleep(int sleepTime, int lap)
	{
		if (((lap + 1) % 18) == 0)
		{
			try
			{
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				// do nothing
				e.printStackTrace();
			}
			finally
			{
				// do nothing
			}
		}
	}

	private static int randomLetancy(int minLetancyInMils, int maxLetancyInMilis)
	{
		int num = minLetancyInMils + (int) (Math.random() * ((maxLetancyInMilis - minLetancyInMils) + 1));
		return num;
	}

	public Properties getRoutingProps()
	{
		return routingProps;
	}

	public void setRoutingProps(Properties routingProps)
	{
		this.routingProps = routingProps;
	}

}
