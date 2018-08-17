package drools.spring.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.ClassObjectFilter;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Diagnose;
import drools.spring.example.facts.DiagnoseMedicament;
import drools.spring.example.facts.DiagnoseSymptom;
import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.IngredientAllergy;
import drools.spring.example.facts.Medicament;
import drools.spring.example.facts.MedicamentAllergy;
import drools.spring.example.facts.Patient;
import drools.spring.example.facts.Record;
import drools.spring.example.facts.Symptom;
import drools.spring.example.facts.User;
import drools.spring.example.repositories.IllnessRepository;

@Service
public class IllnessServiceImpl implements IllnessService{
	
	@Autowired
	private IllnessRepository illnessRepository;
	
	@Autowired
	private SymptomService symptomService;
	
	@Autowired
	private MedicamentAllergyService medicamentAllergyService;
	
	@Autowired
	private IngredientAllergyService ingredientAllergyService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnoseSymptomService diagnoseSymptomService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;
	
	@Override
	public Illness save(Illness illness) throws Exception {
		// TODO Auto-generated method stub
		return illnessRepository.save(illness);
	}

	@Override
	public Illness findOne(Long id) {
		// TODO Auto-generated method stub
		return illnessRepository.findOne(id);
	}

	@Override
	public Illness findByName(String name) {
		// TODO Auto-generated method stub
		return illnessRepository.findByName(name);
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		illnessRepository.delete(id);
	}

	@Override
	public Collection<Illness> findAll() {
		// TODO Auto-generated method stub
		return illnessRepository.findAll();
	}
	
	public ArrayList<Illness> getOneIllness(ArrayList<Symptom> symptoms, Long patientId, HttpServletRequest request){
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		if(!symptoms.isEmpty()){
			symptoms = handleSymptoms(symptoms);
		}
		
		ArrayList<Illness> illnesses = (ArrayList<Illness>) findAll();
		addSymptomsAndIllnesses(kieSession, symptoms, patientId, illnesses);
		insertDiagnoseInSession(kieSession, patientId);
		
		Illness illnessCatFirst = new Illness();
		illnessCatFirst.setName("illnessFirstCat");
		kieSession.insert(illnessCatFirst);
		
		Illness illnessCatSecond = new Illness();
		illnessCatSecond.setName("illnessSecondCat");
		kieSession.insert(illnessCatSecond);
		
		Illness illnessCatThird = new Illness();
		illnessCatThird.setName("illnessThirdCat");
		kieSession.insert(illnessCatThird);
		
		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		kieSession.fireAllRules();
		
		kieSession.getAgenda().getAgendaGroup("set-illness").setFocus();
		kieSession.fireAllRules();
		
		System.out.println(illnessCatFirst.getName());
		System.out.println(illnessCatSecond.getName());
		System.out.println(illnessCatThird.getName());
		
		ArrayList<Illness> foundIllnesses = new ArrayList<Illness>();
		
		if(!illnessCatFirst.getName().equals("illnessFirstCat")){
			foundIllnesses.add(findByName(illnessCatFirst.getName()));
		}
		
		if(!illnessCatSecond.getName().equals("illnessSecondCat")){
			foundIllnesses.add(findByName(illnessCatSecond.getName()));
		}
		
		if(!illnessCatThird.getName().equals("illnessThirdCat")){
			foundIllnesses.add(findByName(illnessCatThird.getName()));
		}
		
		releaseObjectsFromSession(kieSession);
		return foundIllnesses;
	}
	
	public ArrayList<Illness> getAllIllness(ArrayList<Symptom> symptoms, Long patientId, HttpServletRequest request){
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		if(!symptoms.isEmpty()){
			symptoms = handleSymptoms(symptoms);
		}
		ArrayList<Illness> illnesses = (ArrayList<Illness>) findAll();
		addSymptomsAndIllnesses(kieSession, symptoms, patientId, illnesses);
		insertDiagnoseInSession(kieSession, patientId);
		
		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		int num = kieSession.fireAllRules();
		System.out.println("Broj okinutih pravila: " + num);
		ArrayList<Illness> new_illnesses = new ArrayList<Illness>();
		for (Illness illness : illnesses) {
			if(illness.getSymptomsFound() > 0){
				new_illnesses.add(illness);
			}
		}
		
		Collections.sort(new_illnesses, symptomsFoundComparator);
		releaseObjectsFromSession(kieSession);
		return new_illnesses;
	}
	
	public void addSymptomsAndIllnesses(KieSession kieSession, ArrayList<Symptom> symptoms, Long patientId, 
			ArrayList<Illness> illnesses){
		kieSession.setGlobal("pId", patientId);
		
		ArrayList<Illness> findIllnesses = (ArrayList<Illness>) findAll();
		Illness i = findIllnesses.get(0);
		if(!symptoms.isEmpty()){
			for (Symptom symptom : symptoms) {
				symptom.setIllness(i);
				kieSession.insert(symptom);
			}
		}else{
			Symptom s = new Symptom();
			s.setIllness(i);
			kieSession.insert(s);
		}
		
		for (Illness illness : illnesses) {
			illness = findOne(illness.getId());
			illness.setSymptomsFound(0);
			illness.setSpecificSymptomsFound(0);
			illness.setSymptomTermsFound(new ArrayList<Symptom.Term>());
			if(!illness.getSymptoms().isEmpty()){
				for (Symptom s : illness.getSymptoms()) {
					illness.getSymptomsTerms().add(s.getTerm());
				}
				kieSession.insert(illness);
			}else{
				kieSession.insert(illness);
			}
		}
		
	}
	
	public ArrayList<Symptom> getIllnessSymptoms(Illness illness, HttpServletRequest request){
		ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		ArrayList<Symptom> all_symptoms = (ArrayList<Symptom>) symptomService.findAll();
		if(!all_symptoms.isEmpty()){
			for (Symptom symptom : all_symptoms) {
				kieSession.insert(symptom);
			}
		}
		illness.getSymptoms().clear();
		kieSession.insert(illness);
		kieSession.getAgenda().getAgendaGroup("symptoms").setFocus();
		kieSession.fireAllRules();
		symptoms = (ArrayList<Symptom>) illness.getSymptoms();
		Collections.sort(symptoms, symptomsSpecificComparator);
		releaseObjectsFromSession(kieSession);
		return symptoms;
	}

	public ArrayList<Symptom> handleSymptoms(ArrayList<Symptom> symptoms){
		ArrayList<Symptom> newSymptoms = new ArrayList<Symptom>();
		for (Symptom s : symptoms) {
			System.out.println("simptomi: " + s.getTerm());
			Symptom s1 = new Symptom();
			if(s.getTemperature() != null){
				if(s.getTemperature() >= 38 && s.getTemperature() < 40){
					s1.setTerm(Symptom.Term.TEMP_OVER_38);
				}
				if(s.getTemperature() >= 40 && s.getTemperature() <= 41){
					s1.setTerm(Symptom.Term.TEMP_BETWEEN_40_AND_41);
				}
			}else{
				s1.setTerm(s.getTerm());
			}
			boolean flag = true;
			for (Symptom s2 : newSymptoms) {
				if(s2.getTerm().toString().equals(s1.getTerm().toString())){
					flag = false;
					break;
				}
			}
			if(flag){
				newSymptoms.add(s1);
			}
		}
		return newSymptoms;
	}
	
	public void releaseObjectsFromSession(KieSession kieSession){
		kieSession.getObjects();
		
		for (Object object : kieSession.getObjects()) {
			kieSession.delete(kieSession.getFactHandle(object));
		}
	}
	
	public String setDiagnose(Record record, HttpServletRequest request) throws Exception{
		record.setDate(new Date());
		String allergies = "Pacijent je alergičan na: ";
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		kieSession.setGlobal("idPatient", record.getPatient().getId());
		
		if(!record.getMedicaments().isEmpty()){
			for (Medicament medicament : record.getMedicaments()) {
				kieSession.insert(medicament);
				if(!medicament.getIngredients().isEmpty()){
					for (Ingredient ingredient : medicament.getIngredients()) {
						kieSession.insert(ingredient);
					}
				}
			}
		}
		
		ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findByPatientId(record.getPatient().getId());
		if(!medicamentAllergies.isEmpty()){
			for (MedicamentAllergy medicamentAllergy : medicamentAllergies) {
				kieSession.insert(medicamentAllergy);
			}
		}
		
		ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findByPatientId(record.getPatient().getId());
		if(!ingredientAllergies.isEmpty()){
			for (IngredientAllergy ingredientAllergy : ingredientAllergies) {
				kieSession.insert(ingredientAllergy);
			}
		}
		
		kieSession.getAgenda().getAgendaGroup("check-allergies").setFocus();
		kieSession.fireAllRules();
		
		Collection<String> allergiesFound = (Collection<String>) kieSession.getObjects(new ClassObjectFilter(String.class));
		Iterator<String> iterMed = allergiesFound.iterator();
		
		while (iterMed.hasNext()) {
			allergies += iterMed.next() + ", ";
		}
		
		if(!allergies.equals("Pacijent je alergičan na: ")){
			allergies = allergies.substring(0, allergies.length()-2);
			allergies += "!";
		}else{
			ArrayList<Symptom> symptoms = (ArrayList<Symptom>) record.getIllness().getSymptoms();
			if(!symptoms.isEmpty()){
				for (Symptom symptom : symptoms) {
					if(symptom.getTemperature() != null){
						if(symptom.getTemperature() >= 38 && symptom.getTemperature() < 40){
							symptom.setTerm(Symptom.Term.TEMP_OVER_38);
						}
						if(symptom.getTemperature() >= 40 && symptom.getTemperature() <= 41){
							symptom.setTerm(Symptom.Term.TEMP_BETWEEN_40_AND_41);
						}
					}
				}
			}
			
			Diagnose diagnose = new Diagnose();
			diagnose.setDate(new Date());
			diagnose.setIllnessName(record.getIllness().getName());
			diagnose.setPatientId(record.getPatient().getId());
			User doctor = userService.findOne(record.getDoctor().getId());
			diagnose.setDoctorId(record.getDoctor().getId());
			diagnose.setDoctorName(doctor.getName() + " " + doctor.getSurname());
			Patient patient = patientService.findOne(record.getPatient().getId());
			diagnose.setPatientName(patient.getName() + " " + patient.getSurname());
			
			diagnoseService.save(diagnose);
			
			if(!symptoms.isEmpty()){
				for (Symptom symptom : symptoms) {
					System.out.println("Na kraju imam ove simptome! " + symptom.getTerm().toString());
					DiagnoseSymptom ds = new DiagnoseSymptom();
					ds.setDiagnoseId(diagnose.getId());
					ds.setSymptomTerm(symptom.getTerm().toString());
					ds.setDate(diagnose.getDate());
					ds.setPatientId(diagnose.getPatientId());
					ds = diagnoseSymptomService.save(ds);
				}
			}
			
			if(!record.getMedicaments().isEmpty()){
				for (Medicament medicament : record.getMedicaments()) {
					DiagnoseMedicament dm = new DiagnoseMedicament();
					dm.setDiagnoseId(diagnose.getId());
					dm.setMedicamentId(medicament.getId());
					dm.setMedicamentName(medicament.getName());
					dm.setMedicamentCategory(medicament.getCategory().toString());
					dm.setPatientId(record.getPatient().getId());
					dm.setDate(diagnose.getDate());
					dm.setDoctorId(diagnose.getDoctorId());
					dm.setIllnessName(diagnose.getIllnessName());
					dm = diagnoseMedicamentService.save(dm);
				}
			}
			
		}
		releaseObjectsFromSession(kieSession);
		return allergies;
		
	}
	
	public void insertDiagnoseInSession(KieSession kieSession, Long patientId){
		ArrayList<Diagnose> diagnoses = (ArrayList<Diagnose>) diagnoseService.findByPatientId(patientId);
		if(!diagnoses.isEmpty()){
			for (Diagnose diagnose : diagnoses) {
				kieSession.insert(diagnose);
				ArrayList<DiagnoseMedicament> diagnoseMedicaments = (ArrayList<DiagnoseMedicament>) diagnoseMedicamentService.findByDiagnoseId(diagnose.getId());
				if(!diagnoseMedicaments.isEmpty()){
					for (DiagnoseMedicament diagnoseMedicament : diagnoseMedicaments) {
						if(diagnoseMedicament.getMedicamentCategory() != null && diagnoseMedicament.getMedicamentCategory().equals("ANTIBIOTICS")){
							kieSession.insert(diagnoseMedicament);
						}
					}
				}
				
				ArrayList<DiagnoseSymptom> diagnoseSymptoms = (ArrayList<DiagnoseSymptom>) diagnoseSymptomService.findByDiagnoseId(diagnose.getId());
				if(!diagnoseSymptoms.isEmpty()){
					for (DiagnoseSymptom diagnoseSymptom : diagnoseSymptoms) {
						if(diagnoseSymptom.getSymptomTerm().equals("TEMP_OVER_38") || diagnoseSymptom.getSymptomTerm().equals("TEMP_BETWEEN_40_AND_41") ||
								diagnoseSymptom.getSymptomTerm().equals("HIGH_PRESSURE")){
							kieSession.insert(diagnoseSymptom);
						}
					}
				}
			}
		}
	}
	
	Comparator<Illness> symptomsFoundComparator = new Comparator<Illness>() 
	{
		@Override
		public int compare(Illness i1, Illness i2)
		{
			return Integer.compare(i2.getSymptomsFound(),  i1.getSymptomsFound());
		}
	};
	
	Comparator<Symptom> symptomsSpecificComparator = new Comparator<Symptom>() 
	{
		@Override
		public int compare(Symptom s1, Symptom s2)
		{
			return Boolean.compare(s2.getIsSpecific(), s1.getIsSpecific());
		}
	};
	
}
