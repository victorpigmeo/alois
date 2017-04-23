package br.com.alois.solution.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.reminder.Reminder;
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendRequest")
	public Reminder sendRequest(@RequestBody Reminder reminder)
	{
		return this.reminderService.sendRequest(reminder);
	}
	//======================================================================================

}
