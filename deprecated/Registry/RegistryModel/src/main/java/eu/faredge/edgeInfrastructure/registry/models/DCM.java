package eu.faredge.edgeInfrastructure.registry.models;


import java.util.List;

public class DCM
{

	private String uri;
	private String macAddress;
	private List<String> dataSourceDefinitionReferenceID;

	public String getUri()
	{
		return uri;
	}
	
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	public String getMacAddress()
	{
		return macAddress;
	}
	
	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}
	
	public List<String> getDataSourceDefinitionReferenceID()
	{
		return dataSourceDefinitionReferenceID;
	}
	
	public void setDataSourceDefinitionReferenceID(List<String> dataSourceDefinitionReferenceID)
	{
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}
	


}