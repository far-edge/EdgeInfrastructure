package eu.faredge.edgeInfrastructure.registry.models;

import java.util.UUID;

public class DataInterfaceSpecification
{

	private UUID dataInterfaceSpecificationId;
	private String communicationProtocol; 
	private String communicationProtocolDetails;
	private String connectionParameters;
	public String getCommunicationProtocolDetails() {
		return communicationProtocolDetails;
	}
	public void setCommunicationProtocolDetails(String communicationProtocolDetails) {
		this.communicationProtocolDetails = communicationProtocolDetails;
	}
	public String getCommunicationProtocol() {
		return communicationProtocol;
	}
	public void setCommunicationProtocol(String communicationProtocol) {
		this.communicationProtocol = communicationProtocol;
	}
	public UUID getDataInterfaceSpecificationId() {
		return dataInterfaceSpecificationId;
	}
	public void setDataInterfaceSpecificationId(UUID dataInterfaceSpecificationId) {
		this.dataInterfaceSpecificationId = dataInterfaceSpecificationId;
	}
	public String getConnectionParameters() {
		return connectionParameters;
	}
	public void setConnectionParameters(String connectionParameters) {
		this.connectionParameters = connectionParameters;
	}

}
