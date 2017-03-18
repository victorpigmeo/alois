package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alois.domain.entity.user.Patient;
import br.com.alois.solution.domain.repository.IPatientRepository;

@Service
public class PatientService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IPatientRepository patientRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Patient> listPatientsByCaregiverId(Long caregiverId) {
		return this.patientRepository.listPatientsByCaregiverId(caregiverId);
	}

	//======================================================================================

}
