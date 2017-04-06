package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.route.Point;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.solution.domain.service.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
	//=====================================INJECTIONS=======================================
	@Autowired
	PatientService patientService;

	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method=RequestMethod.GET, value="/listByCaregiverId/{caregiverId}")
	public List<Patient> listPatientsByCaregiverId(@PathVariable("caregiverId") Long caregiverId)
	{
		return this.patientService.listPatientsByCaregiverId(caregiverId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findById/{patientId}")
	public Patient findById(@PathVariable("patientId") Long patientId)
	{
		return this.patientService.findById(patientId);
	}

	@RequestMapping(method=RequestMethod.POST, value="/insert")
	public Patient insertPatient(@RequestBody Patient patient)
	{
		return this.patientService.insert(patient);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateLastLocation/{patientId}")
	public void updateLastLocation(@RequestBody Point lastLocation, @PathVariable("patientId") Long patientId)
	{
		this.patientService.updateLastLocation(lastLocation, patientId);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/update")
	public Patient updatePatient(@RequestBody Patient patient)
	{
		return this.patientService.updatePatient(patient);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/delete")
	public void deletePatient(@RequestBody Patient patient)
	{
		this.patientService.deletePatient(patient);
	}
	//======================================================================================

}
