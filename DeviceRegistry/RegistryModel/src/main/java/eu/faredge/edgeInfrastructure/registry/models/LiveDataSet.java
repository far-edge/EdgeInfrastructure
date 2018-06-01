package eu.faredge.edgeInfrastructure.registry.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class LiveDataSet
{
	private String id;
	private String dataSourceManifestReferenceID;
	private boolean mobile;
	private String timestamp;
	private List<Observation> observation;
	
	public LiveDataSet () {
		this.id=null;
		this.dataSourceManifestReferenceID=null;
		this.mobile=false;
		this.timestamp=null;
		this.observation = new ArrayList<Observation>();
	}
	
	@JsonGetter("id")
	public String getId()
	{
		return id;
	}
	
	@JsonSetter("id")
	public void setId(String id)
	{
		this.id = id;
	}
	
	@JsonGetter("dataSourceManifestReferenceID")
	public String getDataSourceManifestReferenceID()
	{
		return dataSourceManifestReferenceID;
	}
	
	@JsonSetter("dataSourceManifestReferenceID")
	public void setDataSourceManifestReferenceID(String dataSourceManifestReferenceID)
	{
		this.dataSourceManifestReferenceID = dataSourceManifestReferenceID;
	}
	
	@JsonGetter("mobile")
	public boolean isMobile()
	{
		return mobile;
	}
	
	@JsonSetter("mobile")
	public void setMobile(boolean mobile)
	{
		this.mobile = mobile;
	}
	
	@JsonGetter("timestamp")
	public String getTimestamp()
	{
		return timestamp;
	}
	
	@JsonSetter("timestamp")
	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	
	@JsonSetter("observation")
	public List<Observation> getObservation()
	{
		return observation;
	}
	
	@JsonGetter("observation")
	public void setObservation(List<Observation> observation)
	{
		this.observation = observation;
	}
	
	public void addObservation(Observation observation)
	{
		this.observation.add(observation);
	}
	
}
