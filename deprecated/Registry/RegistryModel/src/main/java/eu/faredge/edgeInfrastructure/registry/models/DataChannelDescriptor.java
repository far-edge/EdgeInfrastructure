package eu.faredge.edgeInfrastructure.registry.models;

import java.util.Date;
import java.util.UUID;


public class DataChannelDescriptor extends BaseEntity {

	private UUID dataChannelDescriptorId; 
	private DataSourceManifest dataSourceManifest;	
	private DataConsumerManifest dataConsumerManifest;
	private Date validFrom;
	private Date expirationDateTime;
	
	public UUID getDataChannelDescriptorId() {
		return dataChannelDescriptorId;
	}
	public void setDataChannelDescriptorId(UUID dataChannelDescriptorId) {
		this.dataChannelDescriptorId = dataChannelDescriptorId;
	}
	public DataSourceManifest getDataSourceManifest() {
		return dataSourceManifest;
	}
	public void setDataSourceManifest(DataSourceManifest dataSourceManifest) {
		this.dataSourceManifest = dataSourceManifest;
	}
	public DataConsumerManifest getDataConsumerManifest() {
		return dataConsumerManifest;
	}
	public void setDataConsumerManifest(DataConsumerManifest dataConsumerManifest) {
		this.dataConsumerManifest = dataConsumerManifest;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(Date expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}

}
