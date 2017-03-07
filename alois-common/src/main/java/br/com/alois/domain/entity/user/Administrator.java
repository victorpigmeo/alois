package br.com.alois.domain.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Audited
@Table(name = "administrator")
@JsonTypeName("administrator")
public class Administrator extends User
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -4400685457083629204L;

	//======================================================================================
		
	//====================================CONSTRUCTORS======================================

	//======================================================================================

	//==================================GETTERS/SETTERS=====================================

	//======================================================================================

	//=====================================BEHAVIOUR========================================

	//======================================================================================


}
