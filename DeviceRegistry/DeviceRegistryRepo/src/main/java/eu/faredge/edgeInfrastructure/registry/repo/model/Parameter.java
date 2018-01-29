package eu.faredge.edgeInfrastructure.registry.repo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="parameter")
public class Parameter implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id	
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="parameter_id")
	@JsonIgnore
	private Integer Id;
		
	@Column(name="key")
	private String key;
	
	@Column(name="value")
	private String value;
	
	
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
	
	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}	

}
