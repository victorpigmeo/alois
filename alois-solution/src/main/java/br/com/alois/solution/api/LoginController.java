package br.com.alois.solution.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.User;
import br.com.alois.solution.domain.service.CaregiverService;
import br.com.alois.solution.domain.service.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginController
{
	//=====================================INJECTIONS=======================================
	@Autowired
	UserService userService;
	
	@Autowired
	CaregiverService caregiverService;
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	@RequestMapping(method=RequestMethod.POST, value="/authenticate")
	public User authenticate(
		@RequestBody ObjectNode user
	){
		return this.userService.authenticateFromApi(user);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/signup")
	public Caregiver signup(@RequestBody Caregiver caregiver){
		return this.caregiverService.signup(caregiver);
	}
	//======================================================================================
}
