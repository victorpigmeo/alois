package br.com.alois.solution.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alois.domain.entity.route.Route;

@Repository
public interface IRouteRepository extends JpaRepository<Route, Long>
{

}
