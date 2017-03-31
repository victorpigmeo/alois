package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alois.domain.entity.route.AloisLatLng;
import br.com.alois.domain.entity.user.Patient;

public interface IPatientRepository extends JpaRepository<Patient, Long>{

	@Query(value = "FROM Patient p WHERE p.caregiver.id = :caregiverId")
	List<Patient> listPatientsByCaregiverId(@Param("caregiverId") Long caregiverId);

	@Query(value = "FROM Patient p WHERE p.id = :patientId")
	Patient findById(@Param("patientId") Long patientId);
	
	@Query(value = "UPDATE Patient p SET p.lastLocation = :lastLocation WHERE p.id = :patientId")
	AloisLatLng updateLastLocation(@Param("lastLocation") AloisLatLng lastLocation, @Param("patientId") Long patientId);
}
