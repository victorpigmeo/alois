package br.com.alois.domain.entity.user;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.reminder.Reminder;
import br.com.alois.domain.entity.route.Route;

@Entity
@Audited
@Table(name = "patient")
@JsonTypeName("patient")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  scope = Patient.class,
		  property = "id")
public class Patient extends User
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -742729100418082281L;
	
	@NotBlank(message = "Name is required")
	@Column(nullable=false)
	private String phone;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar birthDate;
	
	@NotBlank(message = "Address is required")
	@Column(nullable=false)
	private String address;
	
	private String emergencyPhone;
	
	private String note;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Caregiver caregiver;
	
	@JsonManagedReference
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY, mappedBy="patient")
	private List<Reminder> reminders;
	
	@JsonManagedReference
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY, mappedBy="patient")
	private List<Memory> memories;
	
	@JsonManagedReference
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY, mappedBy="patient")
	private List<Route> routes;

	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	public Patient(){}
	
	public Patient(
			String name,
			String phone,
			Gender gender,
			Calendar dateOfBirth,
			String address,
			String emergencyPhone,
			String note,
			String username,
			String password,
			Caregiver caregiver
	)
	{
		this.setName(name);
		this.phone = phone;
		this.setGender(gender);
		this.birthDate = dateOfBirth;
		this.address = address;
		this.emergencyPhone = emergencyPhone;
		this.note = note;
		this.setUsername(username);
		this.setPassword(password);
		this.caregiver = caregiver;
	}
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Calendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Caregiver getCaregiver() {
		return caregiver;
	}

	public void setCaregiver(Caregiver caregiver) {
		this.caregiver = caregiver;
	}
	
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	public List<Memory> getMemories() {
		return memories;
	}

	public void setMemories(List<Memory> memories) {
		this.memories = memories;
	}
	
	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((caregiver == null) ? 0 : caregiver.hashCode());
		result = prime * result + ((emergencyPhone == null) ? 0 : emergencyPhone.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Patient other = (Patient) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (caregiver == null) {
			if (other.caregiver != null)
				return false;
		} else if (!caregiver.equals(other.caregiver))
			return false;
		if (emergencyPhone == null) {
			if (other.emergencyPhone != null)
				return false;
		} else if (!emergencyPhone.equals(other.emergencyPhone))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
	//======================================================================================

	
}
