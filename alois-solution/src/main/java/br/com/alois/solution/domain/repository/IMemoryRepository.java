package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.memory.Memory;

@Repository
public interface IMemoryRepository extends JpaRepository<Memory, Long>
{
	@Query(value = "FROM Memory m WHERE m.patient.id = :patientId")
	List<Memory> listMemoryByPatientId(@Param("patientId") Long patientId);
	
}
