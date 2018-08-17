package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Patient;
import drools.spring.example.repositories.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public Collection<Patient> findAll() {
		// TODO Auto-generated method stub
		return patientRepository.findAll();
	}

	@Override
	public Patient findOne(Long id) {
		// TODO Auto-generated method stub
		return patientRepository.findOne(id);
	}

	@Override
	public Patient save(Patient patient) throws Exception {
		// TODO Auto-generated method stub
		return patientRepository.save(patient);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		patientRepository.delete(id);
	}

}