package eu.faredge.edgeInfrastructure.registry.models.di;

public class Parameter
{
    
	private String name;
	private String description;
    private String dataType;
    private Object defaultValue;
    
//    getters and setters
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
	
	public String getDataType()
	{
		return dataType;
	}
	
	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}
	
	public Object getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(Object defaultValue)
	{
		this.defaultValue = defaultValue;
	}	
}
