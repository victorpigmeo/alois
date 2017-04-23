package br.com.alois.solution.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alois.domain.entity.reminder.Reminder;

public interface IReminderRepository extends JpaRepository<Reminder, Long>{

}
