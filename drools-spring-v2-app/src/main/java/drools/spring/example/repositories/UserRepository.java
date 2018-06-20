package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
