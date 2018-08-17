package drools.spring.example.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.facts.Report;
import drools.spring.example.services.ReportService;

@RestController
@RequestMapping( value = "/api" )
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@RequestMapping(
		value = "/reports",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Report>> getReports(HttpServletRequest request){
		ArrayList<Report> reports = reportService.getReports(request);
		return new ResponseEntity<ArrayList<Report>>(reports, HttpStatus.OK);
	}
	
}
