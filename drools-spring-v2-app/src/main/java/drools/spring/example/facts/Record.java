package drools.spring.example.facts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Record {

	private Long id;
	
	private Illness illness;
	
	private Patient patient;
	
	private Date date;
	
	private User doctor;
	
	private List<Medicament> medicaments = new ArrayList<Medicament>();

	public Record() {
		super();
		this.date = new Date();
	}

	public Record(Illness illness) {
		super();
		this.illness = illness;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Illness getIllness() {
		return illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", illness=" + illness + ", patient=" + patient + ", date=" + date + ", doctor="
				+ doctor + ", medicaments=" + medicaments + "]";
	}
	
}