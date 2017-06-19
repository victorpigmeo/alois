package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.user.Request;
import br.com.alois.domain.entity.user.RequestType;
import br.com.alois.domain.entity.user.RequestStatus;
import br.com.alois.solution.domain.repository.IMemoryRepository;
import br.com.alois.solution.domain.repository.IRequestRepository;

@Service
public class RequestService {

	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IRequestRepository requestRepository;
	
	@Autowired
	IMemoryRepository memoryRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Request> listRequestByPatientId(Long patientId) {
		return this.requestRepository.listByPatientId( patientId, RequestType.LOGOFF );
	}
	
	public List<Request> listMemoryRequestsByPatientId(Long patientId) {
		return this.requestRepository.listByPatientId( patientId, RequestType.MEMORY_DELETE );
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
	
	public Request memoryDeleteRequest(Request request){
		Assert.notNull(request);
		
		Assert.isNull(this.requestRepository.findPendingRequestsByMemoryId(request.getMemory().getId()), "This memory already has a pending memory request");

		return this.requestRepository.save(request);
	}
	
	public void approveMemoryRequest(Request request) {
		Assert.notNull(request);
		this.requestRepository.delete(request);
		this.memoryRepository.delete(request.getMemory());
	}
	
	public Request discardMemoryRequest(Request request) {
		Assert.notNull(request);

		request.setRequestStatus(RequestStatus.DISCARTED);
		return this.requestRepository.save(request);
	}
	//======================================================================================

}
