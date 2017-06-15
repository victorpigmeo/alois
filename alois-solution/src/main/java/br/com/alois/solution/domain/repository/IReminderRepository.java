package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;

public interface IReminderRepository extends JpaRepository<Reminder, Long>{

	@EntityGraph(attributePaths = {
			"patient"
	})
	@Query(value = "FROM Reminder reminder WHERE reminder.patient.id = :patientId")
	List<Reminder> listReminderByPatientId(@Param("patientId") Long patientId);

	@Override
	@EntityGraph(attributePaths = {
			"patient"
	})
	@Query(value = "FROM Reminder reminder WHERE reminder.id = :reminderId")
	Reminder findOne(@Param("reminderId") Long reminderId);

	@Modifying
	@Query(value="UPDATE Reminder reminder SET reminder.reminderStatus = :status WHERE reminder.id = :reminderId ")
	void updateStatusReminder(@Param("reminderId") Long reminderId, @Param("status") ReminderStatus reminderStatus);

	@EntityGraph(attributePaths = {
			"patient"
	})
	@Query(value = "FROM Reminder reminder WHERE reminder.patient.id = :patientId AND reminder.reminderStatus = 1")
	List<Reminder> listActiveReminderByPatientId(@Param("patientId") Long patientId);
}
