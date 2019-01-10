package eu.sensap.dataRouting.model;

import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;

public class DataSourceDataFlow {
	DSM sourceDsm;
	DSM destDsm;
	public DSM getSourceDsm() {
		return sourceDsm;
	}
	public void setSourceDsm(DSM sourceDsm) {
		this.sourceDsm = sourceDsm;
	}
	public DSM getDestDsm() {
		return destDsm;
	}
	public void setDestDsm(DSM destDsm) {
		this.destDsm = destDsm;
	}
	
}
