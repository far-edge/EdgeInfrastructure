package eu.sensap.dataRouting.model;

public class RequestPojo {
	private String sourceUrl;
	private String sourcePort;
	private String sourceTopic;
	private String sourceType;
	private String destUrl;
	private String destPort;
	private String destTopic;
	private String destType;
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getSourceTopic() {
		return sourceTopic;
	}
	public void setSourceTopic(String sourceTopic) {
		this.sourceTopic = sourceTopic;
	}
	public String getDestUrl() {
		return destUrl;
	}
	public void setDestUrl(String destUrl) {
		this.destUrl = destUrl;
	}
	public String getDestPort() {
		return destPort;
	}
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}
	public String getDestTopic() {
		return destTopic;
	}
	public void setDestTopic(String destTopic) {
		this.destTopic = destTopic;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getDestType() {
		return destType;
	}
	public void setDestType(String destType) {
		this.destType = destType;
	}
	
	
	
}
