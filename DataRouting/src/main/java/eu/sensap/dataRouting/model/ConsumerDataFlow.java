package eu.sensap.dataRouting.model;

import eu.faredge.edgeInfrastructure.registry.models.dcm.DCM;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;

public class ConsumerDataFlow {
	DSM destDsm;
	DCM destDcm;
	public DSM getDestDsm() {
		return destDsm;
	}
	public void setDestDsm(DSM destDsm) {
		this.destDsm = destDsm;
	}
	public DCM getDestDcm() {
		return destDcm;
	}
	public void setDestDcm(DCM destDcm) {
		this.destDcm = destDcm;
	}

	
}
