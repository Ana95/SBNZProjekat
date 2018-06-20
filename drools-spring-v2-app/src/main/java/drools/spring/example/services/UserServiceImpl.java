package drools.spring.example.services;

import java.util.Collection;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.User;
import drools.spring.example.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	private final KieContainer kieContainer;
	
    @Autowired
    public UserServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@Override
	public User save(User user) throws Exception {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(user);
	    kieSession.fireAllRules();
	    kieSession.dispose();
		return userRepository.save(user);
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findOne(id);
	}

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		userRepository.delete(id);
	}
	
}
