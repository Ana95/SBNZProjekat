package drools.spring.example.facts;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Administrator extends User{
	
	public Administrator() {
		super();
	}

	public Administrator(Long id, String username, String password, String name, String surname) {
		super(id, username, password, name, surname, ROLE.ADMIN);
	}
	
}