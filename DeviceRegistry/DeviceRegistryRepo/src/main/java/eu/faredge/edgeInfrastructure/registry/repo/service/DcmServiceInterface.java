package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.List;

import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;

public interface DcmServiceInterface
{
	DCM findByUri(String uri);
	DCM findByMacAddress(String macAddress);
	List<DCM> getAllDcms();
	boolean delete(DCM dcm);
	boolean deleteByUri(String uri);
	boolean addDcm(DCM dcm);
//	void deleteByid(String Id);
//	void deleteBymacAddress(String macAddress);
}
