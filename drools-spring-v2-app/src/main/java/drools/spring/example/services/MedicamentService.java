package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Medicament;

public interface MedicamentService {
	Collection<Medicament> findAll();
	Medicament findOne(Long id);
	Medicament save(Medicament medicament) throws Exception;
	void delete(Long id) throws Exception;

}
