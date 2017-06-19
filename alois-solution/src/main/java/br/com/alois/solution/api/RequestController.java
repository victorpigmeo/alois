package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.user.Request;
import br.com.alois.solution.domain.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	RequestService requestService;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method = RequestMethod.GET, value="/listRequestByPatientId/{patientId}") 
	public List<Request> listRequestByPatientId( @PathVariable("patientId") Long patientId )
	{
		return this.requestService.listRequestByPatientId( patientId );
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listMemoryRequestsByPatientId/{patientId}") 
	public List<Request> listMemoryRequestsByPatientId( @PathVariable("patientId") Long patientId )
	{
		return this.requestService.listMemoryRequestsByPatientId( patientId );
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/approveLogoffRequest")
	public Request approveLogoffRequest(@RequestBody Request request)
	{
		return this.requestService.approveLogoffRequest(request);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/discardLogoffRequest")
	public Request discardLogoffRequest(@RequestBody Request request)
	{
		return this.requestService.discardLogoffRequest(request);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/requestLogoff")
	public Request requestLogoff(@RequestBody Request request)
	{
		return this.requestService.requestLogoff( request );
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/getPatientLogoffApprovedRequest/{patientId}")
	public Request getPatientLogoffApprovedRequest(@PathVariable("patientId") Long patientId)
	{
		return this.requestService.getPatientLogoffApprovedRequest(patientId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/updateUsedPatientLogoffRequest/{patientId}")
	public void updateUsedPatientLogoffRequest(@PathVariable("patientId") Long patientId)
	{
		this.requestService.updateUsedPatientLogoffRequest(patientId);
	}

	@RequestMapping(method = RequestMethod.POST, value="/memoryDeleteRequest")
	public Request memoryDeleteRequest(@RequestBody Request request)
	{
		return this.requestService.memoryDeleteRequest(request);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/approveMemoryRequest")
	public void approveMemoryRequest(@RequestBody Request request)
	{
		this.requestService.approveMemoryRequest(request);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/discardMemoryRequest")
	public Request discardMemoryRequest(@RequestBody Request request)
	{
		return this.requestService.discardMemoryRequest(request);
	}
	//======================================================================================

}
