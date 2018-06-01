package eu.faredge.edgeInfrastructure.registry.models.dcd;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class DCD implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore
	private Integer dcd_id;
	private String id;
	private String uri;
	private String dataSourceManifestReferenceID;
	private String dataConsumerManifestReferenceID;
	private Date validFrom;
	private Date validTo;

	
	//Getter and Setters
		
	@JsonIgnore
	public Integer getDcd_id() {
		return dcd_id;
	}

	@JsonIgnore
	public void setDcd_id(Integer id) {
		dcd_id = id;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDataSourceManifestReferenceID() {
		return dataSourceManifestReferenceID;
	}

	public void setDataSourceManifestReferenceID(String dsmId) {
		this.dataSourceManifestReferenceID = dsmId;
	}

	public String getDataConsumerManifestReferenceID() {
		return dataConsumerManifestReferenceID;
	}

	public void setDataConsumerManifestReferenceID(String dcmId) {
		this.dataConsumerManifestReferenceID = dcmId;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date expirationDateTime) {
		this.validTo = expirationDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
//	public DSM getDsm() {
//	return dsm;
//}
//
//public void setDsm(DSM dsm) {
//	this.dsm = dsm;
//}
//
//public DCM getDcm() {
//	return dcm;
//}
//
//public void setDcm(DCM dcm) {
//	this.dcm = dcm;
//}
	
	
}
