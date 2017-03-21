package br.com.alois.solution.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.user.Caregiver;

@Repository
public interface ICaregiverRepository extends JpaRepository<Caregiver, Long>
{

	@Query(value = "FROM Caregiver c WHERE c.username = :username")
	List<Caregiver> findByUsername(@Param("username") String username);

}
