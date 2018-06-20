package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Doctor;
import drools.spring.example.repositories.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	private final KieContainer kieContainer;
	
    @Autowired
    public DoctorServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@Override
	public Collection<Doctor> findAll() {
		// TODO Auto-generated method stub
		return doctorRepository.findAll();
	}

	@Override
	public Doctor findOne(Long id) {
		// TODO Auto-generated method stub
		return doctorRepository.findOne(id);
	}

	@Override
	public Doctor save(Doctor doctor) throws Exception {
		// TODO Auto-generated method stub
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(doctor);
	    kieSession.fireAllRules();
	    kieSession.dispose();
		return doctorRepository.save(doctor);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		doctorRepository.delete(id);
	}

}