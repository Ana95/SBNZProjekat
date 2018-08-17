package drools.spring.example.facts;

public class MonitoringIssue {
	
	public enum ISSUE{
		OXYGEN_ISSUE,
		ACCELERATED_HEARTBEAT,
		URGENT_DIALYSIS
	};
	
	private Long patientId;
	
	private ISSUE issue;

	public MonitoringIssue() {
		super();
	}

	public MonitoringIssue(Long patientId, ISSUE issue) {
		super();
		this.patientId = patientId;
		this.issue = issue;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public ISSUE getIssue() {
		return issue;
	}

	public void setIssue(ISSUE issue) {
		this.issue = issue;
	}

	@Override
	public String toString() {
		return "MonitoringIssue [patientId=" + patientId + ", issue=" + issue + "]";
	}

}