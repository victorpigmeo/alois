package br.com.alois.solution.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.user.Request;
import br.com.alois.domain.entity.user.RequestStatus;
import br.com.alois.solution.domain.repository.IRequestRepository;

@Service
public class RequestService {

	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IRequestRepository requestRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Request> listRequestByPatientId(Long patientId) 
	{
		return this.requestRepository.listByPatientId( patientId );
	}

	public Request approveLogoffRequest(Request request) 
	{
		Assert.notNull(request);

		request.setRequestStatus(RequestStatus.APPROVED);
		return this.requestRepository.save(request);
	}

	public Request discardLogoffRequest(Request request) 
	{
		Assert.notNull(request);

		request.setRequestStatus(RequestStatus.DISCARTED);
		return this.requestRepository.save(request);
	}

	public Request requestLogoff(Request request) 
	{
		Assert.notNull(request);
		
		Assert.isNull(this.requestRepository.findPendingRequestByPatientId(request.getPatient().getId()), "This patient awready has a pending logoff request");
		
		return this.requestRepository.save(request);
	}

	public Request getPatientLogoffApprovedRequest(Long patientId) 
	{
		return this.requestRepository.findPatientLogoffApprovedRequest(patientId);
	}

	@Transactional
	public void updateUsedPatientLogoffRequest(Long patientId) 
	{
		this.requestRepository.updateUsedPatientLogoffRequest(patientId);
	}

	//======================================================================================

}
