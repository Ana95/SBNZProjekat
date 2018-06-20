package drools.spring.example.facts;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Disease {
	
	public enum DISEASETYPE{
		FIRST_GROUP,
		SECOND_GROUP,
		THIRD_GROUP
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
    @ManyToMany
    @JoinTable(
    	name = "disease_symptoms",
    	joinColumns = { @JoinColumn(name = "disease_id") },
    	inverseJoinColumns = { @JoinColumn(name = "symptom_id") }
    )
	private Set<Symptom> symptoms;
	
	private DISEASETYPE type;
	
	public Disease() {
		super();
	}

	public Disease(Long id, String title, Set<Symptom> symptoms, DISEASETYPE type) {
		super();
		this.id = id;
		this.title = title;
		this.symptoms = symptoms;
		this.type = type;
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

	public Set<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Set<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public DISEASETYPE getType() {
		return type;
	}

	public void setType(DISEASETYPE type) {
		this.type = type;
	}
	
}
