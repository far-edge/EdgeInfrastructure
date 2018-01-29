/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.consumerClient.interfaces;

import eu.sensap.farEdge.consumerClient.models.ConfigurationEnv;
import eu.sensap.farEdge.consumerClient.models.Credentials;
import eu.sensap.farEdge.consumerClient.models.RegistrationResult;

/***
 * Interface which implements registration operations
 * There are three operations:
 * 	1. register to Registry
 * 	2. unRegister from registry
 * 	3. status of registration
 */
public interface DeviceRegisterInterface
{
	
	/***
	 * register method: A device registers to the registry 
	 * @param id
	 * @param credentials
	 * @param configurationEnv
	 * @return  RegistrationResult
	 */
	public RegistrationResult registerDevice(String id, Credentials credentials, ConfigurationEnv configurationEnv );
	
	/***
	 * unRegister method : A device unregisters from the registry
	 * @return RegistrationResult
	 */
	public RegistrationResult unRegisterDevice();
	
	
	/**
	 * isRegistered method: Returns the registration status of a device
	 * @return boolean 
	 */
	public boolean isRegistered();
	

}
