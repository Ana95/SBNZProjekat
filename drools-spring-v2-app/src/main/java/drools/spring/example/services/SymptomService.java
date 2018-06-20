package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Symptom;

public interface SymptomService {
	Collection<Symptom> findAll();
	Symptom findOne(Long id);
	Symptom save(Symptom symptom) throws Exception;
	void delete(Long id) throws Exception;

}
