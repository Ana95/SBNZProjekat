package drools.spring.example.facts;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Medicament {

	public enum Category{
		ANTIBIOTICS, ANALGESICS, OTHER
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Category category;
	
	@JsonManagedReference(value = "medicamentIngredients")
	@OneToMany(mappedBy="medicament")
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();

	public Medicament() {
		super();
	}

	public Medicament(String name, Category category, List<Ingredient> ingredients) {
		super();
		this.name = name;
		this.category = category;
		this.ingredients = ingredients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Medicament [id=" + id + ", name=" + name + ", category=" + category + ", ingredients=" + ingredients
				+ "]";
	}
	
}