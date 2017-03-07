package br.com.alois.solution.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.alois.domain.entity.user.Administrator;
import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.User;
import br.com.alois.solution.domain.repository.IUserRepository;

@Service
public class UserService
{
	//=====================================ATTRIBUTES=======================================
	
	//======================================================================================
	
	//=====================================INJECTIONS=======================================
	@Autowired
	IUserRepository userRepository;
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	public String authenticate(String username, String password)
	{
		User user = this.userRepository.findUserByUsernameAndPassword(username, password);
		
		if(user != null){
			try{
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.writeValueAsString(user);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return "Não achou";
	}

	public User authenticateFromApi(ObjectNode params)
	{
		Assert.notNull(params);
		Assert.notNull(params.get("username"));
		Assert.notNull(params.get("password"));
		
		final String username = params.get("username").asText();
		final String password = params.get("password").asText();

		User user = this.userRepository
				.findUserByUsernameAndPassword(username, password);
		
		if(user == null)
		{
			return null;
		}
		else
		{
			switch(user.getUserType())
			{
				case ADMINISTRATOR:
					return (Administrator) user;
				case CAREGIVER:
					return (Caregiver) user;
				case PATIENT:
					return (Patient) user;
				default:
					return null;
			}
		}
	}
	
	//======================================================================================
}
