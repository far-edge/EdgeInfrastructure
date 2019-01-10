package eu.faredge.edgeInfrastructure.registry.repo.controller;

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

import eu.faredge.edgeInfrastructure.registry.repo.model.DCM;
import eu.faredge.edgeInfrastructure.registry.repo.service.DcmServiceInterface;

//This controller implements CRUD operations for data consumer manifest
@RestController
@RequestMapping("/dcm")
public class DcmController
{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// use dcm service for database communication
    @Autowired
    private DcmServiceInterface dcmService;

  //Get method for retrieving all data consumer manifests
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity <List<DCM>> findAll()
    {
    	log.debug("Find all DCMs");
    	
    	HttpStatus status;
    	
    	List<DCM> alldcm = dcmService.getAllDcms();
    	
    	// check if there are results
    	if (alldcm==null)
    	{
    		status =HttpStatus.NOT_FOUND;
    		log.debug("Repository returns no DCMs");
    	}
    	else
    	{
    		status =HttpStatus.OK;
    		log.debug("Reposistory returns " +alldcm.size() + " DCMs");
    	}
    	
    	log.debug("Find DCMs return status=" + status.toString());
    	
    	return new ResponseEntity<List<DCM>>(alldcm,status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DCM> findByUri(@RequestParam (name="id")String uri)
    {
    	log.debug("Find DCM with uri =" + uri);
    	
        DCM result;
        
        result = dcmService.findByUri(uri);
        
      //Check if result is null
        if (result==null)
        {
        	log.debug("Reposistory returns no DCMs. Status= " + HttpStatus.NOT_FOUND);
        	return new ResponseEntity<DCM>(result, HttpStatus.NOT_FOUND);
        }
        else 
        {
        	log.debug("Reposistory returns DSM with id= " + result.getId() + ". Status=" + HttpStatus.OK);
        	return new ResponseEntity<DCM>(result, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/macAddress/{id}", method = RequestMethod.GET)
    public ResponseEntity<DCM> findByMacAddress(@PathVariable("id") String macAddress)
    {
    	log.debug("Find DCM by macAddress =" + macAddress);
    	
        DCM result;
        
        result = dcmService.findByMacAddress(macAddress);
        
      //Check if result is null
        if (result==null)
        {
        	log.debug("Reposistory returns no DCMs. Status= " + HttpStatus.NOT_FOUND);
        	return new ResponseEntity<DCM>(result, HttpStatus.NOT_FOUND);
        }
        else 
        {
        	log.debug("Reposistory returns DSM with id= " + result.getId() + ". Status=" + HttpStatus.OK);
        	return new ResponseEntity<DCM>(result, HttpStatus.OK);
        }
    }
  
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DCM> addDcm(@RequestBody DCM dcm, UriComponentsBuilder builder)
    {
    	log.debug("Add DCM with id=:" + dcm.getId());
    	
		boolean flag = dcmService.addDcm(dcm);
		
		//Check if result is false
		if (flag == false)
		{
			log.debug("DCM didn't added. macAdrress already exists, or DCD allready exists. Status= " + HttpStatus.CONFLICT);
			return new ResponseEntity<DCM>(dcm, HttpStatus.CONFLICT);
		}
		else
		{
			log.debug("DCM with id= "+ dcm.getId() + " added with status:" + HttpStatus.CREATED);
			return new ResponseEntity<DCM>(dcm, HttpStatus.CREATED);
		}
		
	}
    
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteDcm(@RequestBody DCM dcm)
    {
    	log.debug("Delete a DCM with id=" + dcm.getId());
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dcmService.delete(dcm);
    	
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NOT_FOUND;
    		log.debug("DCM didn't deleted. DCM doesnot exists. Status= " + HttpStatus.NOT_FOUND);
    	}
    	else 
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DCM with id=" +dcm.getId() + " deleted. Status" + HttpStatus.OK);
    	}
    	return new ResponseEntity<String>(msg,status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteDcmByUri(String uri)
    {
    	log.debug("Delete DCM with uri=" + uri);    
    	
    	HttpStatus status;
    	String msg="";
    	
    	boolean flag = dcmService.deleteByUri(uri);
    	
    	//Check result
    	if (flag==false)
    	{
    		msg="false";
    		status = HttpStatus.NOT_FOUND;
    		log.debug("DCM didn't deleted. DCM doesnot exists. Status= " + HttpStatus.NOT_FOUND);
    	}
    	else
    	{
    		msg="true";
    		status = HttpStatus.OK;
    		log.debug("DCM deleted. Status" + HttpStatus.OK);    		
    	}
    	
    	return new ResponseEntity<String>(msg,status);
    }

}
