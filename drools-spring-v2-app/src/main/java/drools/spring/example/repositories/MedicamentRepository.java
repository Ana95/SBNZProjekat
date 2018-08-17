package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}
