package drools.spring.example.facts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MedicamentAllergy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long medicamentId;
	
	private String medicamentName;
	
	private Long patientId;

	public MedicamentAllergy() {
		super();
	}

	public MedicamentAllergy(Long medicamentId, String medicamentName, Long patientId) {
		super();
		this.medicamentId = medicamentId;
		this.medicamentName = medicamentName;
		this.patientId = patientId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}

	public String getMedicamentName() {
		return medicamentName;
	}

	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public String toString() {
		return "MedicamentAllergy [id=" + id + ", medicamentId=" + medicamentId + ", medicamentName=" + medicamentName
				+ ", patientId=" + patientId + "]";
	}

}