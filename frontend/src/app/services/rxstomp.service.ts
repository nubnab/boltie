import {inject, Injectable} from '@angular/core';
import {RxStomp} from '@stomp/rx-stomp';
import SockJS from 'sockjs-client/dist/sockjs';
import {MessageDto} from '../pages/stream/stream.component';
import {RequestsService} from './requests.service';

@Injectable({
  providedIn: 'root'
})
export class RxstompService {

  private rxStomp: RxStomp = new RxStomp();
  private requestsService = inject(RequestsService);
  token = '';

  constructor() {
    this.initSock();
  }

  watchMessages() {
    return this.rxStomp.watch("/topic/chat/1");
  }

  sendMessage(destination: string, message: MessageDto) {
    this.rxStomp.publish({ destination, body: JSON.stringify(message)});
  }

  initSock() {
    this.rxStomp.configure({
      webSocketFactory: () => new SockJS('http://localhost:8082/ws'),
      connectHeaders: {
        Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      },
      reconnectDelay: 15000,
      debug: (msg: string) => console.log(`STOMP: ${msg}`),
    });
    this.rxStomp.activate();
  }
}
