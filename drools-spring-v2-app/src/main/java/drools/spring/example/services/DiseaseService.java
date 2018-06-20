package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Disease;

public interface DiseaseService {
	Collection<Disease> findAll();
	Disease findOne(Long id);
	Disease save(Disease disease) throws Exception;
	void delete(Long id) throws Exception;
	
}
