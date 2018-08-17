package drools.spring.example.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Diagnose;
import drools.spring.example.facts.DiagnoseMedicament;
import drools.spring.example.facts.DiagnoseSymptom;
import drools.spring.example.repositories.DiagnoseRepository;

@Service
public class DiagnoseServiceImpl implements DiagnoseService{
	
	@Autowired
	private DiagnoseRepository diagnoseRepository;
	
	@Autowired
	private DiagnoseSymptomService diagnoseSymptomService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;

	@Override
	public Collection<Diagnose> findAll() {
		// TODO Auto-generated method stub
		return diagnoseRepository.findAll();
	}

	@Override
	public Collection<Diagnose> findByPatientId(Long patientId) {
		// TODO Auto-generated method stub
		return diagnoseRepository.findByPatientId(patientId);
	}

	@Override
	public Collection<Diagnose> findByDoctorId(Long doctorId) {
		// TODO Auto-generated method stub
		return diagnoseRepository.findByDoctorId(doctorId);
	}

	@Override
	public Collection<Diagnose> findByIllnessName(String illnessName) {
		// TODO Auto-generated method stub
		return diagnoseRepository.findByIllnessName(illnessName);
	}

	@Override
	public Diagnose findOne(Long id) {
		// TODO Auto-generated method stub
		return diagnoseRepository.findOne(id);
	}

	@Override
	public Diagnose findByPatientIdAndDoctorIdAndIllnessName(Long patientId, Long doctorId, String illnessName) {
		// TODO Auto-generated method stub
		return diagnoseRepository.findByPatientIdAndDoctorIdAndIllnessName(patientId, doctorId, illnessName);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<DiagnoseSymptom> symptoms = (ArrayList<DiagnoseSymptom>) diagnoseSymptomService.findByDiagnoseId(id);
		if(!symptoms.isEmpty()){
			diagnoseSymptomService.delete(symptoms);
		}
		
		ArrayList<DiagnoseMedicament> medicaments = (ArrayList<DiagnoseMedicament>) diagnoseMedicamentService.findByDiagnoseId(id);
		if(!medicaments.isEmpty()){
			diagnoseMedicamentService.delete(medicaments);
		}
		diagnoseRepository.delete(id);
	}

	@Override
	public void delete(Collection<Diagnose> diagnoses) throws Exception {
		// TODO Auto-generated method stub
		diagnoseRepository.delete(diagnoses);		
	}

	@Override
	public Diagnose save(Diagnose diagnose) throws Exception {
		// TODO Auto-generated method stub
		return diagnoseRepository.save(diagnose);
	}
	
}
