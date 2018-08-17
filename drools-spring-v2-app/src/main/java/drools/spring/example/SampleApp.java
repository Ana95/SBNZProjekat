package drools.spring.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import antlr.debug.Event;
import drools.spring.example.controllers.WebSocketController;
import drools.spring.example.events.HeartBeatEvent;
import drools.spring.example.events.OxygenLevelEvent;
import drools.spring.example.events.UrinalSumEvent;
import drools.spring.example.facts.Diagnose;
import drools.spring.example.facts.MonitoringIssue;


@SpringBootApplication
public class SampleApp {
	
	private static Logger log = LoggerFactory.getLogger(SampleApp.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(SampleApp.class, args); 

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        StringBuilder sb = new StringBuilder("Application beans:\n");
        for (String beanName : beanNames) {
            sb.append(beanName + "\n");
        }
        log.info(sb.toString());
        log.info("Application started");
        
        KieSession kieSession = realtimeSession();
        
        WebSocketController controller = ctx.getBean(WebSocketController.class);
        
        Thread.sleep(1000*60);
        while(true){
        	runRealTimeHeartBeat(kieSession, controller);
        	runRealTimeDialysis(kieSession, controller);
        	runRealTimeHeartBeat(kieSession, controller);
        	runRealTimeDialysis(kieSession, controller);
        	runRealTimeOxygen(kieSession, controller);
        }
	}
	
	@Bean
    public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2","drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		return kContainer;
    }
	
	public static KieSession realtimeSession(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2","drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
		
		KieBaseConfiguration kconf = ks.newKieBaseConfiguration();
		kconf.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kContainer.newKieBase(kconf);
		
		KieSessionConfiguration kconfig1 = ks.newKieSessionConfiguration();
		kconfig1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession ksession = kieBase.newKieSession(kconfig1, null);
		
		return ksession;
	}
	
	public static void runRealTimeHeartBeat(final KieSession realtimeSession, WebSocketController controller) throws InterruptedException{
		Thread.sleep(1000*45);
		
		Thread t = new Thread(){
			@Override
			public void run(){
				for(int index = 0; index < 30; index++){
					HeartBeatEvent event = new HeartBeatEvent();
					event.setPatientId(1L);
					realtimeSession.insert(event);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						//do nothing
					}
				}
			}
			
		};
		t.setDaemon(true);
		t.start();
		try{
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			//do nothing
		}
		realtimeSession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
		realtimeSession.fireUntilHalt();
		Collection<MonitoringIssue> newEvent = (Collection<MonitoringIssue>) realtimeSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
		
		Iterator<MonitoringIssue> iter = newEvent.iterator();
		while(iter.hasNext()){
			MonitoringIssue mi = iter.next();
			if(mi.getIssue().equals(MonitoringIssue.ISSUE.ACCELERATED_HEARTBEAT)){
				String message = "Pacijent: Milan Radović [ 12568 ] ima ubrzani srčani ritam"
						+ "(u poslednjih 10 sekundi zabilježeno više od 25 otkucaja srca)!";
				System.out.println(message);
				controller.sendMessage(message);
				break;
			}
		}
	}
	
	private static void runRealTimeDialysis(final KieSession ksession, WebSocketController controller) throws InterruptedException{
		Thread.sleep(1000*45);
		
		Thread t = new Thread(){
			@Override
			public void run(){
				Diagnose diagnose = new Diagnose();
				diagnose.setIllnessName("Hronicna bubrezna bolest");
				diagnose.setPatientId(1L);
				ksession.insert(diagnose);
				
				for(int index = 0; index < 15; index++){
					HeartBeatEvent event = new HeartBeatEvent();
					event.setPatientId(1L);
					ksession.insert(event);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						//do nothing
					}
				}
				
				for(int i = 0; i < 5; i++){
					UrinalSumEvent event = new UrinalSumEvent();
					event.setPatientId(1L);
					event.setAmount(10);
					ksession.insert(event);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						//do nothing
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			//do nothing
		}
		ksession.getAgenda().getAgendaGroup("dialysis-event").setFocus();
		ksession.fireUntilHalt();
		Collection<MonitoringIssue> newEvents = (Collection<MonitoringIssue>) ksession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
		
		Iterator<MonitoringIssue> iter = newEvents.iterator();
		while (iter.hasNext()) {
			MonitoringIssue mi = iter.next();
			if(mi.getIssue().equals(MonitoringIssue.ISSUE.URGENT_DIALYSIS)){
				String message = "Pacijent: Anastasija Pantić [ 78098 ] mora hitno na dijalizu!";
				System.out.println(message);
				controller.sendMessage(message);
				break;
			}
		}
	}
	
	public static void runRealTimeOxygen(final KieSession realtimeSession, WebSocketController controller){
		Thread t = new Thread(){
			@Override
			public void run(){
				for(int index = 0; index < 10; index++){
					OxygenLevelEvent event = new OxygenLevelEvent();
					event.setPatientId(1L);
					event.setLevel(50);
					realtimeSession.insert(event);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						//do nothing
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			Thread.sleep(1000*60*15);
		} catch (InterruptedException e) {
			//do nothing
		}
		realtimeSession.getAgenda().getAgendaGroup("oxygen-event").setFocus();
		realtimeSession.fireUntilHalt();
		Collection<MonitoringIssue> newEvents = (Collection<MonitoringIssue>) realtimeSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
		
		Iterator<MonitoringIssue> iter = newEvents.iterator();
		while(iter.hasNext()){
			MonitoringIssue mi = iter.next();
			if(mi.getIssue().equals(MonitoringIssue.ISSUE.OXYGEN_ISSUE)){
				String message ="Pacijent: Marko Ristić [ 25098 ] ima problem sa kiseonikom!";
				System.out.println(message);
				controller.sendMessage(message);
				break;
			}	
		}
	}
	
}
