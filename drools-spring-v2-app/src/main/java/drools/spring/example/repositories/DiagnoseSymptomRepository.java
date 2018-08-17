package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.DiagnoseSymptom;

public interface DiagnoseSymptomRepository extends JpaRepository<DiagnoseSymptom, Long>{
	DiagnoseSymptom findByDiagnoseIdAndSymptomTerm(Long diagnoseId, String symptomTerm);
	Collection<DiagnoseSymptom> findByDiagnoseId(Long diagnoseId);
	Collection<DiagnoseSymptom> findBySymptomTerm(String symptomTerm);
}
