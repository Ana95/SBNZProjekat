package drools.spring.example.facts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Symptom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Column
	private Boolean isSpecific;
	
	public Symptom() {
		super();
	}

	public Symptom(Long id, String title, Boolean isSpecific) {
		super();
		this.id = id;
		this.title = title;
		this.isSpecific = isSpecific;
	}

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

	public Boolean getIsSpecific() {
		return isSpecific;
	}

	public void setIsSpecific(Boolean isSpecific) {
		this.isSpecific = isSpecific;
	}

}
