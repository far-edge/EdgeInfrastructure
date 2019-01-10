package eu.sensap.dataRouting.business;

import eu.faredge.edgeInfrastructure.registry.models.dcd.DCD;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;

public class BusinessImp {

	public BusinessImp()
	{
		
	}
	
	
	public DSM registerToRegistry (DSM dsm)	
	{
		
		return dsm;		
	}
	
	public boolean createDataFlow(DSM sourceDsm, DSM destDSM)
	{
		return true;
	}
	
	public DCD registerDCD (DCD dcd)
	{
		return dcd;
	}
}
