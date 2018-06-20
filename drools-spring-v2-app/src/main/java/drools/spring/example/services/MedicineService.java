package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Medicine;

public interface MedicineService {
	Collection<Medicine> findAll();
	Medicine findOne(Long id);
	Medicine save(Medicine medicine) throws Exception;
	void delete(Long id) throws Exception;
}
