package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.faredge.edgeInfrastructure.registry.repo.database.DSMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;

@Service
public class DsmService implements DsmServiceInterface
{
	@Autowired 
	private DSMRepository dsmrepo;

	@Override
	public DSM findByUri(String uri)
	{
		DSM dsmObject = dsmrepo.findByUri(uri);
		return dsmObject;
	}

	@Override
	public DSM findByMacAddress(String macAddress)
	{
		DSM dsmObject = dsmrepo.findByMacAddress(macAddress);
		return dsmObject;
	}

	@Override
	public List<DSM> getAllDsms()
	{
		List<DSM> dsmList = new ArrayList<DSM>();
		dsmList = dsmrepo.findAll();
		return dsmList;
	}

	@Override
	public List<DSM> findByDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID)
	{
		List<DSM> dsmList = new ArrayList<DSM>();
		dsmList = dsmrepo.findByDataSourceDefinitionReferenceID(dataSourceDefinitionReferenceID);
		return dsmList;
	}

	@Override
	public synchronized boolean addDsm(DSM dsm)
	{
        DSM retdcm = dsmrepo.findByUri(dsm.getUri()); 	
            if (retdcm!=null)
	           return false;
        retdcm = dsmrepo.findByMacAddress(dsm.getMacAddress());
        if (retdcm!=null)
	           return false;
        dsmrepo.save(dsm);
	    return true;
	}

	@Override
	public synchronized boolean delete(DSM dsm)
	{
		DSM rtnDcm = dsmrepo.findByUri(dsm.getUri());
		if (rtnDcm==null)
		{
			return false;
		}
		else
		{
			dsmrepo.delete(rtnDcm);
			return true;
		}
			
	}

	@Override
	public boolean deleteByUri(String uri) {
		DSM rtnDsm = dsmrepo.findByUri(uri);
		if (rtnDsm==null)
		{
			return false;
		}
		else
		{
			dsmrepo.delete(rtnDsm);
			return true;
		}
	}
	

}
