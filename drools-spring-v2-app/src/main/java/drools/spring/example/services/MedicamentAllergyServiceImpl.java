package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.MedicamentAllergy;
import drools.spring.example.repositories.MedicamentAllergyRepository;

@Service
public class MedicamentAllergyServiceImpl implements MedicamentAllergyService{

	@Autowired
	private MedicamentAllergyRepository medicamentAllergyRepository;

	@Override
	public MedicamentAllergy findOne(Long id) {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.findOne(id);
	}

	@Override
	public Collection<MedicamentAllergy> findAll() {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.findAll();
	}

	@Override
	public MedicamentAllergy save(MedicamentAllergy medicamentAllergy) throws Exception {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.save(medicamentAllergy);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		medicamentAllergyRepository.delete(id);
	}

	@Override
	public void delete(Collection<MedicamentAllergy> medicamentAllergies) throws Exception {
		// TODO Auto-generated method stub
		medicamentAllergyRepository.delete(medicamentAllergies);
	}

	@Override
	public Collection<MedicamentAllergy> findByPatientId(Long id) {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.findByPatientId(id);
	}

	@Override
	public Collection<MedicamentAllergy> findByMedicamentId(Long id) {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.findByMedicamentId(id);
	}

	@Override
	public MedicamentAllergy findByPatientIdAndMedicamentId(Long patientId, Long medicamentId) {
		// TODO Auto-generated method stub
		return medicamentAllergyRepository.findByPatientIdAndMedicamentId(patientId, medicamentId);
	}

}