package br.com.alois.domain.entity.route;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "step")
public class Step implements Serializable
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = 4355801235764459596L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AloisLatLng startPoint;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AloisLatLng endPoint;
	//======================================================================================
	
	//=====================================INJECTIONS=======================================
	
	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public AloisLatLng getStartPoint() 
	{
		return startPoint;
	}

	public void setStartPoint(AloisLatLng startPoint) 
	{
		this.startPoint = startPoint;
	}

	public AloisLatLng getEndPoint() 
	{
		return endPoint;
	}

	public void setEndPoint(AloisLatLng endPoint) 
	{
		this.endPoint = endPoint;
	}
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	
	//======================================================================================
}
