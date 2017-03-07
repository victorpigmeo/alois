package br.com.alois.domain.entity.route;

import java.io.Serializable;

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
public class AloisLatLng implements Serializable 
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -6618831162858353856L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("latitude")
	private double latitude;
	
	@JsonProperty("longitude")
	private double longitude;
	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	public AloisLatLng(){};
	
	public AloisLatLng(
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
	
	//======================================================================================
}
