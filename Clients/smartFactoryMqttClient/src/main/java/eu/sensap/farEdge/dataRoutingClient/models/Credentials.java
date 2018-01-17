/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.models;
/***
 * This class stores the credentials for the connection to data route layer
 * It is part of DataRouteClient data model
 */
public class Credentials
{
	private String user;
	private String password;

	
	//Getters and setters
	
	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
