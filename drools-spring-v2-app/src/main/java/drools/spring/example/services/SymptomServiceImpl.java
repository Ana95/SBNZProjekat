package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Symptom;
import drools.spring.example.repositories.SymptomRepository;

@Service
public class SymptomServiceImpl implements SymptomService{
	
	@Autowired
	private SymptomRepository symptomRepository;

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
		return symptomRepository.save(symptom);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		symptomRepository.delete(id);
	}

	@Override
	public Collection<Symptom> findByIllness(Illness illness) {
		// TODO Auto-generated method stub
		return symptomRepository.findByIllness(illness);
	}

	@Override
	public Collection<Symptom> findByIllnessOrderByIsSpecificDesc(Illness illness) {
		// TODO Auto-generated method stub
		return symptomRepository.findByIllnessOrderByIsSpecificDesc(illness);
	}

	@Override
	public void delete(Collection<Symptom> symptoms) throws Exception {
		// TODO Auto-generated method stub
		symptomRepository.delete(symptoms);
	}
	
}
