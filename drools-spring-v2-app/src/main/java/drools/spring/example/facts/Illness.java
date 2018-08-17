package drools.spring.example.facts;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Illness {
	
	public enum ILLNESS_TYPE{
		FIRST,
		SECOND,
		THIRD
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private ILLNESS_TYPE illness_type;
	
	@JsonManagedReference(value = "illnessSymptoms")
	@OneToMany(mappedBy="illness")
	private List<Symptom> symptoms = new ArrayList<Symptom>();
	
	@Transient
	private Integer symptomsFound;
	
	@Transient
	private Integer specificSymptomsFound;
	
	@Transient
	private List<Symptom.Term> symptomsTerms = new ArrayList<Symptom.Term>();
	
	@Transient
	private List<Symptom.Term> symptomTermsFound = new ArrayList<Symptom.Term>();

	public Illness() {
		super();
	}

	public Illness(String name, ILLNESS_TYPE illness_type) {
		super();
		this.name = name;
		this.illness_type = illness_type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ILLNESS_TYPE getIllness_type() {
		return illness_type;
	}

	public void setIllness_type(ILLNESS_TYPE illness_type) {
		this.illness_type = illness_type;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Integer getSymptomsFound() {
		return symptomsFound;
	}

	public void setSymptomsFound(Integer symptomsFound) {
		this.symptomsFound = symptomsFound;
	}

	public Integer getSpecificSymptomsFound() {
		return specificSymptomsFound;
	}

	public void setSpecificSymptomsFound(Integer specificSymptomsFound) {
		this.specificSymptomsFound = specificSymptomsFound;
	}

	public List<Symptom.Term> getSymptomsTerms() {
		return symptomsTerms;
	}

	public void setSymptomsTerms(List<Symptom.Term> symptomsTerms) {
		this.symptomsTerms = symptomsTerms;
	}

	public List<Symptom.Term> getSymptomTermsFound() {
		return symptomTermsFound;
	}

	public void setSymptomTermsFound(List<Symptom.Term> symptomTermsFound) {
		this.symptomTermsFound = symptomTermsFound;
	}

	@Override
	public String toString() {
		return "Illness [id=" + id + ", name=" + name + ", illness_type=" + illness_type + ", symptoms=" + symptoms
				+ ", symptomsFound=" + symptomsFound + ", specificSymptomsFound=" + specificSymptomsFound
				+ ", symptomsTerms=" + symptomsTerms + ", symptomTermsFound=" + symptomTermsFound + "]";
	}
	
}