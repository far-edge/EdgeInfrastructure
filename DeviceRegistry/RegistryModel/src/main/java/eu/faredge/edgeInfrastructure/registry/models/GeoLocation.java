package eu.faredge.edgeInfrastructure.registry.models;

public class GeoLocation
{
	private String latitude; //"yxgiQVlxYjqoFaOpZlha",
	private String longitude; //"SSrmbtaSDEIWfoUNYGAj"
	
	
	public GeoLocation(String lat, String lon)
	{
		this.latitude=lat;
		this.longitude=lon;
		
	}
	public GeoLocation()
	{
		this.latitude=null;
		this.longitude=null;
		
	}
	//Getters and Setters
	
	public String getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}
	
	public String getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
}
