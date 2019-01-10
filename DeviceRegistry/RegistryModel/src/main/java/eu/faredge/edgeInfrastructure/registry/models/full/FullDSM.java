package eu.faredge.edgeInfrastructure.registry.models.full;

public class FullDSM
{
	private Integer dsm_id;
	private String uri;
	private String id;
	private String macAddress;	
	private FullDSD dataSourceDefinitionReferenceID;
	private FullDataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters;
	
	public String getUri()
	{
		return uri;
	}
	
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id= id;
	}
	
	public String getMacAddress()
	{
		return macAddress;
	}
	
	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}
	
	public FullDSD getDataSourceDefinitionReferenceID()
	{
		return dataSourceDefinitionReferenceID;
	}
	
	public void setDataSourceDefinitionReferenceID(FullDSD dataSourceDefinitionReferenceID)
	{
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}
	
	public FullDataSourceDefinitionInterfaceParameters getDataSourceDefinitionInterfaceParameters()
	{
		return dataSourceDefinitionInterfaceParameters;
	}
	
	public void setDataSourceDefinitionInterfaceParameters(
			FullDataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters)
	{
		this.dataSourceDefinitionInterfaceParameters = dataSourceDefinitionInterfaceParameters;
	}

	public Integer getDsm_id() {
		return dsm_id;
	}

	public void setDsm_id(Integer dsm_id) {
		this.dsm_id = dsm_id;
	}

}
