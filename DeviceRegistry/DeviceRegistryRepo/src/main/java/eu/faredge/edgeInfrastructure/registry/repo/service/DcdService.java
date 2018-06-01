package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.faredge.edgeInfrastructure.registry.repo.database.DCDRepository;
import eu.faredge.edgeInfrastructure.registry.repo.database.DCMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.database.DSMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DCD;
import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;
import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;


@Service
public class DcdService implements DcdServiceInterface
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	//Init repositories. We need DSM, DCM DCD repositories
	@Autowired DCDRepository dcdRepository;
	@Autowired DCMRepository dcmRepository;
	@Autowired DSMRepository dsmRepository;

	@Override
	public List<DCD> findByDsmId(String dsmId)
	{
		log.debug("Find DCDs with dsmid=" + dsmId );
		
		List<DCD> dcdList = new ArrayList<DCD>();
		
		dcdList = dcdRepository.findByDataSourceManifestReferenceID(dsmId);
		
		log.debug("Returned " + dcdList.size()  + " DCDs");
		
		return dcdList;
	}

	@Override
	public List<DCD> findByDcmId(String dcmId)
	{
		log.debug("Find DCDs with dcmid=" + dcmId );
		
		List<DCD> dcdList = new ArrayList<DCD>();
		
		dcdList = dcdRepository.findByDataConsumerManifestReferenceID(dcmId);
		
		log.debug("Returned " + dcdList.size()  + " DCDs");
		
		return dcdList;
	}

	@Override
	public List<DCD> getAllDcds()
	{
		log.debug("Find all DCDs");
		
		List<DCD> dcdList = new ArrayList<DCD>();
		
		dcdList = dcdRepository.findAll();
		
		log.debug("Returned " + dcdList.size()  + " DCDs");
		
		return dcdList;
	}

	@Override
	public boolean deleteByUri(String uri)
	{
		log.debug("Delete DCD with uri=" + uri);
		
		DCD rtrnDcd = dcdRepository.findByUri(uri);
		
		if (rtrnDcd==null)
		{
			log.debug("DCD with uri=" + uri + " does not exist");
			return false;
		}
		else
		{
			dcdRepository.delete(rtrnDcd);
			log.debug("DCD with uri=" + uri + " deleted");
			return true;
		}
	}

	@Override
	public boolean addDcd(DCD dcd)
	{
		log.debug("add DCD with id=" + dcd.getId());
		
		//	check if DSM exists
		DSM dsm = dsmRepository.findByUri(dcd.getDataSourceManifestReferenceID());
		if (dsm==null)			
		{
			log.debug("DCD is invalid beacause dsm=" + dcd.getDataSourceManifestReferenceID() + " does not exists");
			return false;
		}
		
		//	check if DCM exists
		DCM dcm = dcmRepository.findByUri(dcd.getDataConsumerManifestReferenceID());
		if (dcm==null)
		{
			log.debug("DCD is invalid beacause dcm=" + dcd.getDataConsumerManifestReferenceID() + " does not exists");
			return false;
		}
		
		//	check if DCD exists
		DCD rtrnDcd = dcdRepository.findByDataSourceManifestReferenceIDAndDataConsumerManifestReferenceID(dcd.getDataSourceManifestReferenceID(),dcd.getDataConsumerManifestReferenceID());
		if (rtrnDcd!=null)
		{
			log.debug("DCD with this dcm and dcm allready exists");
			return false;
		}
		
		dcdRepository.save(dcd);
		
		log.debug("DCD with id=" + dcd.getDataSourceManifestReferenceID() + " saved");
		
		return true;
	}

	@Override
	public DCD findByDsmIdAndDcmId(String dsmId, String dcmId)
	{
		log.debug("Find DCDs with dsm = " + dsmId + " and dcm=" + dcmId);
		
		DCD rtrnDcd = dcdRepository.findByDataSourceManifestReferenceIDAndDataConsumerManifestReferenceID(dsmId, dcmId);
		
		//check if null
		if (rtrnDcd==null)
			log.debug("no DCD found");
		else
			log.debug("Returned DCD with id= " + rtrnDcd.getId());
		
		return rtrnDcd;
	}

	@Override
	public DCD findByUri(String uri)
	{
		log.debug("Find DCDs with uri = " + uri);
		
		DCD rtrnDcd = dcdRepository.findByUri(uri);
		
		//check if null
		if (rtrnDcd==null)
			log.debug("no DCD found");
		else
			log.debug("Returned DCD with uri= " + rtrnDcd.getUri());
		
		return rtrnDcd;
	}

	@Override
	public boolean delete(DCD dcd)
	{
		log.debug("Delete DCD with id=" + dcd.getId());
		
		DCD rtrnDcd = dcdRepository.findByUri(dcd.getUri());
		
		// check if exists before deleting
		if (rtrnDcd==null)
		{
			log.debug("DCD with id=" + dcd.getId() + " does not exist");
			return false;
		}
		else
		{
			dcdRepository.delete(rtrnDcd);
			log.debug("DCD with id=" + dcd.getId() + " deleted");
			return true;
		}
	}

}
