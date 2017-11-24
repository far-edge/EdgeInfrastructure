package eu.faredge.edgeInfrastructure.deviceRegistry.business;

public class LedgerCredentials {
	private String user;
	private String password;

	public LedgerCredentials()
	{
		
	}
	
	public LedgerCredentials(String user, String password)
	{
		setUser(user);
		setPassword(password);
				
	}
	
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
