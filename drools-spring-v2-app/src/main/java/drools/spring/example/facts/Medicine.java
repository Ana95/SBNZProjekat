package drools.spring.example.facts;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Medicine {
	
	public enum MEDICINETYPE{
		ANTIBIOTIC,
		ANALGESIC,
		OTHER
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
    @ManyToMany
    @JoinTable(
    	name = "medicine_components",
    	joinColumns = { @JoinColumn(name = "medicine_id") },
    	inverseJoinColumns = { @JoinColumn(name = "component_id") }
    )
	private Set<Component> components;
	
	private MEDICINETYPE type;

	public Medicine() {
		super();
	}

	public Medicine(Long id, String title, Set<Component> components, MEDICINETYPE type) {
		super();
		this.id = id;
		this.title = title;
		this.components = components;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Component> getComponents() {
		return components;
	}

	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	public MEDICINETYPE getType() {
		return type;
	}

	public void setType(MEDICINETYPE type) {
		this.type = type;
	}

}