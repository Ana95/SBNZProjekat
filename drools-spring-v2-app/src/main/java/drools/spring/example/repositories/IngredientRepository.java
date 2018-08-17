package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.Medicament;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	Collection<Ingredient> findByMedicament(Medicament medicament);
}
