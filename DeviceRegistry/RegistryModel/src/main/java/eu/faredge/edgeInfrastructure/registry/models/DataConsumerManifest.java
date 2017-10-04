package eu.faredge.edgeInfrastructure.registry.models;

import java.util.Collection;
import java.util.UUID;

public class DataConsumerManifest extends BaseEntity
{
	private UUID dataConsumerManifestId;
	private String MACAddress;
	private Collection<DataSourceDefinition> dataSourceDefinitions;
	public UUID getDataConsumerManifestId() {
		return dataConsumerManifestId;
	}
	public void setDataConsumerManifestId(UUID dataConsumerManifestId) {
		this.dataConsumerManifestId = dataConsumerManifestId;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}
	public Collection<DataSourceDefinition> getDataSourceDefinitions() {
		return dataSourceDefinitions;
	}
	public void setDataSourceDefinitions(Collection<DataSourceDefinition> dataSourceDefinitions) {
		this.dataSourceDefinitions = dataSourceDefinitions;
	}


}
