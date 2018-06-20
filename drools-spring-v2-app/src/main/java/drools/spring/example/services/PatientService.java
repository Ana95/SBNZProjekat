package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Patient;

public interface PatientService {
	Collection<Patient> findAll();
	Patient findOne(Long id);
	Patient save(Patient patient) throws Exception;
	void delete(Long id) throws Exception;
}
