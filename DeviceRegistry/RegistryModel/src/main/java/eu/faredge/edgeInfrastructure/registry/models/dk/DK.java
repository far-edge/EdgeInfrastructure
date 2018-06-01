package eu.faredge.edgeInfrastructure.registry.models.dk;

public class DK 
{
	private String id;
	private String uri;
	private String name;
	private String description;
	private String modelType;
	private String format;
	private String quantityKind;
	
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

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getModelType()
	{
		return modelType;
	}
	
	public void setModelType(String modelType)
	{
		this.modelType = modelType;
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public void setFormat(String format)
	{
		this.format = format;
	}
	
	public String getQuantityKind()
	{
		return quantityKind;
	}
	
	public void setQuantityKind(String quantityKind)
	{
		this.quantityKind = quantityKind;
	}
	
	

}
