package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Symptom;

public interface SymptomService {
	Collection<Symptom> findAll();
	Collection<Symptom> findByIllness(Illness illness);
	Collection<Symptom> findByIllnessOrderByIsSpecificDesc(Illness illness);
	Symptom findOne(Long id);
	Symptom save(Symptom symptom) throws Exception;
	void delete(Long id) throws Exception;
	void delete(Collection<Symptom> symptoms) throws Exception;

}
