package drools.spring.example.services;

import java.util.Collection;

import drools.spring.example.facts.Diagnose;

public interface DiagnoseService {
	Collection<Diagnose> findAll();
	Collection<Diagnose> findByPatientId(Long patientId);
	Collection<Diagnose> findByDoctorId(Long doctorId);
	Collection<Diagnose> findByIllnessName(String illnessName);
	Diagnose findOne(Long id);
	Diagnose findByPatientIdAndDoctorIdAndIllnessName(Long patientId, Long doctorId, String illnessName);
	void delete(Long id) throws Exception;
	void delete(Collection<Diagnose> diagnoses) throws Exception;
	Diagnose save(Diagnose diagnose) throws Exception;
}
