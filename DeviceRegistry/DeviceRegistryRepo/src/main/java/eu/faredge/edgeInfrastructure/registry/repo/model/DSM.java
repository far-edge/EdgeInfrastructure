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
@Table (name="DSMs")//, uniqueConstraints={@UniqueConstraint(columnNames = {"uri" , "macAddress"})})
public class DSM implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="dsm_id")
	@JsonIgnore
	private Integer Id;

	@Column(name="uri", unique=true)
	private String uri;
	
	@Column(name="macAddress", unique=true)
	private String macAddress;
	
	@Column(name="dataSourceDefinitionReferenceID")   //DataSourceDefinitionReferenceID
	private String dataSourceDefinitionReferenceID;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dsdip_id")  //DataSourceDefinitionInterfaceParameters
	private DataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters;
	
	
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
	
	public String getDataSourceDefinitionReferenceID()
	{
		return dataSourceDefinitionReferenceID;
	}
	
	public void setDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID)
	{
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}
	
	public DataSourceDefinitionInterfaceParameters getDataSourceDefinitionInterfaceParameters()
	{
		return dataSourceDefinitionInterfaceParameters;
	}
	
	public void setDataSourceDefinitionInterfaceParameters(
			DataSourceDefinitionInterfaceParameters dataSourceDefinitionInterfaceParameters)
	{
		this.dataSourceDefinitionInterfaceParameters = dataSourceDefinitionInterfaceParameters;
	}

}
