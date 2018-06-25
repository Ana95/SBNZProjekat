package drools.spring.example.facts;

import java.util.Date;
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
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String surname;
	
	private int age;
	
    @ManyToMany
    @JoinTable(
    	name = "patient_diseases",
    	joinColumns = { @JoinColumn(name = "patient_id") },
    	inverseJoinColumns = { @JoinColumn(name = "disease_id") }
    )
	Set<Disease> diseases;
	
    @ManyToMany
    @JoinTable(
    	name = "patient_medicines",
    	joinColumns = { @JoinColumn(name = "patient_id") },
    	inverseJoinColumns = { @JoinColumn(name = "medicine_id") }
    )
	Set<Medicine> medicines;

	public Patient() {
		super();
	}

	public Patient(Long id, String name, String surname, int age, Set<Disease> diseases,
			Set<Medicine> medicines) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.diseases = diseases;
		this.medicines = medicines;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(Set<Disease> diseases) {
		this.diseases = diseases;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}
	
}
