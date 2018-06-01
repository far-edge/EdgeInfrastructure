package eu.faredge.edgeInfrastructure.registry.models.dcm;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataSourceDefinitionReferenceIDs {

	@JsonIgnore
	private Integer dsdr_id;
	private List<String> dataSourceDefinitionReferenceID;

	
	@JsonIgnore
	public Integer getDsdr_id() {
		return dsdr_id;
	}

	@JsonIgnore
	public void setDsdr_id(Integer dsdr_id) {
		this.dsdr_id = dsdr_id;
	}

	public List<String> getDataSourceDefinitionReferenceID() {
		return dataSourceDefinitionReferenceID;
	}

	public void setDataSourceDefinitionReferenceID(List<String> dataSourceDefinitionReferenceID) {
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}

}
