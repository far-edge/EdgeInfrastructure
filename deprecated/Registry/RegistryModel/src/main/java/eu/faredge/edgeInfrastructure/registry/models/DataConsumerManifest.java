package eu.faredge.edgeInfrastructure.registry.models;

import java.util.Collection;
import java.util.UUID;

public class DataConsumerManifest extends BaseEntity
{

	private UUID dataConsumerManifestId;
	private String macAddress;
	private Collection<DataSourceDefinition> dataSourceDefinitions;
	public UUID getDataConsumerManifestId() {
		return dataConsumerManifestId;
	}
	public void setDataConsumerManifestId(UUID dataConsumerManifestId) {
		this.dataConsumerManifestId = dataConsumerManifestId;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Collection<DataSourceDefinition> getDataSourceDefinitions() {
		return dataSourceDefinitions;
	}
	public void setDataSourceDefinitions(Collection<DataSourceDefinition> dataSourceDefinitions) {
		this.dataSourceDefinitions = dataSourceDefinitions;
	}


}
