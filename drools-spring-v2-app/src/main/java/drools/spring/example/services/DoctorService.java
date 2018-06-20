package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Doctor;

public interface DoctorService {
	Collection<Doctor> findAll();
	Doctor findOne(Long id);
	Doctor save(Doctor doctor) throws Exception;
	void delete(Long id) throws Exception;
}
