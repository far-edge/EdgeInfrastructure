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

import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;
import eu.faredge.edgeInfrastructure.registry.repo.service.DcmServiceInterface;

@RestController
@RequestMapping("/dcm")
public class DcmController
{

    @Autowired
    private DcmServiceInterface dcmService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity <List<DCM>> findAll()
    {
    	List<DCM> alldcm = dcmService.getAllDcms();
    	if (alldcm==null)
    		return new ResponseEntity<List<DCM>>(alldcm,HttpStatus.NOT_FOUND);
    	else
    		return new ResponseEntity<List<DCM>>(dcmService.getAllDcms(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DCM> findByUri(String uri)
    {
        DCM result;
        result = dcmService.findByUri(uri);
        if (result==null)
        	return new ResponseEntity<DCM>(result, HttpStatus.NOT_FOUND);
        else 
        {
        	return new ResponseEntity<DCM>(result, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/macAddress/{macAddress}", method = RequestMethod.GET)
    public ResponseEntity<DCM> findByMacAddress(@PathVariable("macAddress") String macAddress)
    {
        DCM result;
        result = dcmService.findByMacAddress(macAddress);
        if (result==null)
        	return new ResponseEntity<DCM>(result, HttpStatus.NOT_FOUND);
        else 
        {
        	return new ResponseEntity<DCM>(result, HttpStatus.OK);
        }
    }
  
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DCM> addDcm(@RequestBody DCM dcm, UriComponentsBuilder builder)
    {
		boolean flag = dcmService.addDcm(dcm);
		if (flag == false)
		{
			return new ResponseEntity<DCM>(dcm, HttpStatus.FORBIDDEN);
		}
		else
		{
			return new ResponseEntity<DCM>(dcm, HttpStatus.CREATED);
		}
		
	}
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteDcm(@RequestBody DCM dcm)
    {
    	boolean flag = dcmService.delete(dcm);
    	HttpStatus status;
    	if (flag==false)
    		status = HttpStatus.NO_CONTENT;
    	else 
    		status = HttpStatus.OK;
    	return new ResponseEntity<String>("deleted",status);
    }

}
