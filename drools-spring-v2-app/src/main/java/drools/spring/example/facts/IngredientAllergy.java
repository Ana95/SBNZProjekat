package drools.spring.example.facts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IngredientAllergy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long ingredientId;
	
	private String ingredientName;
	
	private Long patientId;

	public IngredientAllergy() {
		super();
	}

	public IngredientAllergy(Long ingredientId, String ingredientName, Long patientId) {
		super();
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.patientId = patientId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public String toString() {
		return "IngridientAllergy [id=" + id + ", ingredientId=" + ingredientId + ", ingredientName=" + ingredientName
				+ ", patientId=" + patientId + "]";
	}
	
}