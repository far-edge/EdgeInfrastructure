package eu.faredge.edgeInfrastructure.registry.models;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class DCD implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore
	private Integer Id;
	private String uri;
	private String dsmId;
	private String dcmId;
	private Date validFrom;
	private Date expirationDateTime;

	
	//Getter and Setters
		
	@JsonIgnore
	public Integer getId() {
		return Id;
	}

	@JsonIgnore
	public void setId(Integer id) {
		Id = id;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDsmId() {
		return dsmId;
	}

	public void setDsmId(String dsmId) {
		this.dsmId = dsmId;
	}

	public String getDcmId() {
		return dcmId;
	}

	public void setDcmId(String dcmId) {
		this.dcmId = dcmId;
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
