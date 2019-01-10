/***
 *Sensap contribution
 * @author George
 * 
 */
package eu.sensap.farEdge.consumerClient.interfaces;

/**
 * Interface which implements publish message operations
 * There are two operations: 
 *	1. sync publishing 
 *	2. Async publishing  
 * @param <V>
 */
public interface MessageBusInterface<V>
{
	
	/***
	 * publish method: The client publishes (sync) a message V
	 * @param message
	 */
	public void publish(V message);
	
	/***
	 * publishAsync method: The client publishes (Async) a message V
	 * @param message
	 */
	public void publishAsync(V message);
}
