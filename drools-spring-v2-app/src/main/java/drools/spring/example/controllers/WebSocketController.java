package drools.spring.example.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	public void sendMessage(String message){
		this.template.convertAndSend("/issue", new SimpleDateFormat("HH:mm").format(new Date()) + " - " + message);
	}

}