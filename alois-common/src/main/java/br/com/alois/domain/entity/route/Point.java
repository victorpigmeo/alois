package br.com.alois.domain.entity.route;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Audited
@Table(name="point")
public class Point implements Serializable 
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -6618831162858353856L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("latitude")
	@Column(nullable=false)
	private double latitude;
	
	@JsonProperty("longitude")
	@Column(nullable=false)
	private double longitude;
	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	public Point(){};
	
	public Point(
			@JsonProperty("latitude") double latitude, 
			@JsonProperty("longitude") double longitude
			){
		this.latitude = latitude;
		this.longitude = longitude;
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

	public double getLatitude() 
	{
		return latitude;
	}

	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}

	public double getLongitude() 
	{
		return longitude;
	}

	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Point other = (Point) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
	
	//======================================================================================
}
