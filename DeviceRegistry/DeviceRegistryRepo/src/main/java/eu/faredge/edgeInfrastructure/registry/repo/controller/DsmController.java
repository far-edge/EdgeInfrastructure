package eu.faredge.edgeInfrastructure.registry.repo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import eu.faredge.edgeInfrastructure.registry.repo.model.DSM;
import eu.faredge.edgeInfrastructure.registry.repo.service.DsmServiceInterface;

// This controller implements CRUD operations for data source manifest
@RestController
@RequestMapping("/dsm")
public class DsmController
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	// use dsm service for database communication
	@Autowired
	private DsmServiceInterface dsmService;
		
	//Get method for retrieving all data source manifests
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity <List<DSM>> findAll()
    {
		log.debug("Find all DSMs");
		
		HttpStatus status;
		
		List<DSM> alldsm = dsmService.getAllDsms();
    	
		// check if there are results
    	if (alldsm==null)
    	{
    		status = HttpStatus.NOT_FOUND;
    		log.debug("Repository returns no DSMs");
    	}
    	else
    	{
    		status = HttpStatus.OK;
    		log.debug("Reposistory returns " +alldsm.size() + " DSMs");
    	}
    	
    	log.debug("Find DSMs return status=" + status.toString());
    	
    	return new ResponseEntity<List<DSM>>(alldsm,status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DSM> findByUri(@RequestParam (name="id")String uri)
    {
    	log.debug("Find DSM with uri =" + uri);
    	
        DSM result;
        HttpStatus status;
        
        result = dsmService.findByUri(uri);
        
        //Check if result is null
        if (result==null)
        {
        	status = HttpStatus.NOT_FOUND;
        	log.debug("Reposistory returns no DSMs");        	
        }
        else 
        {
        	status = HttpStatus.OK;
        	log.debug("Reposistory returns DSM with id= " + result.getId());
        }
        
        log.debug("Find DSM return status=" + status.toString());
        
        return new ResponseEntity<DSM>(result, status);
    }
    
    @RequestMapping(value = "/dsd", method = RequestMethod.GET)
    public ResponseEntity<List<DSM>> findByDsd(@RequestParam(name ="id") String dsd)
    {
    	log.debug("Find DSMs by dsd =" + dsd);
    	HttpStatus status;
    	
        List<DSM> result = dsmService.findByDataSourceDefinitionReferenceID(dsd);
        
      //Check if result is null
        if (result==null)
        {        	
        	status=HttpStatus.NOT_FOUND;
        	log.debug("Reposistory returns no DSMs");
        }        	
        else 
        {
        	status = HttpStatus.OK;
        	log.debug("Reposistory returns " + result.size() +" DSMs");
        }
        
        log.debug("Find DSMs return status=" + status.toString());
        
        return new ResponseEntity<List<DSM>>(result, status);
    }
    
    @RequestMapping(value = "/macAddress/{macAddress}", method = RequestMethod.GET)
    public ResponseEntity<DSM> findByMacAddress(@PathVariable("macAddress") String macAddress)
    {
    	log.debug("Find DSMs by macAddress =" + macAddress);
    	
    	HttpStatus status;
        DSM result;
        
      //Check if result is null
        result = dsmService.findByMacAddress(macAddress);
        if (result==null)
        {
        	status= HttpStatus.NOT_FOUND;
        	log.debug("Reposistory returns no DSMs");
        }        	
        else 
        {
        	status = HttpStatus.OK;
        	log.debug("Reposistory returns DSMs with id =" + result.getId());        	
        }
        
        log.debug("Find DSMs return status=" + status.toString());
        
        return new ResponseEntity<DSM>(result, HttpStatus.OK);
    }
  
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DSM> addDsm(@RequestBody DSM dsm, UriComponentsBuilder builder)
    {
    	log.debug("Add DSM with id=:" + dsm.getId());
    	
    	HttpStatus status;
		boolean flag = dsmService.addDsm(dsm);
		
		//Check if result is false
		if (flag == false)
		{
			status = HttpStatus.CONFLICT;
			log.debug("DSM didn't added. macAdrress already exists, or DCD allready exists. Status= " + HttpStatus.CONFLICT);
		}
		else
		{
			status = HttpStatus.CREATED;
			log.debug("DSM with id= "+ dsm.getId() + " added with status:" + status);
		}
		
		return new ResponseEntity<DSM>(dsm, status);
	}
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteDsm(@RequestBody (required=false) DSM dsm) throws Exception
    {    	
    	log.debug("Delete a DSM with id=" + dsm.getId());

    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dsmService.delete(dsm);

    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NOT_FOUND;
    		log.debug("DSM didn't deleted. DSM doesnot exists. Status= " + HttpStatus.NOT_FOUND);
    	}
    	else
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DSM with id=" +dsm.getId() + " deleted. Status" + HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>(msg ,status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.DELETE) 
    public ResponseEntity<String> deleteDsmByUri(@RequestParam ("uri") String uri) 
    {
    	log.debug("Delete DSM with uri=" + uri);    	
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dsmService.deleteByUri(uri);
    	
    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NOT_FOUND;
    		log.debug("DSM didn't deleted. DSM doesnot exists. Status= " + HttpStatus.NOT_FOUND);
    	}
    	else
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DSM deleted. Status" + HttpStatus.OK);    		
    	}
    	
    	return new ResponseEntity<String>(msg,status);
    }
    
    @RequestMapping(value = "/id", method = RequestMethod.DELETE) 
    public ResponseEntity<String> deleteDsmById(@RequestParam ("id") String id) 
    {
    	log.debug("Delete DSM with id=" + id);    	
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dsmService.deleteById(id);
    	
    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NOT_FOUND;
    		log.debug("DSM didn't deleted. DSM doesnot exists. Status= " + HttpStatus.NOT_FOUND);
    	}
    	else
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DSM deleted. Status" + HttpStatus.OK);    		
    	}
    	
    	return new ResponseEntity<String>(msg,status);
    }
    
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		String error;
		// error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error = ex.getMessage();
		return new ResponseEntity<String>(error, HttpStatus.BAD_REQUEST);
	}
    
}
