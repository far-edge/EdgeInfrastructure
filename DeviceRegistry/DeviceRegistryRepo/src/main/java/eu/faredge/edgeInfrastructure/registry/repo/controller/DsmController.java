package eu.faredge.edgeInfrastructure.registry.repo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;
import eu.faredge.edgeInfrastructure.registry.repo.service.DsmServiceInterface;

@RestController
@RequestMapping("/dsm")
public class DsmController
{
	
	@Autowired
	private DsmServiceInterface dsmService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity <List<DSM>> findAll()
    {
    	List<DSM> alldsm = dsmService.getAllDsms();
    	if (alldsm==null)
    		return new ResponseEntity<List<DSM>>(alldsm,HttpStatus.NOT_FOUND);
    	else
    		return new ResponseEntity<List<DSM>>(dsmService.getAllDsms(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DSM> findByUri(String uri)
    {
        DSM result;
        result = dsmService.findByUri(uri);
        if (result==null)
        	return new ResponseEntity<DSM>(result, HttpStatus.NOT_FOUND);
        else 
        {
        	return new ResponseEntity<DSM>(result, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/dsd", method = RequestMethod.GET)
    public ResponseEntity<List<DSM>> findByDsd(String dsd)
    {
        List<DSM> result = dsmService.findByDataSourceDefinitionReferenceID(dsd);
        if (result==null)
        	return new ResponseEntity<List<DSM>>(result, HttpStatus.NOT_FOUND);
        else 
        {
        	return new ResponseEntity<List<DSM>>(result, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/macAddress/{macAddress}", method = RequestMethod.GET)
    public ResponseEntity<DSM> findByMacAddress(@PathVariable("macAddress") String macAddress)
    {
        DSM result;
        result = dsmService.findByMacAddress(macAddress);
        if (result==null)
        	return new ResponseEntity<DSM>(result, HttpStatus.NOT_FOUND);
        else 
        {
        	return new ResponseEntity<DSM>(result, HttpStatus.OK);
        }
    }
  
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DSM> addDsm(@RequestBody DSM dsm, UriComponentsBuilder builder)
    {
		boolean flag = dsmService.addDsm(dsm);
		if (flag == false)
		{
			return new ResponseEntity<DSM>(dsm, HttpStatus.FORBIDDEN);
		}
		else
		{
			return new ResponseEntity<DSM>(dsm, HttpStatus.CREATED);
		}
		
	}
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteDsm(@RequestBody DSM dsm)
    {
    	boolean flag = dsmService.delete(dsm);
    	HttpStatus status;
    	if (flag==false)
    		status = HttpStatus.NO_CONTENT;
    	else 
    		status = HttpStatus.OK;
    	return new ResponseEntity<String>("deleted",status);
    }


}
