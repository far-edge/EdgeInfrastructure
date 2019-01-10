package eu.sensap.dataRouting.model;

import eu.faredge.edgeInfrastructure.registry.models.full.FullDSM;

public class RequestWrapper {
	FullDSM sourceDsm;
	FullDSM destDsm;
	public FullDSM getSourceDsm() {
		return sourceDsm;
	}
	public void setSourceDsm(FullDSM sourceDsm) {
		this.sourceDsm = sourceDsm;
	}
	public FullDSM getDestDsm() {
		return destDsm;
	}
	public void setDestDsm(FullDSM destDsm) {
		this.destDsm = destDsm;
	}
	

}
