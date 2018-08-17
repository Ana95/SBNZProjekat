package drools.spring.example.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Diagnose;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
	Diagnose findByPatientIdAndDoctorIdAndIllnessName(Long patientId, Long doctorId, String illnessName);
	Collection<Diagnose> findByPatientId(Long patientId);
	Collection<Diagnose> findByDoctorId(Long doctorId);
	Collection<Diagnose> findByIllnessName(String illnessName);
}
