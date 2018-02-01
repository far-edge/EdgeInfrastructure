package eu.faredge.edgeInfrastructure.registry.repo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;

public interface DSMRepository extends JpaRepository<DSM, Integer>
{
	DSM findByUri(String uri);
	DSM findByMacAddress(String macAddress);
	List<DSM> findByDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID);
	//boolean deleteByUri(String uri);
	//List<DSM> findAll();
	//delete (DSM);
	//save (DSM);
}
