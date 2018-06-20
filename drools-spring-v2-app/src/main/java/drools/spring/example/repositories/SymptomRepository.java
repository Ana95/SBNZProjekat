package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Long>{

}
