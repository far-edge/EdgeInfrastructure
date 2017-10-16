/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.models;

/**
 * This class stores the results of a registration request
 * It is part of DataRouteClient data model
 */
public class RegistrationResult
{
	private boolean status;
	private String errorMessage;
	
	
	//Getters and setters
	
	public boolean isStatus()
	{
		return status;
	}
	
	public void setStatus(boolean status)
	{
		this.status = status;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
}
