package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.List;

import eu.faredge.edgeInfrastructure.registry.repo.model.DCD;


public interface DcdServiceInterface {
	DCD findByDsmIdAndDcmId(String dsmId,String dcmId);
	DCD findByUri(String uri);
	List<DCD> findByDsmId(String dsmId);
	List<DCD> findByDcmId(String dcmId);
	List<DCD> getAllDcds();
	boolean delete(DCD dcd);
	boolean deleteByUri(String uri);	
	boolean addDcd(DCD dcd);

}
