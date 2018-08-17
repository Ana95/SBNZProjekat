package drools.spring.example.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("10s")
public class HeartBeatEvent {
	
	private Date executionTime;
	
	private Long patientId;

	public HeartBeatEvent() {
		super();
		this.executionTime = new Date();
	}

	public HeartBeatEvent(Long patientId) {
		super();
		this.patientId = patientId;
		this.executionTime = new Date();
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	
}