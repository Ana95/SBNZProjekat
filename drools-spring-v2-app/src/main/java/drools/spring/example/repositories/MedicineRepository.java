package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
