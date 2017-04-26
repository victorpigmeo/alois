package br.com.alois.solution.domain.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.solution.domain.repository.IMemoryRepository;

@Service
public class MemoryService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IMemoryRepository memoryRepository;

	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Memory> listMemoryByPatient(Long patientId) 
	{
		return this.memoryRepository.listMemoryByPatientId(patientId);
	}
	
	/*public Patient findById(Long patientId) 
	{
		Patient patient = new Patient();
		patient = this.patientRepository.findById(patientId);
		
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

	public void updateLastLocation(Point lastLocation, Long patientId) 
	{
		Assert.notNull(lastLocation);
		Assert.notNull(patientId);
		
		Patient patient = this.patientRepository.findOne(patientId);
		
		Point oldLastLocation = patient.getLastLocation();
		
		patient.setLastLocation(lastLocation);
		this.patientRepository.save(patient);
		
		if(oldLastLocation != null)
		{
			this.pointRepository.delete(oldLastLocation);
		}
	}

	public Patient updatePatient(Patient patient) 
	{
		return this.patientRepository.save(patient);
	}

	public void deletePatient(Patient patient)
	{
		this.patientRepository.delete(patient);
	}*/
	//======================================================================================

}
