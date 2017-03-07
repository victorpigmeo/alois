package br.com.alois.domain.entity.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Audited
@Table(name = "patient")
@JsonTypeName("patient")
public class Patient extends User
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -742729100418082281L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Caregiver caregiver;
	//======================================================================================
		
	//====================================CONSTRUCTORS======================================
	
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	
	//======================================================================================

	
}
