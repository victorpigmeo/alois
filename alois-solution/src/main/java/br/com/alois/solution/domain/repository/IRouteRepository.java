package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.route.Route;

@Repository
public interface IRouteRepository extends JpaRepository<Route, Long>
{
	@EntityGraph(attributePaths = {
			"steps"
	})
	@Query("SELECT DISTINCT r FROM Route r WHERE r.patient.id = :patientId")
	List<Route> findByPatientId(@Param("patientId") Long patientId);

}
