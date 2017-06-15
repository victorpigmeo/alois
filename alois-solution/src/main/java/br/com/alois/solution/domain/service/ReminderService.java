package br.com.alois.solution.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.*;

import br.com.alois.domain.entity.notification.ReminderNotification;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
import br.com.alois.solution.configuration.NotificationServerConfiguration;
import br.com.alois.solution.domain.client.NotificationClient;
import br.com.alois.solution.domain.repository.IPatientRepository;
import br.com.alois.solution.domain.repository.IReminderRepository;
import feign.Feign;

@Service
public class ReminderService 
{
	//=====================================ATTRIBUTES=======================================
	private final String ADD_REMINDER_TAG = "ADD_REMINDER_REQUEST";
	
	private final String UPDATE_REMINDER_TAG = "UPDATE_REMINDER_REQUEST";
	
	private final String DELETE_REMINDER_TAG = "DELETE_REMINDER_REQUEST";
	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IReminderRepository reminderRepository;
	
	@Autowired
	IPatientRepository patientRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public Reminder insertReminder(Reminder reminder) 
	{
		return this.reminderRepository.save(reminder);
	}

	public Reminder sendRequest(Reminder reminder) 
	{
		Assert.notNull( reminder, "Reminder null" );
		
		reminder.setReminderStatus(ReminderStatus.PENDING);
		
		reminder = this.reminderRepository.save( reminder );
		
		this.sendNotification(reminder, this.ADD_REMINDER_TAG);
		
		return reminder;
	}
	
	public Reminder updateReminder(Reminder reminder) 
	{
		Assert.notNull( reminder, "Reminder null" );
		Assert.notNull( reminder.getId(), "Reminder id null" );
		
		reminder.setReminderStatus(ReminderStatus.PENDING_DELETE);
		
		reminder = this.reminderRepository.save( reminder );
		
		this.sendNotification(reminder, this.UPDATE_REMINDER_TAG);

		return this.reminderRepository.save(reminder);
	}
	
	public void sendNotification(Reminder reminder, String tag)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Hibernate4Module());
		try
		{
			String jsonReminder = objectMapper.writeValueAsString(reminder);
			String notification = new ReminderNotification().setData(jsonReminder).toJson( reminder.getPatient().getNotificationToken(), tag );
			
			NotificationClient notificationClient = Feign.builder()
					.target(NotificationClient.class, NotificationServerConfiguration.API_ENDPOINT);
			
			notificationClient.sendNotification(notification, NotificationServerConfiguration.FIREBASE_TOKEN);
		}
		catch( JsonProcessingException e )
		{
			e.printStackTrace();
		}
	}
	
	public List<Reminder> listReminderByPatientId(Long patientId) 
	{
		return this.reminderRepository.listReminderByPatientId(patientId);
	}
	
	public void deleteRequest(Long reminderId) 
	{
		Assert.notNull(reminderId, "Reminder id null");
		
		Reminder reminder = this.reminderRepository.findOne( reminderId );
		
		reminder.setReminderStatus( ReminderStatus.PENDING_DELETE );
		
		this.reminderRepository.save( reminder );
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			String patientNotificationToken = reminder.getPatient().getNotificationToken();
			
			//Setando patient null pois não é necessário e se deixar excede o tamanho maximo do firebase
			reminder.setPatient(null);
			
			String jsonReminder = objectMapper.writeValueAsString(reminder);
			String notification = new ReminderNotification().setData(jsonReminder).toJson( patientNotificationToken, this.DELETE_REMINDER_TAG );
			
			NotificationClient notificationClient = Feign.builder()
					.target(NotificationClient.class, NotificationServerConfiguration.API_ENDPOINT);
			
			notificationClient.sendNotification(notification, NotificationServerConfiguration.FIREBASE_TOKEN);
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteReminder(Reminder reminder) 
	{
		this.reminderRepository.delete( reminder );
	}

	@Transactional
	public void updateAtivoReminder(Long reminderId, ReminderStatus reminderStatus) 
	{
		this.reminderRepository.updateStatusReminder(reminderId, reminderStatus);
	}

	public List<Reminder> listActiveReminderByPatientId(Long patientId) {
		return this.reminderRepository.listActiveReminderByPatientId(patientId);
	}
	
	//======================================================================================





}
