package br.com.alois.domain.entity.user;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.alois.domain.entity.memory.Memory;

@Entity
@Audited
@Table(name = "request")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  scope = Request.class,
		  property = "id")
public class Request implements Serializable{

	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -6513401190443564866L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	@Enumerated(EnumType.ORDINAL)
	private RequestType requestType;
	
	@Enumerated(EnumType.ORDINAL)
	private RequestStatus requestStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Memory memory;
	
	private Calendar timeRequested;
	
	//======================================================================================

	//====================================CONSTRUCTORS======================================
	
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	
	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public Calendar getTimeRequested() {
		return timeRequested;
	}

	public void setTimeRequested(Calendar timeRequested) {
		this.timeRequested = timeRequested;
	}

	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((requestStatus == null) ? 0 : requestStatus.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((timeRequested == null) ? 0 : timeRequested.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memory == null) {
			if (other.memory != null)
				return false;
		} else if (!memory.equals(other.memory))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (requestStatus != other.requestStatus)
			return false;
		if (requestType != other.requestType)
			return false;
		if (timeRequested == null) {
			if (other.timeRequested != null)
				return false;
		} else if (!timeRequested.equals(other.timeRequested))
			return false;
		return true;
	}
	
	//======================================================================================


}
