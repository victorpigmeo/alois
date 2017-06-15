package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alois.domain.entity.user.Request;

public interface IRequestRepository extends JpaRepository<Request, Long>{
	
	@Query(value = "FROM Request request WHERE request.patient.id = :patientId ORDER BY request.timeRequested DESC")
	List<Request> listByPatientId(@Param("patientId") Long patientId);

	@Query(value = "FROM Request request WHERE request.patient.id = :patientId AND request.requestStatus = 0 AND request.requestType = 0")
	Request findPendingRequestByPatientId(@Param("patientId") Long patientId);

	@Query(value = "FROM Request request WHERE request.patient.id = :patientId AND request.requestStatus = 1 AND request.requestType = 0")
	Request findPatientLogoffApprovedRequest(@Param("patientId") Long patientId);
	
	@Modifying
	@Query(value = "UPDATE Request request SET request.requestStatus = 3 WHERE request.patient.id = :patientId AND request.requestStatus = 1 AND request.requestType = 0")
	void updateUsedPatientLogoffRequest(@Param("patientId") Long patientId);
	
}
