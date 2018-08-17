package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.Medicament;

public interface IngredientService {
	Collection<Ingredient> findAll();
	Collection<Ingredient> findByMedicament(Medicament medicament);
	Ingredient findOne(Long id);
	Ingredient save(Ingredient ingredient) throws Exception;
	void delete(Long id) throws Exception;
	void delete(Collection<Ingredient> ingredients) throws Exception;
}
