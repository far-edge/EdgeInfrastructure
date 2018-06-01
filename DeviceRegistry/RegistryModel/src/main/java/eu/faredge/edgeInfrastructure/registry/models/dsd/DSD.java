package eu.faredge.edgeInfrastructure.registry.models.dsd;


public class DSD 
{

	private String id;
	private String uri;
	private String name;
	private String dataInterfaceReferenceID;
	private DataKindReferenceIDs dataKindReferenceIDs;
	
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getUri()
	{
		return uri;
	}
	
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDataInterfaceReferenceID()
	{
		return dataInterfaceReferenceID;
	}
	
	public void setDataInterfaceReferenceID(String dataInterfaceReferenceID)
	{
		this.dataInterfaceReferenceID = dataInterfaceReferenceID;
	}
	
	public DataKindReferenceIDs getDataKindReferenceIDs()
	{
		return dataKindReferenceIDs;
	}
	
	public void setDataKindReferenceIDs(DataKindReferenceIDs dataKindReferenceIDs)
	{
		this.dataKindReferenceIDs = dataKindReferenceIDs;
	}


}
