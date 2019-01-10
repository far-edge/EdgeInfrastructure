package eu.faredge.edgeInfrastructure.registry.models.full;


public class FullDI
{
	private String id;
	private String uri;
	private String name;


	private String communicationProtocol;
	private Parameters parameters;
	
	//Getters and Setters
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

	public String getCommunicationProtocol()
	{
		return communicationProtocol;
	}
	
	public void setCommunicationProtocol(String communicationProtocol)
	{
		this.communicationProtocol = communicationProtocol;
	}
	
	public Parameters getParameters()
	{
		return parameters;
	}
	
	public void setParameters(Parameters parameters)
	{
		this.parameters = parameters;
	}
	
	
}
