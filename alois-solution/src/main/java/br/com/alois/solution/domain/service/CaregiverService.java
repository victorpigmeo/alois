package br.com.alois.solution.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.UserType;
import br.com.alois.solution.domain.repository.ICaregiverRepository;

@Service
public class CaregiverService
{
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	ICaregiverRepository caregiverRepository;

	//======================================================================================

	//=====================================BEHAVIOUR========================================

	//======================================================================================
	public Caregiver signup(Caregiver caregiver)
	{
		Assert.notNull(caregiver);
		
		caregiver.setUserType(UserType.CAREGIVER);
		
		if(this.caregiverRepository.findByUsername(caregiver.getUsername()).size() != 0)
		{
			return null;
		}
		else
		{
			return this.caregiverRepository.save(caregiver);
		}
	}
}
