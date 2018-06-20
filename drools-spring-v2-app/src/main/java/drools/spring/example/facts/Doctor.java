package drools.spring.example.facts;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends User{

	private ROLE role;
	
	public Doctor() {
		super();
	}

	public Doctor(Long id, String username, String password, String name, String surname) {
		super(id, username, password, name, surname);
		this.role = ROLE.DOCTOR;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}
	
}
