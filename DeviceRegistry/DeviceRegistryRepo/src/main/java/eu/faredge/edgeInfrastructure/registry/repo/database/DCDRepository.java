package eu.faredge.edgeInfrastructure.registry.repo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.faredge.edgeInfrastructure.registry.repo.model.DCD;

public interface DCDRepository extends JpaRepository<DCD, Integer> {
	List<DCD> findByDsmId(String dsmId);
	List<DCD> findByDcmId(String dcmId);
	DCD findByUri(String uri);
	DCD findByDsmIdAndDcmId(String dsmId,String dcmId);
//	boolean deleteByUri(String uri);
}
