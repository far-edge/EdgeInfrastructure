package eu.faredge.edgeInfrastructure.registry.repo.model;


import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table (name="DCMs")//, uniqueConstraints= {@UniqueConstraint(columnNames = {"uri" , "macAddress"})})
public class DCM implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="dcm_id", unique=true)	
	@JsonIgnore
	private Integer dcm_id;

	@Column(name="uri", unique=true)
	private String uri;
	
	@Column(name="id", unique=true)
	private String id;
	
	@Column(name="macAddress")
	private String macAddress;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dsdr_id")  //dataSourceDefinitionReferenceIDs
	private DataSourceDefinitionReferenceIDs dataSourceDefinitionReferenceIDs;
	
		
	//Getters and Setters
	
	@JsonIgnore
	public Integer getDcm_id()
	{
		return dcm_id;
	}

	@JsonIgnore
	public void setDcm_id(Integer dcm_id)
	{
		this.dcm_id = dcm_id;
	}
	
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


}