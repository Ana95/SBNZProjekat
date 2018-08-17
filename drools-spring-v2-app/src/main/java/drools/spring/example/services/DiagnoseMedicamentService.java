package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.DiagnoseMedicament;

public interface DiagnoseMedicamentService {
	DiagnoseMedicament findOne(Long id);
	DiagnoseMedicament findByDiagnoseIdAndMedicamentId(Long diagnoseId, Long medicamentId);
	Collection<DiagnoseMedicament> findByMedicamentId(Long medicamentId);
	Collection<DiagnoseMedicament> findByDiagnoseId(Long diagnoseId);
	Collection<DiagnoseMedicament> findAll();
	DiagnoseMedicament save(DiagnoseMedicament diagnoseMedicament) throws Exception;
	void delete(Long id) throws Exception;
	void delete(Collection<DiagnoseMedicament> medicaments) throws Exception;	
}
