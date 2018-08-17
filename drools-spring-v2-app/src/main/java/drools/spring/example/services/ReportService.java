package drools.spring.example.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.runtime.KieSession;

import drools.spring.example.facts.Report;

public interface ReportService {
	ArrayList<Report> getReports(HttpServletRequest request);
	void addDiagnosesToSession(KieSession kieSession);
}
