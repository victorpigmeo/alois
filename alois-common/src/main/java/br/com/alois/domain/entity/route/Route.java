package br.com.alois.domain.entity.route;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Audited
@Table(name="route")
public class Route implements Serializable
{
	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -8580815217803600937L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String description;
	
	@OneToMany(orphanRemoval = true)
	private List<Step> steps;
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
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public List<Step> getSteps() 
	{
		return steps;
	}
	
	public void setSteps(List<Step> steps) 
	{
		this.steps = steps;
	}
	//======================================================================================
	
	//=====================================BEHAVIOUR========================================
	
	//======================================================================================
	
}
