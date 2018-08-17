package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.DiagnoseMedicament;
import drools.spring.example.repositories.DiagnoseMedicamentRepository;

@Service
public class DiagnoseMedicamentServiceImpl implements DiagnoseMedicamentService{

	@Autowired
	private DiagnoseMedicamentRepository diagnoseMedicamentRepository;

	@Override
	public DiagnoseMedicament findOne(Long id) {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.findOne(id);
	}

	@Override
	public DiagnoseMedicament findByDiagnoseIdAndMedicamentId(Long diagnoseId, Long medicamentId) {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.findByDiagnoseIdAndMedicamentId(diagnoseId, medicamentId);
	}

	@Override
	public Collection<DiagnoseMedicament> findByMedicamentId(Long medicamentId) {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.findByMedicamentId(medicamentId);
	}

	@Override
	public Collection<DiagnoseMedicament> findByDiagnoseId(Long diagnoseId) {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.findByDiagnoseId(diagnoseId);
	}

	@Override
	public Collection<DiagnoseMedicament> findAll() {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.findAll();
	}

	@Override
	public DiagnoseMedicament save(DiagnoseMedicament diagnoseMedicament) throws Exception {
		// TODO Auto-generated method stub
		return diagnoseMedicamentRepository.save(diagnoseMedicament);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		diagnoseMedicamentRepository.delete(id);
		
	}

	@Override
	public void delete(Collection<DiagnoseMedicament> medicaments) throws Exception {
		// TODO Auto-generated method stub
		diagnoseMedicamentRepository.delete(medicaments);
	}
	
}
