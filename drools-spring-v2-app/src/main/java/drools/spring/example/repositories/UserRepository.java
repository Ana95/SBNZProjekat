package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.User;
import drools.spring.example.facts.User.ROLE;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	Collection<User> findByRole(ROLE role);
}
