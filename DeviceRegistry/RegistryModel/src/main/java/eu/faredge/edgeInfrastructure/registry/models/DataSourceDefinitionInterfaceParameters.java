package eu.faredge.edgeInfrastructure.registry.models;

import java.io.Serializable;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataSourceDefinitionInterfaceParameters implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer Id;


	private String descr;
	

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
