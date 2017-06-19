package br.com.alois.solution.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.solution.domain.service.CaregiverService;

@RestController
@RequestMapping("/api/caregiver")
public class CaregiverController {

	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================

	@Autowired
	CaregiverService caregiverService;
	//======================================================================================

	//====================================CONSTRUCTORS======================================

	//======================================================================================

	//==================================GETTERS/SETTERS=====================================

	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@RequestMapping(method = RequestMethod.GET, value = "/listAll")
	public List<Caregiver> getCaregiverList()
	{
		return this.caregiverService.getCaregiverList();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateNotificationToken/{caregiverId}/{notificationToken}")
	public void updateNotificationToken(@PathVariable("notificationToken") String notificationToken, @PathVariable("caregiverId") Long caregiverId)
	{
		this.caregiverService.updateNotificationToken(notificationToken, caregiverId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/insert")
	public Caregiver addCaregiver(@RequestBody Caregiver caregiver)
	{
		return this.caregiverService.addCaregiver(caregiver);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{caregiverId}")
	public void deleteCaregiver(@PathVariable("caregiverId") Long caregiverId)
	{
		this.caregiverService.deleteCaregiver(caregiverId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public Caregiver updateCaregiver(@RequestBody Caregiver caregiver)
	{
		return this.caregiverService.updateCaregiver(caregiver);
	}
	//======================================================================================

}
