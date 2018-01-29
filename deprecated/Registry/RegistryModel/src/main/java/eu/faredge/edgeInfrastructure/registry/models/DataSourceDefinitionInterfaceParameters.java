package eu.faredge.edgeInfrastructure.registry.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataSourceDefinitionInterfaceParameters {

	private  List<Parameter> parameter;
	private UUID dataInterfaceSpecificationParametersId;
	
	public List<Parameter> getParameters()
	{
		
		if (parameter == null)
		{
            parameter = new ArrayList<Parameter>();
        }
		
        return this.parameter;
	}
	
	public void setParameters(List<Parameter> parameter)
	{
		this.parameter = parameter;
	}
	
	public UUID getDataInterfaceSpecificationParametersId()
	{
		return dataInterfaceSpecificationParametersId;
	}
	
	public void setDataInterfaceSpecificationParametersId(UUID dataInterfaceSpecificationParametersId)
	{
		this.dataInterfaceSpecificationParametersId = dataInterfaceSpecificationParametersId;
	}

}
