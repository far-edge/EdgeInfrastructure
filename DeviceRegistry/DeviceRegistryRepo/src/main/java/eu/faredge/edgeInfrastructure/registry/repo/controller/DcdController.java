package eu.faredge.edgeInfrastructure.registry.repo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import eu.faredge.edgeInfrastructure.registry.repo.model.DCD;
import eu.faredge.edgeInfrastructure.registry.repo.service.DcdServiceInterface;

//This controller implements CRUD operations for data channel descriptor
@RestController
@RequestMapping("/dcd")
public class DcdController
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	// use dcd service for database communication
	@Autowired
	private DcdServiceInterface dcdService;
	
	//Get method for retrieving all data channel descriptors
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<DCD>> findAll()
	{
		log.debug("Find all DCDs");
		
		HttpStatus status;
		List<DCD> alldcd = dcdService.getAllDcds();
		
		// check if there are results
		if (alldcd ==null)
		{
			status = HttpStatus.NOT_FOUND;
			log.debug("Repository return no DCDs");
		}
		else
		{
			status = HttpStatus.OK;
			log.debug("Find DCDs returns " +alldcd.size() + " DCDs");
		}
		
		log.debug("Find DCDs return status=" + status.toString());
		
		return new ResponseEntity<List<DCD>>(alldcd,status);
	}
	
    @RequestMapping(value = "/dsm", method = RequestMethod.GET)
    public ResponseEntity<List<DCD>> findByDataSourceManifest(@RequestParam (name="id")String dsmId)
    {
    	log.debug("Find DCDs with dsm=" + dsmId);
        List<DCD> result = new ArrayList<DCD>();
        HttpStatus status;
        
        result = dcdService.findByDsmId(dsmId);
        
      //Check if result is null
        if (result==null)
        {
        	status =HttpStatus.NOT_FOUND;
        	log.debug("Repository returns no DCDs");
        }
        else
        {
        	status =HttpStatus.OK;
        	log.debug("Find DCDs returns " +result.size() + " DCDs");
        } 
        
        log.debug("Find DCDs return status=" + status.toString());
        
        return new ResponseEntity<List<DCD>>(result, status);
    }
    
    @RequestMapping(value = "/dcm", method = RequestMethod.GET)
    public ResponseEntity<List<DCD>> findByDataConsumerManifest(@RequestParam (name="id")String dcmId)
    {
    	log.debug("Find DCDs with dcm=" + dcmId);
    	
    	HttpStatus status;
    	List<DCD> result = new ArrayList<DCD>();
        
    	//Check if result is null
        result = dcdService.findByDcmId(dcmId);
        if (result==null)
        {
        	status =HttpStatus.NOT_FOUND;
        	log.debug("Repository returns no DCDs");
        }
        else
        {
        	status =HttpStatus.OK;
        	log.debug("Repository returns " +result.size() + " DCDs");
        }
        
        log.debug("Find DCDs return status=" + status.toString());
        
        return new ResponseEntity<List<DCD>>(result, status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DCD> findByUri(@RequestParam (name="id") String uri)
    {
    	log.debug("Find DCD with uri=" + uri);
        
        HttpStatus status;
        
      //Check if result is null
        DCD result = dcdService.findByUri(uri);
        if (result==null)
        {
        	status =HttpStatus.NOT_FOUND;
        	log.debug("Repository returns no DSDs");
        }
        else
        {
        	status =HttpStatus.OK;
        	log.debug("Repository returns DCD with id=" +result.getId());
        }
        log.debug("Returns status= " +status);

        return new ResponseEntity<DCD>(result, status);
    }
    
    @RequestMapping(value = "/{dsm}/{dcm}", method = RequestMethod.GET)
    public ResponseEntity<DCD> findByDsmByDcm(@PathVariable("dsm") String dsmId,@PathVariable("dcm") String dcmId)
    {
    	log.debug("Find DCD with dsm=" + dsmId + " and dcm=" + dcmId);
        
    	HttpStatus status;
        
    	//Check if result is null
        DCD result = dcdService.findByDsmIdAndDcmId(dsmId, dcmId);
        if (result==null)
        {
        	status =HttpStatus.NOT_FOUND;
        	log.debug("Repository Returns no DCDs");
        }
        else
        {
        	status =HttpStatus.OK;
        	log.debug("Repository returns DCD with id=" +result.getId());
        }
        
        log.debug("Returns status= " +status);
        
        return new ResponseEntity<DCD>(result, status);
    }
	
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DCD> addDcd(@RequestBody DCD dcd, UriComponentsBuilder builder)
    {
    	log.debug("Add DCD with id=" + dcd.getId());
		
    	boolean flag = dcdService.addDcd(dcd);
		
    	//Check if result is false
		if (flag == false)
		{
			log.debug("DCD didn't added. dcm or dsm doesnot exist, or DCD allready exists. Status= " + HttpStatus.FORBIDDEN);
			return new ResponseEntity<DCD>(dcd, HttpStatus.FORBIDDEN);
		}
		else
		{
			log.debug("DCD with id=" +dcd.getId() + " added with status" + HttpStatus.CREATED);
			return new ResponseEntity<DCD>(dcd, HttpStatus.CREATED);			
		}
		
	}
    
    @RequestMapping(value = "/uri", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteByUri(@RequestParam (name="id") String dcdId)
    {
    	log.debug("Delete DCD with id=" + dcdId);
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dcdService.deleteByUri(dcdId);
    	
    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NO_CONTENT;
    		log.debug("DCD didn't deleted. DCD doesnot exists. Status= " + HttpStatus.NO_CONTENT);
    	}
    	else 
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DCD with id=" +dcdId + " deleted. Status" + HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>(msg,status);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)    
    public ResponseEntity<String> delete(@RequestBody DCD dcd)
    {
    	log.debug("Delete DCD with id=" + dcd.getId());
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dcdService.delete(dcd);
    	    	
    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NO_CONTENT;
    		log.debug("DCD didn't deleted. DCD doesnot exists. Status= " + HttpStatus.NO_CONTENT);
    	}
    	else 
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DCD deleted. Status" + HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>(msg,status);
    }

}
