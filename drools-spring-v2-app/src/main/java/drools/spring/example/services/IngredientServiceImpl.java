package drools.spring.example.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.Medicament;
import drools.spring.example.repositories.IngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService{
	
	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	public Collection<Ingredient> findAll() {
		// TODO Auto-generated method stub
		return ingredientRepository.findAll();
	}

	@Override
	public Collection<Ingredient> findByMedicament(Medicament medicament) {
		// TODO Auto-generated method stub
		return ingredientRepository.findByMedicament(medicament);
	}

	@Override
	public Ingredient findOne(Long id) {
		// TODO Auto-generated method stub
		return ingredientRepository.findOne(id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) throws Exception {
		// TODO Auto-generated method stub
		return ingredientRepository.save(ingredient);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		ingredientRepository.delete(id);
	}
	
	public void delete(Collection<Ingredient> ingredients) throws Exception{
		ingredientRepository.delete(ingredients);
	}

}