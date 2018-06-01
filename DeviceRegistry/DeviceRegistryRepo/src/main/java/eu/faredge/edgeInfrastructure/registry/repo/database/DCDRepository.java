package eu.faredge.edgeInfrastructure.registry.repo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.faredge.edgeInfrastructure.registry.repo.model.DCD;

public interface DCDRepository extends JpaRepository<DCD, Integer> {
	List<DCD> findByDataSourceManifestReferenceID(String dsmId);
	List<DCD> findByDataConsumerManifestReferenceID(String dcmId);
	DCD findByUri(String uri);
	DCD findByDataSourceManifestReferenceIDAndDataConsumerManifestReferenceID(String dsmId,String dcmId);
//	boolean deleteByUri(String uri);
}
