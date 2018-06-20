package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Symptom;
import drools.spring.example.repositories.SymptomRepository;

@Service
public class SymptomServiceImpl implements SymptomService{
	
	@Autowired
	private SymptomRepository symptomRepository;
	
	private final KieContainer kieContainer;
	
    @Autowired
    public SymptomServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

	@Override
	public Collection<Symptom> findAll() {
		// TODO Auto-generated method stub
		return symptomRepository.findAll();
	}

	@Override
	public Symptom findOne(Long id) {
		// TODO Auto-generated method stub
		return symptomRepository.findOne(id);
	}

	@Override
	public Symptom save(Symptom symptom) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(symptom.getTitle());
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(symptom);
	    kieSession.fireAllRules();
	    kieSession.dispose();
		return symptomRepository.save(symptom);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		symptomRepository.delete(id);
	}
	
}
