package eu.faredge.edgeInfrastructure.registry.models;

import java.util.UUID;

public class DataTopic extends BaseEntity
{

	private UUID dataTopicId;
	private String dataModelDescription;
	public UUID getDataTopicId() {
		return dataTopicId;
	}
	public void setDataTopicId(UUID dataTopicId) {
		this.dataTopicId = dataTopicId;
	}
	public String getDataModelDescription() {
		return dataModelDescription;
	}
	public void setDataModelDescription(String dataModelDescription) {
		this.dataModelDescription = dataModelDescription;
	}
	

}
