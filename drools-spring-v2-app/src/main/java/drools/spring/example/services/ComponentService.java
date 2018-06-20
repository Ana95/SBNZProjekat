package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Component;

public interface ComponentService {
	Collection<Component> findAll();
	Component findOne(Long id);
	Component save(Component component) throws Exception;
	void delete(Long id) throws Exception;
}
