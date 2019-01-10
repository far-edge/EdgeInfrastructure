package eu.sensap.farEdge.dataRoutingClient.smartFactory.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Utils {
	
	public static <T> T readJSONFile (String fileName, Class<T> a)
	{
        try
        {
        	ObjectMapper mapper = new ObjectMapper();
        	return  mapper.readValue(Utils.class.getClassLoader().getResourceAsStream(fileName), a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
