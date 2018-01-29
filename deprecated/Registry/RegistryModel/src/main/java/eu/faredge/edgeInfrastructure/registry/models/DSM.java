package eu.faredge.edgeInfrastructure.registry.models;

public class DSM
{
	private String uri;
	private String macAddress;	
	private String dataSourceDefinitionReferenceID;
	private DataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters;
	
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
	
	public String getDataSourceDefinitionReferenceID()
	{
		return dataSourceDefinitionReferenceID;
	}
	
	public void setDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID)
	{
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}
	
	public DataSourceDefinitionInterfaceParameters getDataSourceDefinitionInterfaceParameters()
	{
		return dataSourceDefinitionInterfaceParameters;
	}
	
	public void setDataSourceDefinitionInterfaceParameters(
			DataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters)
	{
		this.dataSourceDefinitionInterfaceParameters = dataSourceDefinitionInterfaceParameters;
	}

}
