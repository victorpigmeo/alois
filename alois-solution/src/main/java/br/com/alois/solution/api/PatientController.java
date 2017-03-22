package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(method=RequestMethod.POST, value="/insert")
	public Patient insertPatient(@RequestBody Patient patient)
	{
		return this.patientService.insert(patient);
	}
	//======================================================================================

}
