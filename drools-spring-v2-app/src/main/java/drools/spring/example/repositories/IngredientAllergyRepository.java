package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.IngredientAllergy;

public interface IngredientAllergyRepository extends JpaRepository<IngredientAllergy, Long> {
	Collection<IngredientAllergy> findByPatientId(Long id);
	Collection<IngredientAllergy> findByIngredientId(Long id);
	IngredientAllergy findByPatientIdAndIngredientId(Long patientId, Long ingredientId);
}
