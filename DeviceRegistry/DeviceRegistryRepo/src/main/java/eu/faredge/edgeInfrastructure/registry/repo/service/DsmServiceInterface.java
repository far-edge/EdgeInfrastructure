package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.List;

import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;


public interface DsmServiceInterface
{
	DSM findByUri(String uri);
	DSM findByMacAddress(String macAddress);
	List<DSM> getAllDsms();
	List<DSM> findByDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID);
	boolean delete(DSM dsm);
	boolean deleteByUri(String uri);
	boolean deleteById(String uri);
	boolean addDsm(DSM dsm);

}
