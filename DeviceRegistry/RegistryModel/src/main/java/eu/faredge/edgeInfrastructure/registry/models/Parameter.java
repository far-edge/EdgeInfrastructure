package eu.faredge.edgeInfrastructure.registry.models;

import java.util.UUID;

public class Parameter 
{
	private UUID parameterId;
	private String key;
	private String value;
	public UUID getParameterId() {
		return parameterId;
	}
	public void setParameterId(UUID parameterId) {
		this.parameterId = parameterId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
