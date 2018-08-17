package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.MedicamentAllergy;

public interface MedicamentAllergyService {
	MedicamentAllergy findOne(Long id);
	Collection<MedicamentAllergy> findAll();
	MedicamentAllergy save(MedicamentAllergy medicamentAllergy) throws Exception;
	void delete(Long id) throws Exception;
	void delete(Collection<MedicamentAllergy> medicamentAllergies) throws Exception;
	Collection<MedicamentAllergy> findByPatientId(Long id);
	Collection<MedicamentAllergy> findByMedicamentId(Long id);
	MedicamentAllergy findByPatientIdAndMedicamentId(Long patientId, Long medicamentId);
	
}
