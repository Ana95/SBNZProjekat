package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.IngredientAllergy;
import drools.spring.example.repositories.IngredientAllergyRepository;

@Service
public class IngredientAllergyServiceImpl implements IngredientAllergyService{
	
	@Autowired
	private IngredientAllergyRepository ingredientAllergyRepository;

	@Override
	public IngredientAllergy findOne(Long id) {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.findOne(id);
	}

	@Override
	public IngredientAllergy save(IngredientAllergy ingredientAllergy) throws Exception {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.save(ingredientAllergy);
	}

	@Override
	public Collection<IngredientAllergy> findAll() {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.findAll();
	}

	@Override
	public Collection<IngredientAllergy> findByPatientId(Long id) {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.findByPatientId(id);
	}

	@Override
	public Collection<IngredientAllergy> findByIngredientId(Long id) {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.findByIngredientId(id);
	}

	@Override
	public IngredientAllergy findByPatientIdAndIngredientId(Long patientId, Long ingredientId) {
		// TODO Auto-generated method stub
		return ingredientAllergyRepository.findByPatientIdAndIngredientId(patientId, ingredientId);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		ingredientAllergyRepository.delete(id);
	}

	@Override
	public void delete(Collection<IngredientAllergy> ingredientAllergies) throws Exception {
		// TODO Auto-generated method stub
		ingredientAllergyRepository.delete(ingredientAllergies);
	}

}
