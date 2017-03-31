package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.route.Route;
import br.com.alois.solution.domain.service.RouteService;

@RestController
@RequestMapping("/api/route")
public class RouteController {
	//=====================================INJECTIONS=======================================
	@Autowired
	RouteService routeService;
	
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method = RequestMethod.GET, value="/listRoutesByPatientId/{patientId}")
	public List<Route> listRoutesByPatientId(@PathVariable("patientId") Long patientId)
	{
		return this.routeService.listRoutesByPatientId(patientId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/addRoute")
	public Route insertRoute(@RequestBody Route route)
	{
		return this.routeService.insertRoute(route);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/updateRoute")
	public Route updateRoute(@RequestBody Route route)
	{
		return this.routeService.updateRoute(route);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/deleteRoute")
	public void deleteRoute(@RequestBody Route route)
	{
		this.routeService.deleteRoute(route);
	}
	//======================================================================================

}
