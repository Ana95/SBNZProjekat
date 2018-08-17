package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.DiagnoseSymptom;
import drools.spring.example.repositories.DiagnoseSymptomRepository;

@Service
public class DiagnoseSymptomServiceImpl implements DiagnoseSymptomService{
	
	@Autowired
	private DiagnoseSymptomRepository diagnoseSymptomRepository;

	@Override
	public DiagnoseSymptom findOne(Long id) {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.findOne(id);
	}

	@Override
	public DiagnoseSymptom findByDiagnoseIdAndSymptomTerm(Long diagnoseId, String symptomTerm) {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.findByDiagnoseIdAndSymptomTerm(diagnoseId, symptomTerm);
	}

	@Override
	public Collection<DiagnoseSymptom> findByDiagnoseId(Long diagnoseId) {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.findByDiagnoseId(diagnoseId);
	}

	@Override
	public Collection<DiagnoseSymptom> findBySymptomTerm(String symptomTerm) {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.findBySymptomTerm(symptomTerm);
	}

	@Override
	public Collection<DiagnoseSymptom> findAll() {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.findAll();
	}

	@Override
	public DiagnoseSymptom save(DiagnoseSymptom diagnoseSymptom) throws Exception {
		// TODO Auto-generated method stub
		return diagnoseSymptomRepository.save(diagnoseSymptom);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		diagnoseSymptomRepository.delete(id);
	}

	@Override
	public void delete(Collection<DiagnoseSymptom> symptoms) {
		// TODO Auto-generated method stub
		diagnoseSymptomRepository.delete(symptoms);
	}

}