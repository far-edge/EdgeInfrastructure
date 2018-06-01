package eu.faredge.edgeInfrastructure.registry.repo.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dataSourceDefinitionInterfaceParameters")
public class DataSourceDefinitionInterfaceParameters implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dsdip_id")
	@JsonIgnore
	private Integer Id;

	@Column(name = "descr")
	private String descr;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name = "dsdip_id")
	private Set<Parameter> parameter;
	
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

	public String getDescr()
	{
		return descr;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
	}

	public Set<Parameter> getParameter()
	{
		return this.parameter;
	}

	public void setParameter(Set<Parameter> parameter)
	{
		this.parameter = parameter;
	}

}
