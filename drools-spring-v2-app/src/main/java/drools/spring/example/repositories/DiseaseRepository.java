package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

}
