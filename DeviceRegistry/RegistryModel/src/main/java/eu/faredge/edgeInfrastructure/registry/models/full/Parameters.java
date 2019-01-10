package eu.faredge.edgeInfrastructure.registry.models.full;

import java.util.ArrayList;
import java.util.List;

public class Parameters
{
	private List <Parameter> parameter;

	public List <Parameter> getParameter()
	{
		if (parameter==null)
		{
			parameter = new ArrayList<Parameter>();
		}
		return this.parameter;
	}

	public void setParameter(List <Parameter> parameter)
	{
		this.parameter = parameter;
	}

}
