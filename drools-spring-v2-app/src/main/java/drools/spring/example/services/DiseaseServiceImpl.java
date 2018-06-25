package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Disease;
import drools.spring.example.repositories.DiseaseRepository;

@Service
public class DiseaseServiceImpl implements DiseaseService{

	@Autowired
	private DiseaseRepository diseaseRepository;
	
	private final KieContainer kieContainer;
	
	@Autowired
    public DiseaseServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@Override
	public Collection<Disease> findAll() {
		// TODO Auto-generated method stub
		return diseaseRepository.findAll();
	}

	@Override
	public Disease findOne(Long id) {
		// TODO Auto-generated method stub
		return diseaseRepository.findOne(id);
	}

	@Override
	public Disease save(Disease disease) throws Exception {
		// TODO Auto-generated method stub
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(disease);
		kieSession.fireAllRules();
		kieSession.dispose();
		return diseaseRepository.save(disease);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		diseaseRepository.delete(id);
	}

}