package br.com.alois.solution.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.route.Step;

@Repository
public interface IStepRepository extends JpaRepository<Step, Long>
{
	
}
