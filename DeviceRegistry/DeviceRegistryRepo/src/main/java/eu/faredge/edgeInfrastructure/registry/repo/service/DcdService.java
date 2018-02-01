package eu.faredge.edgeInfrastructure.registry.repo.service;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired DCDRepository dcdRepository;
	@Autowired DCMRepository dcmRepository;
	@Autowired DSMRepository dsmRepository;

	@Override
	public List<DCD> findByDsmId(String dsmId)
	{
		List<DCD> dcdList = new ArrayList<DCD>();
		dcdList = dcdRepository.findByDsmId(dsmId);
		return dcdList;
	}

	@Override
	public List<DCD> findByDcmId(String dcmId)
	{
		List<DCD> dcdList = new ArrayList<DCD>();
		dcdList = dcdRepository.findByDcmId(dcmId);
		return dcdList;
	}

	@Override
	public List<DCD> getAllDcds()
	{
		List<DCD> dcdList = new ArrayList<DCD>();
		dcdList = dcdRepository.findAll();
		return dcdList;
	}

	@Override
	public boolean deleteByUri(String uri)
	{
		DCD rtrnDcd = dcdRepository.findByUri(uri);
		if (rtrnDcd==null)
		{
			return false;
		}
		else
		{
			dcdRepository.delete(rtrnDcd);
			return true;
		}
	}

	@Override
	public boolean addDcd(DCD dcd) {
		//	check if DSM exists
		DSM dsm = dsmRepository.findByUri(dcd.getDsmId());
		if (dsm==null)
			return false;
		
		//	check if DCM exists
		DCM dcm = dcmRepository.findByUri(dcd.getDcmId());
		if (dcm==null)
			return false;
		
		//	check if DCD exists
		DCD rtrnDcd = dcdRepository.findByDsmIdAndDcmId(dcd.getDsmId(),dcd.getDcmId());
		if (rtrnDcd!=null)
			return false;
		
		dcdRepository.save(dcd);
		return true;
	}

	@Override
	public DCD findByDsmIdAndDcmId(String dsmId, String dcmId)
	{
		DCD rtrnDcd = dcdRepository.findByDsmIdAndDcmId(dsmId, dcmId);
		return rtrnDcd;
	}

	@Override
	public DCD findByUri(String uri) {
		DCD rtrnDcd = dcdRepository.findByUri(uri);
		return rtrnDcd;
	}

	@Override
	public boolean delete(DCD dcd) {
		DCD rtrnDcd = dcdRepository.findByUri(dcd.getUri());
		if (rtrnDcd==null)
		{
			return false;
		}
		else
		{
			dcdRepository.delete(rtrnDcd);
			return true;
		}
	}

}
