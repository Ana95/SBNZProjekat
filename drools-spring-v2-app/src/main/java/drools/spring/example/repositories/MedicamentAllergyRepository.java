package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.MedicamentAllergy;

public interface MedicamentAllergyRepository extends JpaRepository<MedicamentAllergy, Long> {
	Collection<MedicamentAllergy> findByPatientId(Long id);
	Collection<MedicamentAllergy> findByMedicamentId(Long id);
	MedicamentAllergy findByPatientIdAndMedicamentId(Long patientId, Long medicamentId);
}
