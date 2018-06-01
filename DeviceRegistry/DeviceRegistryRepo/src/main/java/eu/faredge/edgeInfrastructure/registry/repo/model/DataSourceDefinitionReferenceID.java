package eu.faredge.edgeInfrastructure.registry.repo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dataSourceDefinitionReferenceID")
public class DataSourceDefinitionReferenceID implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dsd_id")
	private Integer Id;
	
	@Column(name="dataSourceDefinitionReferenceID")
	private String dataSourceDefinitionReferenceID;

	public String getDataSourceDefinitionReferenceID() {
		return dataSourceDefinitionReferenceID;
	}

	public void setDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID) {
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}
		
	
//	@ElementCollection(targetClass=String.class)
//	private Set<String> dataSourceDefinitionReferenceID;
//	
//	//Getters and Setters 
//		
//	public Set<String> getDataSourceDefinitionReferenceID() {
//		return dataSourceDefinitionReferenceID;
//	}
//
//	public void setDataSourceDefinitionReferenceID(Set<String> dataSourceDefinitionReferenceID) {
//		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
//	}

}
