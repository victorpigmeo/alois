package br.com.alois.domain.entity.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Audited
@Table(name = "caregiver")
@JsonTypeName("caregiver")
public class Caregiver extends User
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -52559064557703378L;
	
	@OneToMany(orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="caregiver")
	private List<Patient> patients;

	//======================================================================================
		
	//====================================CONSTRUCTORS======================================
	public Caregiver(){};
	
	public Caregiver(String name, String email, String username, String password){
		this.setName(name);
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(password);
	}
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	
	//======================================================================================

}
