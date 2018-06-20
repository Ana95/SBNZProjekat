package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long>{

}
