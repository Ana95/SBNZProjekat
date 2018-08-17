package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.DiagnoseSymptom;

public interface DiagnoseSymptomService {
	DiagnoseSymptom findOne(Long id);
	DiagnoseSymptom findByDiagnoseIdAndSymptomTerm(Long diagnoseId, String symptomTerm);
	Collection<DiagnoseSymptom> findByDiagnoseId(Long diagnoseId);
	Collection<DiagnoseSymptom> findBySymptomTerm(String symptomTerm);
	Collection<DiagnoseSymptom> findAll();
	DiagnoseSymptom save(DiagnoseSymptom diagnoseSymptom) throws Exception;
	void delete(Long id) throws Exception;
	void delete(Collection<DiagnoseSymptom> symptoms);
}
