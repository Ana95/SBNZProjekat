package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.User;

public interface UserService {
	Collection<User> findAll();
	User findOne(Long id);
	User save(User user) throws Exception;
	void delete(Long id) throws Exception;
}
