package drools.spring.example.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.runtime.KieSession;

import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Record;
import drools.spring.example.facts.Symptom;

public interface IllnessService {
	Illness save(Illness illness) throws Exception;
	Illness findOne(Long id);
	Illness findByName(String name);
	void delete(Long id) throws Exception;
	Collection<Illness> findAll();
	ArrayList<Illness> getOneIllness(ArrayList<Symptom> symptoms, Long patientId, HttpServletRequest request);
	ArrayList<Illness> getAllIllness(ArrayList<Symptom> symptoms, Long patientId, HttpServletRequest request);
	void addSymptomsAndIllnesses(KieSession kieSession, ArrayList<Symptom> symptoms, Long patientId, ArrayList<Illness> illnesses);
	ArrayList<Symptom> getIllnessSymptoms(Illness illness, HttpServletRequest request);
	ArrayList<Symptom> handleSymptoms(ArrayList<Symptom> symptoms);
	void releaseObjectsFromSession(KieSession kieSession);
	String setDiagnose(Record record, HttpServletRequest request) throws Exception;
	void insertDiagnoseInSession(KieSession kieSession, Long patientId);
}
