package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Long>{
	Collection<Symptom> findByIllness(Illness illness);
	Collection<Symptom> findByIllnessOrderByIsSpecificDesc(Illness illness);
}
