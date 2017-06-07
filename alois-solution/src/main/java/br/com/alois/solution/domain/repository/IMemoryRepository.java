package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.memory.Memory;

@Repository
public interface IMemoryRepository extends JpaRepository<Memory, Long>
{
	@EntityGraph(attributePaths = {
			"patient"
	})
	@Query(value = "FROM Memory m WHERE m.patient.id = :patientId")
	List<Memory> listMemoryByPatientId(@Param("patientId") Long patientId);
	
	@EntityGraph(attributePaths = {
			"patient"
	})
	@Query(value = "FROM Memory m WHERE m.id = :memoryId")
	Memory findById(@Param("memoryId") Long memoryId);
	
	@Query(value = "FROM Memory m WHERE m.title = :title")
	Memory findByTitle(@Param("title") String title);
	
}
