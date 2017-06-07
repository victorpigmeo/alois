package br.com.alois.domain.entity.memory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.alois.domain.entity.user.Patient;
import net.coobird.thumbnailator.Thumbnails;

@Entity
@Audited
@Table(name="memory")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  scope = Memory.class,
		  property = "id")
public class Memory implements Serializable{

	//=====================================ATTRIBUTES=======================================
	private static final long serialVersionUID = -8234737950767286626L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable=false)
	private String title;

	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@NotEmpty
	@Column(nullable=false)
	private byte[] file;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Patient patient;
	
	@Transient
	private byte[] thumbnail;
	//======================================================================================
	
	//====================================CONSTRUCTORS======================================
	public Memory(
			String title,
			String description,
			byte[] file,
			Calendar creationDate,
			Patient patient
	)
	{
		this.setTitle(title);
		this.setDescription(description);
		this.setFile(file);
		this.setCreationDate(creationDate);
		this.setPatient(patient);
	}
	
	public Memory(){}
	
	//======================================================================================
	
	//==================================GETTERS/SETTERS=====================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	//======================================================================================
	//=====================================BEHAVIOUR========================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
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
		Memory other = (Memory) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		return true;
	}
	
	public void generateBase64PhotoThumbnail()
	{
		if(this.file != null)
		{
			ByteArrayInputStream inputStream = new ByteArrayInputStream(this.file);
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    
		    try
			{
				Thumbnails.of(inputStream)
					.size(60, 60)
					.keepAspectRatio(true)
				    .toOutputStream(outputStream);
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		    this.thumbnail = outputStream.toByteArray();
		    this.file = null;
		}
	}

	//======================================================================================

}
