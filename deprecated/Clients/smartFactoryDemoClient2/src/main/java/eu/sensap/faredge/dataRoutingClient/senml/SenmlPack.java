/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.faredge.dataRoutingClient.senml;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
/***
 * This class supports the SenML data transformation version 10 
 * There are the following operations:
 * 1. Create a base record
 * 2. Create a regular record
 * 3. Export to JSON format 	
 */
public class SenmlPack
{

	private Number baseVersion=10;
	private ArrayList<SenmlDataObject> listEntries;		//list of SenML Records

	// Initialization of SenML pack
	public SenmlPack()
	{
		listEntries = new ArrayList<SenmlDataObject>();
	}
	

	// public method for base record creation 
	public void addBaseEntry(String baseName, Number baseTime)
	{
		SenmlDataObject rec = new SenmlDataObject(baseName, baseTime, baseVersion);
		if ((listEntries == null) || (listEntries.size() == 0))
			listEntries.add(rec);
	}
	
	public void addBaseEntry(SenmlDataObject baseRecord) {
		if ((listEntries == null) || (listEntries.size() == 0))
			listEntries.add(baseRecord);
	}

	//public method for a single record creation
	public void addEntry(String name, String unit, Number value)
	{
		SenmlDataObject rec = new SenmlDataObject(name, unit, value);
		if ((listEntries != null) && (listEntries.size() > 0))
			listEntries.add(rec);

	}
	
	public void addEntry(SenmlDataObject record)
	{		
		if ((listEntries != null) && (listEntries.size() > 0))
			listEntries.add(record);

	}
	
	public void addEntries(ArrayList<SenmlDataObject> records)
	{		
//		if ((listEntries != null) && (listEntries.size() > 0)) 
//		{
			listEntries.clear();
			listEntries.addAll(records);
//		}
	}

	//public method to extract SenML measurement entity to JSON string representation 
	public String convertToJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		String jsonInString;
		try
		{
			jsonInString = mapper.writeValueAsString(listEntries);
			return jsonInString;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}
	
	// getters and setters of records
	
	public ArrayList<SenmlDataObject> getListEntries()
	{
		return listEntries;
	}

	public void setListEntries(ArrayList<SenmlDataObject> listEntries)
	{
		this.listEntries = listEntries;
	}
	
}
