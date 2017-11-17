package eu.faredge.edgeInfrastructure.registry.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public class DataInterfaceSpecificationParameters 
{

	public Collection<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(Collection<Parameter> parameters) {
		this.parameters = parameters;
	}
	public UUID getDataInterfaceSpecificationParametersId() {
		return dataInterfaceSpecificationParametersId;
	}
	public void setDataInterfaceSpecificationParametersId(UUID dataInterfaceSpecificationParametersId) {
		this.dataInterfaceSpecificationParametersId = dataInterfaceSpecificationParametersId;
	}
	private Collection<Parameter> parameters;
	private UUID dataInterfaceSpecificationParametersId;

}
