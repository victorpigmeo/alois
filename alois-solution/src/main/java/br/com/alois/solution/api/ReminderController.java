package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.reminder.ReminderStatus;
import br.com.alois.solution.domain.service.ReminderService;

@RestController
@RequestMapping("/api/reminder")
public class ReminderController {
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	ReminderService reminderService;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method = RequestMethod.POST, value = "/insert")
	public Reminder insertReminder(@RequestBody Reminder reminder)
	{
		return this.reminderService.insertReminder(reminder);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listActiveRemindersByPatientId/{patientId}")
	public List<Reminder> listActiveRemindersByPatientId(@PathVariable("patientId") Long patientId)
	{
		return this.reminderService.listActiveReminderByPatientId(patientId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public Reminder updateReminder(@RequestBody Reminder reminder)
	{
		return this.reminderService.updateReminder(reminder);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateAtivoReminder/{reminderId}/{reminderStatus}")
	public void updateAtivoReminder(@PathVariable("reminderId") Long reminderId, @PathVariable("reminderStatus") ReminderStatus reminderStatus)
	{
		this.reminderService.updateAtivoReminder(reminderId, reminderStatus);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendRequest")
	public Reminder sendRequest(@RequestBody Reminder reminder)
	{
		return this.reminderService.sendRequest(reminder);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteRequest/{reminderId}")
	public void deleteRequest(@PathVariable Long reminderId)
	{
		this.reminderService.deleteRequest(reminderId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public void deleteReminder(@RequestBody Reminder reminder)
	{
		Assert.isTrue( reminder.getReminderStatus().equals( ReminderStatus.PENDING_DELETE ), "Esse reminder não está marcado para remoção" );
		
		this.reminderService.deleteReminder(reminder);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listReminderByPatientId/{patientId}")
	public List<Reminder> listReminderByPatientId(@PathVariable("patientId") Long patientId)
	{
		return this.reminderService.listReminderByPatientId(patientId);
	}
	//======================================================================================

}
