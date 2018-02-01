package eu.faredge.edgeInfrastructure.registry.repo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name ="DCDs")
public class DCD implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="dcd_id", unique=true)	
	@JsonIgnore
	private Integer Id;

	@Column (name="uri")
	private String uri;
	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="dsm_id")
//	private DSM dsm;
	
	@Column (name="dsm_id")
	private String dsmId;
	
	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name="dcm_id")
//	private DCM dcm;
	
	@Column (name="dcm_id")
	private String dcmId;
	
	@Column (name="validFrom")
	private Date validFrom;
	
	@Column (name="expirationDateTime")
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
