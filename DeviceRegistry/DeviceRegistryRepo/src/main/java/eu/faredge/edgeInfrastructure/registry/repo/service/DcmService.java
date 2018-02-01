package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.faredge.edgeInfrastructure.registry.repo.database.DCMRepository;
import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;

@Service
public class DcmService implements DcmServiceInterface
{

	@Autowired 
	private DCMRepository dcmrepo;
	
	@Override
	public DCM findByUri(String uri) 
	{
		DCM dcmObject = dcmrepo.findByUri(uri);
		return dcmObject;
	}

	@Override
	public DCM findByMacAddress(String macAddress)
	{
		DCM dcmObject = dcmrepo.findByMacAddress(macAddress);
		return dcmObject;
	}

	@Override
	public List<DCM> getAllDcms()
	{
		List<DCM> dcmList = new ArrayList<DCM>();
		dcmList = dcmrepo.findAll();
		return dcmList;
	}

	@Override
	public synchronized boolean addDcm(DCM dcm)
	{
        DCM retdcm = dcmrepo.findByUri(dcm.getUri()); 	
        if (retdcm!=null)
	           return false;
        retdcm = dcmrepo.findByMacAddress(dcm.getMacAddress());
        if (retdcm!=null)
	           return false;
        dcmrepo.save(dcm);
	    return true;
	}

	@Override
	public synchronized boolean delete(DCM dcm)
	{
		DCM rtnDcm = dcmrepo.findByUri(dcm.getUri());
		if (rtnDcm==null)
		{
			return false;
		}
		else
		{
			dcmrepo.delete(rtnDcm);
			return true;
		}
			
	}

	@Override
	public boolean deleteByUri(String uri) {
		DCM rtnDcm = dcmrepo.findByUri(uri);
		if (rtnDcm==null)
		{
			return false;
		}
		else
		{
			dcmrepo.delete(rtnDcm);
			return true;
		}
	}

}
