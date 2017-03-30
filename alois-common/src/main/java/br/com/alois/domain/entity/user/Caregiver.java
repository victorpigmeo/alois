package br.com.alois.domain.entity.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Audited
@Table(name = "caregiver")
@JsonTypeName("caregiver")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  scope = Caregiver.class,
		  property = "id")
public class Caregiver extends User
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -52559064557703378L;
	
	@OneToMany(orphanRemoval = true, fetch=FetchType.LAZY, mappedBy="caregiver")
	private List<Patient> patients;

	//======================================================================================
		
	//====================================CONSTRUCTORS======================================
	public Caregiver(){};
	
	public Caregiver(Long id)
	{
		this.setId(id);
	}
	
	public Caregiver(String name, String email, Gender gender, String username, String password){
		this.setName(name);
		this.setEmail(email);
		this.setGender(gender);
		this.setUsername(username);
		this.setPassword(password);
	}
	//======================================================================================

	//==================================GETTERS/SETTERS=====================================
	public List<Patient> getPatients() {
		return patients;
	}
	
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	
	//Não precisamos de equals e hashCode pois não devemos fazer isso com listas e esse objeto tem apenas uma lista,
	//Portanto utilizamos o equals e hashcode do User
	
	//======================================================================================

}
