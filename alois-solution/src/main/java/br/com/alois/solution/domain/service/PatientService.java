package br.com.alois.solution.domain.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.notification.Notification;
import br.com.alois.domain.entity.route.Point;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.UserType;
import br.com.alois.solution.configuration.NotificationServerConfiguration;
import br.com.alois.solution.domain.client.NotificationClient;
import br.com.alois.solution.domain.repository.IPatientRepository;
import br.com.alois.solution.domain.repository.IPointRepository;
import br.com.alois.solution.domain.repository.IRouteRepository;
import br.com.alois.solution.domain.repository.IUserRepository;
import feign.Feign;

@Service
public class PatientService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IPatientRepository patientRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IPointRepository pointRepository;
	
	@Autowired
	IRouteRepository routeRepository;
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

	public void updateLastLocation(Point newLastLocation, Long patientId) 
	{
		Assert.notNull(newLastLocation);
		Assert.notNull(patientId);
		
		System.out.println( "Location: " + newLastLocation.getLatitude() + "|" + newLastLocation.getLongitude() );
		
		final Patient patient = this.patientRepository.findByIdWithRoutes(patientId);
		
		final Point oldLastLocation = patient.updateLastLocation(newLastLocation);
		
		this.patientRepository.save(patient);
		
		if(oldLastLocation != null)
		{
			this.pointRepository.delete(oldLastLocation);
		}
		
		if( !patient.isPatientOnSafeRoute() )
		{
			System.out.println("Out of route!!!");
			//TODO apagar os sysout
			final NotificationClient notificationClient = Feign.builder()
					.target(NotificationClient.class, NotificationServerConfiguration.API_ENDPOINT);
			
			String notification = Notification.toJson(
					"Alois", 
					"Warning! Patient: "+patient.getName()+" is out of all his secure routes!",
					patient.getName(),
					patient.getCaregiver().getNotificationToken()
			);
			
			System.out.println( notificationClient.sendNotification(notification, NotificationServerConfiguration.FIREBASE_TOKEN) );
		}
		
	}

	public Patient updatePatient(Patient patient) 
	{
		return this.patientRepository.save(patient);
	}

	public void deletePatient(Patient patient)
	{
		this.patientRepository.delete(patient);
	}

	public void updateNotificationToken(String notificationToken, Long patientId) {
		Patient patient = this.patientRepository.findOne(patientId);
		
		patient.setNotificationToken(notificationToken);
		
		this.patientRepository.save(patient);
	}
	
	//======================================================================================

}
