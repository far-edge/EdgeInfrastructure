package eu.faredge.edgeInfrastructure.registry.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Observation
{
    private String id; //"obs://dca24f35-932f-4579-94c2-c40d80082a40",
    private String name; // "xKQwLxxaDolqfnErMXTi"
    private String dataKindReferenceID; // "dk://e22ea144-c8f7-4018-aa2f-b094202881a5"
    private String timestamp; //"2017-05-13T20:20:20.0Z"
    private GeoLocation location;
    private Number value; //"WidMxhGJFnVZxamsRngS"
    
    
    public Observation()
    {
    	this.id=null;
    	this.name=null;
    	this.dataKindReferenceID=null;
    	this.timestamp=null;
    	this.value=null;
    	this.location= null;
    }
    
    @JsonGetter ("id")
    public String getId()
    {
		return id;
	}
    
    @JsonSetter("id")
    public void setId(String id)
	{
		this.id = id;
	}
	
	@JsonGetter("name")
	public String getName()
	{
		return name;
	}
	
	@JsonSetter("name")
	public void setName(String name)
	{
		this.name = name;
	}
	
	@JsonGetter("dataKindReferenceID")
	public String getDataKindReferenceID()
	{
		return dataKindReferenceID;
	}
	
	@JsonSetter("dataKindReferenceID")
	public void setDataKindReferenceID(String dataKindReferenceID)
	{
		this.dataKindReferenceID = dataKindReferenceID;
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
	
	@JsonGetter("location")
	public GeoLocation getLocation()
	{
		return location;
	}
	
	@JsonSetter("location")
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
	
	@JsonGetter("value")
	public Number getValue() {
		return value;
	}
	
	@JsonSetter("value")
	public void setValue(Number value) {
		this.value = value;
	}
}
