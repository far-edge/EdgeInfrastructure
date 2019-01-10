package eu.sensap.dataRouting.controllers;


import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.context.IntegrationFlowRegistration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResult;
import eu.faredge.edgeInfrastructure.registry.messages.RegistrationResultStatusEnum;
import eu.faredge.edgeInfrastructure.registry.models.dcd.DCD;
import eu.faredge.edgeInfrastructure.registry.models.dsm.DSM;
import eu.faredge.edgeInfrastructure.registry.models.full.FullDSM;
import eu.sensap.dataRouting.business.BusinessImp;
import eu.sensap.dataRouting.flows.FlowConfig;
import eu.sensap.dataRouting.model.ConsumerDataFlow;
import eu.sensap.dataRouting.model.DataSourceDataFlow;
import eu.sensap.dataRouting.model.RequestPojo;
import eu.sensap.dataRouting.model.RequestResults;
import eu.sensap.dataRouting.model.RequestWrapper;
import io.swagger.annotations.Api;


@RestController
@RequestMapping("/dataRouting")
@Api(value = "user", description = "Rest API for user operations", tags = "User API")
public class DataRoutingController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private BusinessImp bimpl = new BusinessImp(); 
	
	
	@Autowired
	private ConfigurableApplicationContext context;
	private ArrayList<IntegrationFlowRegistration> registerdFlows = new ArrayList<IntegrationFlowRegistration>();
	
	/*
	@RequestMapping(value = "newDataSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult newDataSource (@RequestBody RequestWrapper req)//, @RequestBody FullDSM destDsm)
	{
		RegistrationResult res = new RegistrationResult();
		
		
//		check input validity
		if (req == null)//||(destDsm==null))
		{
			res.setStatus(RegistrationResultStatusEnum.DENIED);
			res.setStatusMessage("Not valid input");
			res.setBody("Body: Not valid input");
			return res;
		}
		
		
		System.out.println("sourcedsm=" + req.getSourceDsm().getId());
		
		
		res.setStatus(RegistrationResultStatusEnum.SUCCESS);
		res.setStatusMessage("sdfsdfsd");
		res.setBody("sdfdsfsdfsdfsdfsd");
		
		
			
				
		return res;
	}
	
	
	@RequestMapping(value = "createFlow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RequestResults createFlow (@RequestBody RequestPojo request)
	{		
		RequestResults res = new RequestResults();
		res.setStatus("eeee");
		res.setBody(request.getSourceTopic() + "| " + request.getDestTopic() );
		res.setStatusMessage("rewrwerwe");
		
//		String sourceUri[]= {request.getSourceUrl() + ":" + request.getSourcePort()};
//		String destUri[]= {request.getDestUrl() + ":" + request.getDestPort()};
//		String recieveTopic = request.getSourceTopic();
//		String sendTopic =request.getDestTopic();

		
		registerdFlows.add(context.getBean(FlowConfig.class).addAnAdapter(request));
	   
		return res;		
	}
	
	@RequestMapping(value = "deleteFlow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RequestResults deleteFlow (@RequestBody RequestPojo request)
	{		
		RequestResults res = new RequestResults();
		res.setStatus("eeee");
		res.setBody(request.getSourceTopic() + "| " + request.getDestTopic() );
		res.setStatusMessage("rewrwerwe");
		
		//String uri[]= {request.getSourceUrl()};
		String recieveTopic[] = {request.getSourceTopic()};
		String sendTopic[] = {request.getDestTopic()};
		IntegrationFlowRegistration flow = null;
		for (int i=0;i<registerdFlows.size();i++) {
			if ((registerdFlows.get(i).getId()).equals(sendTopic+":"+recieveTopic)) {
				flow = registerdFlows.get(i);
				break;
			}				
		}
		if (flow==null) {}
		
		context.getBean(FlowConfig.class).removeAdapter(flow);
	   
		return res;
		
	}
	*/
		
	@RequestMapping(value = "newDataSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult DataSourceFlow (@RequestBody DataSourceDataFlow req)//, @RequestBody FullDSM destDsm)
	{
		RegistrationResult res = new RegistrationResult();
		
		
//		check input validity
		if (req == null)//||(destDsm==null))
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Not valid input");
			res.setBody("Body: Not valid input");
			return res;
		}		
		
		System.out.println("sourcedsm=" + req.getSourceDsm().getId());		
		
		res.setStatus(RegistrationResultStatusEnum.SUCCESS);
		res.setStatusMessage("sdfsdfsd");
		res.setBody("sdfdsfsdfsdfsdfsd");
				
		return res;
	}	
	
	
	@RequestMapping(value = "removeDataSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RequestResults deleteDataSource (@RequestBody DSM request)
	{		
		RequestResults res = new RequestResults();
		res.setStatus("eeee");
		res.setBody(request.getId());
		res.setStatusMessage("rewrwerwe");
		
		//String uri[]= {request.getSourceUrl()};
//		String recieveTopic[] = {request.getSourceTopic()};
//		String sendTopic[] = {request.getDestTopic()};
		IntegrationFlowRegistration flow = null;
//		for (int i=0;i<registerdFlows.size();i++) {
//			if ((registerdFlows.get(i).getId()).equals(sendTopic+":"+recieveTopic)) {
//				flow = registerdFlows.get(i);
//				break;
//			}				
//		}
		if (flow==null) {}
		
		context.getBean(FlowConfig.class).removeAdapter(flow);
	   
		return res;
		
	}
	
	@RequestMapping(value = "newConsumer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RegistrationResult ConsumerFlow (@RequestBody ConsumerDataFlow req)//, @RequestBody FullDSM destDsm)
	{
		RegistrationResult res = new RegistrationResult();
		
		
//		check input validity
		if (req == null)//||(destDsm==null))
		{
			res.setStatus(RegistrationResultStatusEnum.SYSTEMFAILURE);
			res.setStatusMessage("Not valid input");
			res.setBody("Body: Not valid input");
			return res;
		}		
		
		System.out.println("consumerdsm=" + req.getDestDcm().getId());		
		
		res.setStatus(RegistrationResultStatusEnum.SUCCESS);
		res.setStatusMessage("sdfsdfsd");
		res.setBody("sdfdsfsdfsdfsdfsd");
				
		return res;
	}
	
	@RequestMapping(value = "removeDataChannelDescriptor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RequestResults deleteConsumerFlow (@RequestBody DCD request)
	{		
		RequestResults res = new RequestResults();
		res.setStatus("eeee");
		res.setBody(request.getId());
		res.setStatusMessage("rewrwerwe");
		
		//String uri[]= {request.getSourceUrl()};
//		String recieveTopic[] = {request.getSourceTopic()};
//		String sendTopic[] = {request.getDestTopic()};
		IntegrationFlowRegistration flow = null;
//		for (int i=0;i<registerdFlows.size();i++) {
//			if ((registerdFlows.get(i).getId()).equals(sendTopic+":"+recieveTopic)) {
//				flow = registerdFlows.get(i);
//				break;
//			}				
//		}
		if (flow==null) {}
		
		context.getBean(FlowConfig.class).removeAdapter(flow);
	   
		return res;
		
	}
}