package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.IngredientAllergy;

public interface IngredientAllergyService {
	IngredientAllergy findOne(Long id);
	IngredientAllergy save(IngredientAllergy ingredientAllergy) throws Exception;
	Collection<IngredientAllergy> findAll();
	Collection<IngredientAllergy> findByPatientId(Long id);
	Collection<IngredientAllergy> findByIngredientId(Long id);
	IngredientAllergy findByPatientIdAndIngredientId(Long  patientId, Long ingredientId);
	void delete(Long id) throws Exception;
	void delete(Collection<IngredientAllergy> ingredientAllergies) throws Exception;
}
