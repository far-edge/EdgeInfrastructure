package eu.faredge.edgeInfrastructure.registry.models;

import java.util.UUID;

public class DataSourceDefinition extends BaseEntity{
	private UUID dataSourceDefinitionId;
	private String type;
	private DataTopic dataTopic;
	private DataInterfaceSpecification dataInterfaceSpecification;
	public UUID getDataSourceDefinitionId() {
		return dataSourceDefinitionId;
	}
	public void setDataSourceDefinitionId(UUID dataSourceDefinitionId) {
		this.dataSourceDefinitionId = dataSourceDefinitionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public DataTopic getDataTopic() {
		return dataTopic;
	}
	public void setDataTopic(DataTopic dataTopic) {
		this.dataTopic = dataTopic;
	}
	public DataInterfaceSpecification getDataInterfaceSpecification() {
		return dataInterfaceSpecification;
	}
	public void setDataInterfaceSpecification(DataInterfaceSpecification dataInterfaceSpecification) {
		this.dataInterfaceSpecification = dataInterfaceSpecification;
	}

}
