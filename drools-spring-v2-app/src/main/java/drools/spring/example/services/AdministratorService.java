package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Administrator;

public interface AdministratorService {
	Collection<Administrator> findAll();
	Administrator findOne(Long id);
	Administrator save(Administrator administrator) throws Exception;
	void delete(Long id) throws Exception;
}
