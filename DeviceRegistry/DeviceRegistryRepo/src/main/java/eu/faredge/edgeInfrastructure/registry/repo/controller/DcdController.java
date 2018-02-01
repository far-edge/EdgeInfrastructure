package eu.faredge.edgeInfrastructure.registry.repo.controller;

import java.util.ArrayList;
import java.util.List;

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

@RestController
@RequestMapping("/dcd")
public class DcdController
{
	@Autowired
	private DcdServiceInterface dcdService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<DCD>> findAll()
	{
		HttpStatus status;
		List<DCD> alldcd = dcdService.getAllDcds();
		if (alldcd ==null)
			status = HttpStatus.NOT_FOUND;
		else
			status = HttpStatus.OK;
		return new ResponseEntity<List<DCD>>(alldcd,status);
	}
	
    @RequestMapping(value = "/dsm", method = RequestMethod.GET)
    public ResponseEntity<List<DCD>> findByDataSourceManifest(@RequestParam (name="id")String dsmId)
    {
        List<DCD> result = new ArrayList<DCD>();
        HttpStatus status;
        
        result = dcdService.findByDsmId(dsmId);
        if (result==null)
        	status =HttpStatus.NOT_FOUND;
        else
        	status =HttpStatus.OK;

        return new ResponseEntity<List<DCD>>(result, status);
    }
    
    @RequestMapping(value = "/dcm", method = RequestMethod.GET)
    public ResponseEntity<List<DCD>> findByDataConsumerManifest(@RequestParam (name="id")String dcmId)
    {
        List<DCD> result = new ArrayList<DCD>();
        HttpStatus status;
        
        result = dcdService.findByDcmId(dcmId);
        if (result==null)
        	status =HttpStatus.NOT_FOUND;
        else
        	status =HttpStatus.OK;

        return new ResponseEntity<List<DCD>>(result, status);
    }
    
    @RequestMapping(value = "/uri", method = RequestMethod.GET)
    public ResponseEntity<DCD> findByUri(@RequestParam (name="id") String uri)
    {
        
        HttpStatus status;
        
        DCD result = dcdService.findByUri(uri);
        if (result==null)
        	status =HttpStatus.NOT_FOUND;
        else
        	status =HttpStatus.OK;

        return new ResponseEntity<DCD>(result, status);
    }
    
    @RequestMapping(value = "/{dsm}/{dcm}", method = RequestMethod.GET)
    public ResponseEntity<DCD> findByDsmByDcm(@PathVariable("dsm") String dsmId,@PathVariable("dcm") String dcmId)
    {
        
        HttpStatus status;
        
        DCD result = dcdService.findByDsmIdAndDcmId(dsmId, dcmId);
        if (result==null)
        	status =HttpStatus.NOT_FOUND;
        else
        	status =HttpStatus.OK;

        return new ResponseEntity<DCD>(result, status);
    }
	
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DCD> addDcd(@RequestBody DCD dcd, UriComponentsBuilder builder)
    {
		boolean flag = dcdService.addDcd(dcd);
		
		if (flag == false)
		{
			return new ResponseEntity<DCD>(dcd, HttpStatus.FORBIDDEN);
		}
		else
		{
			return new ResponseEntity<DCD>(dcd, HttpStatus.CREATED);
		}
		
	}
    
    @RequestMapping(value = "/uri", method = RequestMethod.DELETE)    
    public ResponseEntity<String> deleteByUri(@RequestParam (name="id") String dcdId)
    {
    	boolean flag = dcdService.deleteByUri(dcdId);
    	HttpStatus status;
    	
    	if (flag==false)
    		status = HttpStatus.NO_CONTENT;
    	else 
    		status = HttpStatus.OK;
    	return new ResponseEntity<String>("deleted",status);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)    
    public ResponseEntity<String> delete(@RequestBody DCD dcd)
    {
    	boolean flag = dcdService.delete(dcd);
    	HttpStatus status;
    	
    	if (flag==false)
    		status = HttpStatus.NO_CONTENT;
    	else 
    		status = HttpStatus.OK;
    	return new ResponseEntity<String>("deleted",status);
    }

}
