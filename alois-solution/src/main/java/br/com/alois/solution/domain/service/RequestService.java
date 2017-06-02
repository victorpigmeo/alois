package br.com.alois.solution.domain.service;

import java.util.List;

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
	public List<Request> listRequestByPatientId(Long patientId) {
		return this.requestRepository.listByPatientId( patientId );
	}

	public Request approveLogoffRequest(Request request) {
		Assert.notNull(request);

		request.setRequestStatus(RequestStatus.APPROVED);
		return this.requestRepository.save(request);
	}

	public Request discardLogoffRequest(Request request) {
		Assert.notNull(request);

		request.setRequestStatus(RequestStatus.DISCARTED);
		return this.requestRepository.save(request);
	}

	//======================================================================================

}
