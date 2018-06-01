package eu.faredge.edgeInfrastructure.registry.models.dsm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataSourceDefinitionInterfaceParameters implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer Id;
	private String descr;
	private List<Parameter> parameter;
	
	
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

	public List<Parameter> getParameter()
	{
		if (parameter==null)
		{
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public void setParameter(List<Parameter> parameter)
	{
		this.parameter = parameter;
	}

}
