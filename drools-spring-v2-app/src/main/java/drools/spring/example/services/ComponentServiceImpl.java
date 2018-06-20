package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Component;
import drools.spring.example.repositories.ComponentRepository;

@Service
public class ComponentServiceImpl implements ComponentService{
	
	@Autowired
	private ComponentRepository componentRepository;
	
	@Override
	public Collection<Component> findAll() {
		// TODO Auto-generated method stub
		return componentRepository.findAll();
	}

	@Override
	public Component findOne(Long id) {
		// TODO Auto-generated method stub
		return componentRepository.findOne(id);
	}

	@Override
	public Component save(Component component) throws Exception {
		// TODO Auto-generated method stub
		return componentRepository.save(component);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		componentRepository.delete(id);
	}

}
