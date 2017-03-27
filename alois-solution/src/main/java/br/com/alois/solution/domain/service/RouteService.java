package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alois.domain.entity.route.Route;
import br.com.alois.solution.domain.repository.IRouteRepository;

@Service
public class RouteService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IRouteRepository routeRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Route> listRoutesByPatientId(Long patientId) {
		return this.routeRepository.findByPatientId(patientId);
	}
	//======================================================================================

}
