package eu.faredge.edgeInfrastructure.registry.repo.model;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="DataSourceDefinitionReferenceIDs")
public class DataSourceDefinitionReferenceIDs implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dsdr_id")
	@JsonIgnore
	private Integer dsdr_id;
	
//	@Column(name = "descr")
//	private String descr;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
//	@JoinColumn(name = "dsdr_id")
//	private Set<DataSourceDefinitionReferenceID> dataSourceDefinitionReferenceID;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> dataSourceDefinitionReferenceID;

	public Set<String> getDataSourceDefinitionReferenceID() {
		return dataSourceDefinitionReferenceID;
	}

	public void setDataSourceDefinitionReferenceID(Set<String> dataSourceDefinitionReferenceID) {
		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
	}

		
//	public Set<DataSourceDefinitionReferenceID> getDataSourceDefinitionReferenceID() {
//		return dataSourceDefinitionReferenceID;
//	}
//
//	public void setDataSourceDefinitionReferenceID(Set<DataSourceDefinitionReferenceID> dataSourceDefinitionReferenceID) {
//		this.dataSourceDefinitionReferenceID = dataSourceDefinitionReferenceID;
//	}

	public Integer getDsdr_id() {
		return dsdr_id;
	}

	public void setDsdr_id(Integer dsdr_id) {
		this.dsdr_id = dsdr_id;
	}

//	public String getDescr() {
//		return descr;
//	}
//
//	public void setDescr(String descr) {
//		this.descr = descr;
//	}
	
//	private List<String> dataSourceDefinitionReferenceID;



}
