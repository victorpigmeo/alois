package br.com.alois.solution.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alois.domain.entity.notification.ReminderNotification;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.solution.configuration.NotificationServerConfiguration;
import br.com.alois.solution.domain.client.NotificationClient;
import br.com.alois.solution.domain.repository.IReminderRepository;
import feign.Feign;

@Service
public class ReminderService {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IReminderRepository reminderRepository;
	
	//======================================================================================

	public Reminder insertReminder(Reminder reminder) {
		return this.reminderRepository.save(reminder);
	}

	public Reminder sendRequest(Reminder reminder) {
		System.out.println(reminder.getPatient().getNotificationToken());
		ObjectMapper objectMapper = new ObjectMapper();
		
		try{
			String jsonReminder = objectMapper.writeValueAsString(reminder);
			String notification = new ReminderNotification().setData(jsonReminder).toJson( reminder.getPatient().getNotificationToken() );
			System.out.println(notification);
			NotificationClient notificationClient = Feign.builder()
					.target(NotificationClient.class, NotificationServerConfiguration.API_ENDPOINT);
			
			String result = notificationClient.sendNotification(notification, NotificationServerConfiguration.FIREBASE_TOKEN);
			System.out.println(result);
		}catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	//=====================================BEHAVIOUR========================================

	//======================================================================================

}
