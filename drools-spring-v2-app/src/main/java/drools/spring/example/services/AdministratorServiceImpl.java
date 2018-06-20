package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Administrator;
import drools.spring.example.repositories.AdministratorRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService{

	@Autowired
	private AdministratorRepository administratorRepository;
	
	private final KieContainer kieContainer;
	
    @Autowired
    public AdministratorServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@Override
	public Collection<Administrator> findAll() {
		// TODO Auto-generated method stub
		return administratorRepository.findAll();
	}

	@Override
	public Administrator findOne(Long id) {
		// TODO Auto-generated method stub
		return administratorRepository.findOne(id);
	}

	@Override
	public Administrator save(Administrator administrator) throws Exception {
		// TODO Auto-generated method stub
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(administrator);
	    kieSession.fireAllRules();
	    kieSession.dispose();
		return administratorRepository.save(administrator);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		administratorRepository.delete(id);
	}

}
