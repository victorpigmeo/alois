package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.user.Caregiver;

@Repository
public interface ICaregiverRepository extends JpaRepository<Caregiver, Long>
{

	@Query("SELECT c.id, c.name, c.email, c.gender, c.username, c.password, c.lastLogin FROM Caregiver c")
	List<Caregiver> listAll();

}
