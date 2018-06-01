package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.faredge.edgeInfrastructure.registry.repo.database.DCMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;


@Service
public class DcmService implements DcmServiceInterface
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Init repositories. We need DCM repository
	@Autowired 
	private DCMRepository dcmrepo;
	
	@Override
	public DCM findByUri(String uri) 
	{
		log.debug("Find DCM with uri=" + uri );
		
		DCM dcmObject = dcmrepo.findByUri(uri);
		
		//check if null
		if (dcmObject==null)
			log.debug("no DCM found");
		else
			log.debug("Returned DCM with id= " + dcmObject.getId());
		
		return dcmObject;
	}

	@Override
	public DCM findByMacAddress(String macAddress)
	{
		log.debug("Find DCM with macAddress=" + macAddress );
		
		DCM dcmObject = dcmrepo.findByMacAddress(macAddress);
		
		//check if null
		if (dcmObject==null)
			log.debug("no DCM found");
		else
			log.debug("Returned DCM with macAddress= " + dcmObject.getMacAddress());
		
		return dcmObject;
	}

	@Override
	public List<DCM> getAllDcms()
	{
		log.debug("Find all DCMs");
		
		List<DCM> dcmList = new ArrayList<DCM>();
		
		dcmList = dcmrepo.findAll();
		
		log.debug("Returned " + dcmList.size()  + " DCMs");
		
		return dcmList;
	}

	@Override
	public synchronized boolean addDcm(DCM dcm)
	{
		log.debug("add DCM with id=" + dcm.getId());
		
        //check if DCM with this id exists
		DCM retdcm = dcmrepo.findById(dcm.getId());
        if (retdcm!=null)
        {        	
        	log.debug("DCM with this id allready exists");
        	return false;
        }
        
        //check if DCM with this macAddress exists
        retdcm = dcmrepo.findByMacAddress(dcm.getMacAddress());
        if (retdcm!=null)
        {
        	log.debug("DCM with this macAdrress allready exists");
	        return false;
        }
        
        dcmrepo.save(dcm);
        
        log.debug("DCM with id=" + dcm.getId() + " saved");
        
	    return true;
	}
	
	@Override
	public synchronized boolean delete(DCM dcm)
	{
		log.debug("Delete DCM with id=" + dcm.getId());
		
		// check if exists before deleting
		DCM rtnDcm = dcmrepo.findById(dcm.getId());
		if (rtnDcm==null)
		{
			log.debug("DCM with id=" + dcm.getId() + " does not exist");
			return false;
		}
		else
		{			
			dcmrepo.delete(rtnDcm);
			log.debug("DCM with id=" + dcm.getId() + " deleted");
			return true;
		}
			
	}

	@Override
	public boolean deleteByUri(String uri)
	{
		log.debug("Delete DCM with uri=" + uri);
		
		// check if exists before deleting
		DCM rtnDcm = dcmrepo.findByUri(uri);
		if (rtnDcm==null)
		{
			log.debug("DCM with uri=" + uri + " does not exist");
			return false;
		}
		else
		{
			dcmrepo.delete(rtnDcm);
			log.debug("DCM with uri=" + uri + " deleted");
			return true;
		}
	}

}
