package eu.faredge.edgeInfrastructure.registry.models;

import java.util.Date;
import java.util.UUID;


public class DataChannelDescriptor extends BaseEntity {

	private UUID dataChannelDescriptorId; 
	private DSM dataSourceManifest;	
	private DCM dataConsumerManifest;
	private Date validFrom;
	private Date expirationDateTime;
	
	public UUID getDataChannelDescriptorId() {
		return dataChannelDescriptorId;
	}
	public void setDataChannelDescriptorId(UUID dataChannelDescriptorId) {
		this.dataChannelDescriptorId = dataChannelDescriptorId;
	}
	public DSM getDataSourceManifest() {
		return dataSourceManifest;
	}
	public void setDataSourceManifest(DSM dataSourceManifest) {
		this.dataSourceManifest = dataSourceManifest;
	}
	public DCM getDataConsumerManifest() {
		return dataConsumerManifest;
	}
	public void setDataConsumerManifest(DCM dataConsumerManifest) {
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
