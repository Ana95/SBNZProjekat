package drools.spring.example.services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import drools.spring.example.facts.User;
import drools.spring.example.facts.User.ROLE;

public interface UserService {
	Collection<User> findAll();
	User findOne(Long id);
	User save(User user) throws Exception;
	void delete(Long id) throws Exception;
	User findByUsername(String username);
	Collection<User> findByRole(ROLE role);
	Boolean login(String username, String password, HttpServletRequest request);
}
