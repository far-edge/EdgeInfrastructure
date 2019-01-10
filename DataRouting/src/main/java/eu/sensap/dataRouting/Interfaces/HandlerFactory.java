package eu.sensap.dataRouting.Interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.messaging.MessageHandler;

public class HandlerFactory {
	public MessageHandler gethandler(Map<String, Object> props, String topic,String type)
	{		
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();		
		InputStream in = loader.getResourceAsStream("handler.properties");
		
		try
		{			
			prop.load(in);
			in.close();
			
		} catch (IOException e1) {			
			e1.printStackTrace();			
			//TODO: return something like "I cannot open property file"
			return null;
		}
		
		String handleClass=null;
		
		if (prop.containsKey(type))
		{
			handleClass = prop.getProperty(type);
			
		}
		else
		{
			//TODO: return something like "This class does not exists"
			
			return null;
		}
		
		System.out.println("conections properties size=" +props.size());
		//MyMqttHandler handler = new MyMqttHandler(props, topic);
		

		try {
			Class<?> myClass = Class.forName(handleClass);
			Constructor<?> ctr = myClass.getConstructor(Map.class,String.class);
		    Object object = ctr.newInstance(new Object[] { props,topic});		    
		    MyHandler base = (MyHandler)object;
		    return base.getHandler();		    
		    

		} catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException");
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			System.out.println("SecurityException");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException a");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		//return handler.getHandler();
		return null;
	}

}
