package eu.faredge.edgeInfrastructure.registry.models;

import java.util.UUID;

public class DataSourceManifest extends BaseEntity
{
	private UUID dataSourceManifestId;
	private DataSourceDefinition dataSourceDefinition;
	private DataInterfaceSpecificationParameters dataSourceDefinitionParameters;
	private String MACAddress;
	public UUID getDataSourceManifestId() {
		return dataSourceManifestId;
	}
	public void setDataSourceManifestId(UUID dataSourceManifestId) {
		this.dataSourceManifestId = dataSourceManifestId;
	}
	public DataSourceDefinition getDataSourceDefinition() {
		return dataSourceDefinition;
	}
	public void setDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		this.dataSourceDefinition = dataSourceDefinition;
	}
	public DataInterfaceSpecificationParameters getDataSourceDefinitionParameters() {
		return dataSourceDefinitionParameters;
	}
	public void setDataSourceDefinitionParameters(DataInterfaceSpecificationParameters dataSourceDefinitionParameters) {
		this.dataSourceDefinitionParameters = dataSourceDefinitionParameters;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}
	

}
