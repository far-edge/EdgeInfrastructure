package eu.faredge.edgeInfrastructure.registry.repo.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.faredge.edgeInfrastructure.registry.repo.model.DataSourceDefinitionInterfaceParameters;

@RepositoryRestResource(exported = false)
public interface DataSourceDefinitionInterfaceParametersRepository extends JpaRepository<DataSourceDefinitionInterfaceParameters, Integer>
{

}
