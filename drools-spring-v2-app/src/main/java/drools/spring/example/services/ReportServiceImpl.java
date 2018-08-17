package drools.spring.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.facts.Diagnose;
import drools.spring.example.facts.DiagnoseMedicament;
import drools.spring.example.facts.Report;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;

	@Autowired
	private PatientService patientService;
	
	@Override
	public ArrayList<Report> getReports(HttpServletRequest request) {
		// TODO Auto-generated method stub
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		addDiagnosesToSession(kieSession);
		kieSession.getAgenda().getAgendaGroup("reports").setFocus();
		kieSession.fireAllRules();
		
		Collection<Report> reports = (Collection<Report>) kieSession.getObjects(new ClassObjectFilter(Report.class));
		Iterator<Report> iter = reports.iterator();
		
		ArrayList<Report> new_report = new ArrayList<Report>();
		
		while (iter.hasNext()) {
			Report report = iter.next();
			boolean flag = true;
			if(!new_report.isEmpty()){
				for (Report report2 : new_report) {
					if(report2.getCategory().equals(report.getCategory()) && report2.getPatient().getId() == report.getPatient().getId() 
							&& report2.getHelper().equals(report.getHelper())){
						flag = false;
						break;
					}
				}
			}
			if(flag){
				new_report.add(report);
			}	
		}
		
		if(!new_report.isEmpty()){
			for (Report report : new_report) {
				report.setPatient(patientService.findOne(report.getPatient().getId()));
			}
		}
		
		kieSession.getObjects();
		for( Object object: kieSession.getObjects() ){
			kieSession.delete( kieSession.getFactHandle( object ) );
	    }
		return new_report;
	}

	@Override
	public void addDiagnosesToSession(KieSession kieSession) {
		// TODO Auto-generated method stub
		ArrayList<Diagnose> diagnoses = (ArrayList<Diagnose>) diagnoseService.findAll();
		System.out.println("funkcija za ubacivanje dijagnoze u sesiju");
		if(!diagnoses.isEmpty()){
			for (Diagnose diagnose : diagnoses) {
				kieSession.insert(diagnose);
				System.out.println("Ubacujemo u sesiju uspostavljenu dijagnozu za bolest " + diagnose.getIllnessName());
				ArrayList<DiagnoseMedicament> diagnoseMedicaments = (ArrayList<DiagnoseMedicament>) diagnoseMedicamentService.findByDiagnoseId(diagnose.getId());
				if(!diagnoseMedicaments.isEmpty()){
					for (DiagnoseMedicament diagnoseMedicament : diagnoseMedicaments) {
						if(diagnoseMedicament.getMedicamentCategory() != null){
							kieSession.insert(diagnoseMedicament);
							System.out.println("Ubacujemo u sesiju lijek " + diagnoseMedicament.getMedicamentName());
						}
					}
				}
			}
		}
	}

}
