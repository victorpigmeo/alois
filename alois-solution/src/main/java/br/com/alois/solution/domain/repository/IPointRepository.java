package br.com.alois.solution.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alois.domain.entity.route.Point;

public interface IPointRepository extends JpaRepository<Point, Long>
{
	
}
