package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alois.domain.entity.reminder.Reminder;

public interface IReminderRepository extends JpaRepository<Reminder, Long>{

	@Query(value = "FROM Reminder reminder WHERE reminder.patient.id = :patientId")
	List<Reminder> listReminderByPatientId(@Param("patientId") Long patientId);

}
