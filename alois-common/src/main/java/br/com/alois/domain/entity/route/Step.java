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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Audited
@Table(name = "step")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  scope = Step.class,
		  property = "id")
public class Step implements Serializable
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = 4355801235764459596L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Point startPoint;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Point endPoint;
	
	private int sequence;

	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference
	private Route route;
	//======================================================================================
	
	//=====================================INJECTIONS=======================================
	
	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	public Step(){}
	
	public Step(Point startPoint, Point endPoint, int sequence)
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.sequence = sequence;
	}
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

	public Point getStartPoint() 
	{
		return startPoint;
	}

	public void setStartPoint(Point startPoint) 
	{
		this.startPoint = startPoint;
	}

	public Point getEndPoint() 
	{
		return endPoint;
	}

	public void setEndPoint(Point endPoint) 
	{
		this.endPoint = endPoint;
	}
	
	public Route getRoute() 
	{
		return route;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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
	
	@JsonIgnore
	public boolean isPointNearStepLine(Point location, Double patientLimit)
	{
		//Get the distance in degrees
		Double distancia = (((this.endPoint.getLatitude() - this.startPoint.getLatitude()) * (this.startPoint.getLongitude() - location.getLongitude())) - ((this.startPoint.getLatitude() - location.getLatitude()) * (this.getEndPoint().getLongitude() - this.startPoint.getLongitude()))) / Math.sqrt( (Math.pow( (this.endPoint.getLatitude() - this.startPoint.getLatitude()), 2) + Math.pow( (this.endPoint.getLongitude() - this.startPoint.getLongitude()), 2) ) );
		//Convert to meters
		distancia = Math.abs((distancia * 111.325) * 1000);
		
		System.out.println(distancia);
		System.out.println(patientLimit);
		if(distancia < patientLimit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	//======================================================================================
}
