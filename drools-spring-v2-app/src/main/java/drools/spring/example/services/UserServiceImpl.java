package drools.spring.example.services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.User;
import drools.spring.example.facts.User.ROLE;
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

	@Override
	public Boolean login(String username, String password, HttpServletRequest request) {
		// TODO Auto-generated method stub
		User user = findByUsername(username);
		if(user != null){
			if(user.getPassword().equals(password)){
				request.getSession().setAttribute("currentUser", user);
				
    			KieServices ks = KieServices.Factory.get();
    			KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
    			kbconf.setOption(EventProcessingOption.STREAM);
    			KieBase kbase = kieContainer.newKieBase(kbconf);
    			KieSession kieSession = kbase.newKieSession();
    			
    			request.getSession().setAttribute("kieSession", kieSession);
    			System.out.println("Sesija pri logovanju " + request.getSession().getAttribute("kieSession"));
    			return true;
			}
		}
		return false;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public Collection<User> findByRole(ROLE role) {
		// TODO Auto-generated method stub
		return userRepository.findByRole(role);
	}
	
}