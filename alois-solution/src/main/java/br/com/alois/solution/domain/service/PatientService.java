package br.com.alois.solution.domain.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.route.AloisLatLng;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.UserType;
import br.com.alois.solution.domain.repository.IPatientRepository;
import br.com.alois.solution.domain.repository.IUserRepository;

@Service
public class PatientService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IPatientRepository patientRepository;
	
	@Autowired
	IUserRepository userRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Patient> listPatientsByCaregiverId(Long caregiverId) 
	{
		return this.patientRepository.listPatientsByCaregiverId(caregiverId);
	}
	
	public Patient findById(Long patientId) 
	{
		Patient patient = new Patient();
		patient = this.patientRepository.findById(patientId);
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println(patient.getName());
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		return patient;
	}

	public Patient insert(Patient patient) 
	{
		Assert.notNull(patient);
		patient.setUserType(UserType.PATIENT);
		if(this.userRepository.findByUsername(patient.getUsername()) == null)
		{
			try
			{
				return this.patientRepository.save(patient);
			}
			catch(ConstraintViolationException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	public AloisLatLng updateLastLocation(AloisLatLng lastLocation, Long patientId) {
		return this.patientRepository.updateLastLocation(lastLocation, patientId);
	}

	public Patient updatePatient(Patient patient) {
		return this.patientRepository.save(patient);
	}

	//======================================================================================

}
