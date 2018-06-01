package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.faredge.edgeInfrastructure.registry.repo.database.DSMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;


@Service
public class DsmService implements DsmServiceInterface
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Init repositories. We need DSM repository
	@Autowired 
	private DSMRepository dsmrepo;

	@Override
	public DSM findByUri(String uri)
	{
		log.debug("Find DSM with uri=" + uri );
		
		DSM dsmObject = dsmrepo.findByUri(uri);
		
		//check if null
		if (dsmObject==null)
			log.debug("no DSM found");
		else
			log.debug("Returned DSM with id= " + dsmObject.getId());
				
		return dsmObject;
	}

	@Override
	public DSM findByMacAddress(String macAddress)
	{
		log.debug("Find DSM with macAddress=" + macAddress );
		
		DSM dsmObject = dsmrepo.findByMacAddress(macAddress);
		
		//check if null
		if (dsmObject==null)
			log.debug("no DSM found");
		else
			log.debug("Returned DSM with macAddress= " + dsmObject.getMacAddress());
		
		return dsmObject;
	}

	@Override
	public List<DSM> getAllDsms()
	{
		log.debug("Find all DSMs");
		
		List<DSM> dsmList = new ArrayList<DSM>();
		
		dsmList = dsmrepo.findAll();
		
		log.debug("Returned " + dsmList.size()  + " DSMs");
		
		return dsmList;
	}

	@Override
	public List<DSM> findByDataSourceDefinitionReferenceID(String dataSourceDefinitionReferenceID)
	{
		log.debug("Find DSM with dsd=" + dataSourceDefinitionReferenceID );
		
		List<DSM> dsmList = new ArrayList<DSM>();
		
		dsmList = dsmrepo.findByDataSourceDefinitionReferenceID(dataSourceDefinitionReferenceID);
		
		log.debug("Returned " + dsmList.size()  + " DSMs");
		
		return dsmList;
	}

	@Override
	public synchronized boolean addDsm(DSM dsm)
	{
		log.debug("add DSM with id=" + dsm.getId());
		
		//check if DSM with this id exists
        DSM retdcm = dsmrepo.findById(dsm.getId()); 	
        if (retdcm!=null)
        {
        	log.debug("DSM with this id allready exists");
        	return false;
        }
        
      //check if DSM with this macAddress exists
        retdcm = dsmrepo.findByMacAddress(dsm.getMacAddress());
        if (retdcm!=null)
        {
        	log.debug("DSM with this macAdrress allready exists");
        	return false;
        }
        
        dsmrepo.save(dsm);
        
        log.debug("DSM with id=" + dsm.getId() + " saved");
        
	    return true;
	}

	@Override
	public synchronized boolean delete(DSM dsm)
	{
		log.debug("Delete DSM with id=" + dsm.getId());
		
		// check if exists before deleting
		DSM rtnDcm = dsmrepo.findById(dsm.getId());
		if (rtnDcm==null)
		{
			log.debug("DSM with id=" + dsm.getId() + " does not exist");
			return false;
		}
		else
		{
			dsmrepo.delete(rtnDcm);
			log.debug("DSM with id=" + dsm.getId() + " deleted");
			return true;
		}
			
	}

	@Override
	public boolean deleteByUri(String uri)
	{
		log.debug("Delete DSM with uri=" + uri);
		
		// check if exists before deleting
		DSM rtnDsm = dsmrepo.findByUri(uri);
		if (rtnDsm==null)
		{
			log.debug("DSM with uri=" + uri + " does not exist");
			return false;
		}
		else
		{
			boolean flag=false;
			try
			{
				dsmrepo.delete(rtnDsm);
				flag=true;
				log.debug("DSM with uri=" + uri + " deleted");
			}
			catch (Exception e)
			{
				flag=false;
				log.debug("DSM with uri=" + uri + " cannot be deleted!!!!");
				e.printStackTrace();
			}
			return flag;
		}
	}
	
	@Override
	public boolean deleteById(String id)
	{
		log.debug("Delete DSM with id=" + id);
		
		// check if exists before deleting
		DSM rtnDsm = dsmrepo.findById(id);
		if (rtnDsm==null)
		{
			log.debug("DSM with id=" + id + " does not exist");
			return false;
		}
		else
		{
			boolean flag=false;
			try
			{
				dsmrepo.delete(rtnDsm);
				flag=true;
				log.debug("DSM with id=" + id + " deleted");
			}
			catch (Exception e)
			{
				flag=false;
				log.debug("DSM with id=" + id + " cannot be deleted!!!!");
				e.printStackTrace();
			}
			return flag;
		}
	}
	

}
