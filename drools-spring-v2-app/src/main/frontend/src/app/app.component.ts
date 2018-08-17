import { Component } from '@angular/core';
import { WebSocketServiceService } from './services/web-socket-service.service';
declare var $:any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  monitoringMessage: string;
  
  constructor(private webSocketService : WebSocketServiceService){
    let stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame =>{
      stompClient.subscribe('/issue', message =>{
        this.monitoringMessage = message.body;
        $("#monitoring").modal("show");
      });
    });
  }

}
