package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Medicine;
import drools.spring.example.repositories.MedicineRepository;

@Service
public class MedicineServiceImpl implements MedicineService {

	@Autowired
	private MedicineRepository medicineRepository;
	
	private final KieContainer kieContainer;
	
    @Autowired
    public MedicineServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@Override
	public Collection<Medicine> findAll() {
		// TODO Auto-generated method stub
		return medicineRepository.findAll();
	}

	@Override
	public Medicine findOne(Long id) {
		// TODO Auto-generated method stub
		return medicineRepository.findOne(id);
	}

	@Override
	public Medicine save(Medicine medicine) throws Exception {
		// TODO Auto-generated method stub
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(medicine);
	    kieSession.dispose();
		return medicineRepository.save(medicine);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		medicineRepository.delete(id);
	}

}