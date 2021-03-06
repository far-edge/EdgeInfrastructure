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
	private Integer dcd_id;

	@Column (name="id")
	private String id;
	
	@Column (name="uri")
	private String uri;
	
	@Column (name="dsm_id")
	private String dataSourceManifestReferenceID;
	
	@Column (name="dcm_id")
	private String dataConsumerManifestReferenceID;
	
	@Column (name="validFrom")
	private Date validFrom;
	
	@Column (name="validTo")
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

	public void setDataSourceManifestReferenceID(String dataSourceManifestReferenceID) {
		this.dataSourceManifestReferenceID = dataSourceManifestReferenceID;
	}

	public String getDataConsumerManifestReferenceID() {
		return dataConsumerManifestReferenceID;
	}

	public void setDataConsumerManifestReferenceID(String dataConsumerManifestReferenceID) {
		this.dataConsumerManifestReferenceID = dataConsumerManifestReferenceID;
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

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
