package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Medicament;
import drools.spring.example.repositories.MedicamentRepository;

@Service
public class MedicamentServiceImpl implements MedicamentService{
	
	@Autowired
	private MedicamentRepository medicamentRepository;

	@Override
	public Collection<Medicament> findAll() {
		// TODO Auto-generated method stub
		return medicamentRepository.findAll();
	}

	@Override
	public Medicament findOne(Long id) {
		// TODO Auto-generated method stub
		return medicamentRepository.findOne(id);
	}

	@Override
	public Medicament save(Medicament medicament) throws Exception {
		// TODO Auto-generated method stub
		return medicamentRepository.save(medicament);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		medicamentRepository.delete(id);
	}

}
