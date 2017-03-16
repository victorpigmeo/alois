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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((patients == null) ? 0 : patients.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caregiver other = (Caregiver) obj;
		if (patients == null) {
			if (other.patients != null)
				return false;
		} else if (!patients.equals(other.patients))
			return false;
		return true;
	}
	
	//======================================================================================

}
