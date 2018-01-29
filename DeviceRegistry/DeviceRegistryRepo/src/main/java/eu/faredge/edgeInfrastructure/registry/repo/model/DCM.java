package eu.faredge.edgeInfrastructure.registry.repo.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Integer Id;

	@Column(name="uri", unique=true)
	private String uri;
	
	@Column(name="macAddress")
	private String macAddress;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
//	@JoinColumn(name = "dcm_id")
	@ElementCollection(targetClass=String.class)
	private Set<String> dataSourceDefinitionReferenceID;

	
	//Getters and Setters
	
	@JsonIgnore
	public Integer getId()
	{
		return Id;
	}

	@JsonIgnore
	public void setId(Integer id)
	{
		Id = id;
	}
	
	public String getUri()
	{
		return uri;
	}
	
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	public String getMacAddress()
	{
		return macAddress;
	}
	
	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}
	
	public Set<String> getDataSourceDefinitionReferenceID()
	{
		return dataSourceDefinitionReferenceID;
	}
	
	public void setDataSourceDefinitionReferenceID(Set<String> dataSourceDefinitionReferenceID)
	{
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}

}