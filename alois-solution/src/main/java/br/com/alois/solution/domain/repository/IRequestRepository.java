package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alois.domain.entity.user.Request;

public interface IRequestRepository extends JpaRepository<Request, Long>{
	
	@Query(value = "FROM Request request WHERE request.patient.id = :patientId ORDER BY request.timeRequested DESC")
	List<Request> listByPatientId(@Param("patientId") Long patientId);
	
}
