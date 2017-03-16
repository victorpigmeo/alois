package br.com.alois.domain.entity.route;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Route route;
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
	
	public Route getRoute() 
	{
		return route;
	}

	public void setRoute(Route route) 
	{
		this.route = route;
	}
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
	}
	
	//======================================================================================
}
