package eu.faredge.edgeInfrastructure.registry.models.full;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;





public class FullDataSourceDefinitionInterfaceParameters implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Integer Id;
	private String descr;
	private List<Params> parameter;
	
	
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

	public List<Params> getParameter()
	{
		if (parameter==null)
		{
			parameter = new ArrayList<Params>();
		}
		return this.parameter;
	}

	public void setParameter(List<Params> parameter)
	{
		this.parameter = parameter;
	}

}
