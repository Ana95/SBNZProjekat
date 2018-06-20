package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
