package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.DiagnoseMedicament;

public interface DiagnoseMedicamentRepository extends JpaRepository<DiagnoseMedicament, Long>{
	DiagnoseMedicament findByDiagnoseIdAndMedicamentId(Long diagnoseId, Long medicamentId);
	Collection<DiagnoseMedicament> findByDiagnoseId(Long diagnoseId);
	Collection<DiagnoseMedicament> findByMedicamentId(Long medicamentId);
}
