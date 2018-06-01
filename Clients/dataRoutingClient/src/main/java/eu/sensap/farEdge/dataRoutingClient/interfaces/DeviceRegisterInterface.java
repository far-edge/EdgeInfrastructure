/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.dataRoutingClient.interfaces;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import eu.sensap.farEdge.dataRoutingClient.models.Credentials;


/***
 * Interface which implements registration operations
 * There are three operations:
 * 	1. register to Registry
 * 	2. unRegister from registry
 * 	3. status of registration
 */
public interface DeviceRegisterInterface
{
	
	public void create(String registryUri);
	
	/***
	 * register method: A device registers to the registry 
	 * @param dsm: data source manifest
	 * @param credentials
	 * @return  RegistrationResult
	 */
	public RegistrationResult registerDevice(DSM dsm, Credentials credentials);
	
	/***
	 * unRegister method : A device unregisters from the registry
	 * @return RegistrationResult
	 */
	public RegistrationResult unRegisterDevice(String uri, Credentials credentials);
	
	
	/**
	 * isRegistered method: Returns the registration status of a device
	 * @return boolean 
	 */
	public boolean isRegistered(DSM dsm, Credentials credentials);
	

}
