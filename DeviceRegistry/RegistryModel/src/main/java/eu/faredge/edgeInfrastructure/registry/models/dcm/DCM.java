package eu.faredge.edgeInfrastructure.registry.models.dcm;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DCM
{

	@JsonIgnore
	private Integer dcm_id;
	private String uri;
	private String id;
	private String macAddress;
	private DataSourceDefinitionReferenceIDs dataSourceDefinitionReferenceIDs;
	//private List<String> dataSourceDefinitionReferenceID;

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
		this.id = id;
	}
	
	public String getMacAddress()
	{
		return macAddress;
	}
	
	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}
	
	public DataSourceDefinitionReferenceIDs getDataSourceDefinitionReferenceIDs() {
		return dataSourceDefinitionReferenceIDs;
	}

	public void setDataSourceDefinitionReferenceIDs(DataSourceDefinitionReferenceIDs dataSourceDefinitionReferenceIDs) {
		this.dataSourceDefinitionReferenceIDs = dataSourceDefinitionReferenceIDs;
	}

	@JsonIgnore
	public Integer getDcm_id() {
		return dcm_id;
	}

	@JsonIgnore
	public void setDcm_id(Integer dcm_id) {
		this.dcm_id = dcm_id;
	}
	


}