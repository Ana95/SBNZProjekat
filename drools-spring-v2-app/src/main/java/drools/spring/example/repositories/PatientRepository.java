package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
