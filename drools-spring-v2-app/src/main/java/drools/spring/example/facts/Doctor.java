package drools.spring.example.facts;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends User{
	
	private String institution;
	
	public Doctor() {
		super();
	}

	public Doctor(Long id, String username, String password, String name, String surname, String institution) {
		super(id, username, password, name, surname, ROLE.DOCTOR);
		this.institution = institution;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
}
