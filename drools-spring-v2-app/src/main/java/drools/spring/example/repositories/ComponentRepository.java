package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Component;

public interface ComponentRepository extends JpaRepository<Component, Long>{

}
