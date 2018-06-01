package eu.faredge.edgeInfrastructure.registry.repo.database;

import org.springframework.data.jpa.repository.JpaRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;

public interface DCMRepository extends JpaRepository<DCM, Integer>
{
	DCM findByUri(String uri);
	DCM findByMacAddress(String macAddress);
	DCM findById(String id);
//	boolean deleteByUri(String uri);
//	List<DCM> findAll();
//	void delete (@Param ("id") int id);
//	DCM save (DCM dcm);
}
