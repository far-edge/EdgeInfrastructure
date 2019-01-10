package eu.faredge.edgeInfrastructure.registry.models.full;

public class FullDSD 
{

	private String id;
	private String uri;
	private String name;
	private FullDI dataInterfaceReferenceID;
	private FullDataKindReferenceIDs dataKindReferenceIDs;
	
	
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
	
	public FullDI getDataInterfaceReferenceID()
	{
		return dataInterfaceReferenceID;
	}
	
	public void setDataInterfaceReferenceID(FullDI dataInterfaceReferenceID)
	{
		this.dataInterfaceReferenceID = dataInterfaceReferenceID;
	}
	
	public FullDataKindReferenceIDs getDataKindReferenceIDs()
	{
		return dataKindReferenceIDs;
	}
	
	public void setDataKindReferenceIDs(FullDataKindReferenceIDs dataKindReferenceIDs)
	{
		this.dataKindReferenceIDs = dataKindReferenceIDs;
	}


}
