import {Injectable} from '@angular/core';
import {RxStomp} from '@stomp/rx-stomp';
import SockJS from 'sockjs-client/dist/sockjs';
import {SimpleMessageDto} from '../pages/stream/stream.component';

const env = window.__env;

@Injectable({
  providedIn: 'root'
})
export class RxstompService {
  private rxStomp: RxStomp = new RxStomp();
  private wsUrl = env.wsChatUrl;

  token = '';

  constructor() {
    this.initSock();
  }

  watchMessages(chatId: number) {
    return this.rxStomp.watch("/topic/chat/" + chatId);
  }

  sendMessage(destination: string, message: SimpleMessageDto) {
    this.rxStomp.publish({ destination, body: JSON.stringify(message)});
  }

  initSock() {
    this.rxStomp.configure({
      webSocketFactory: () => new SockJS(`${this.wsUrl}/ws`),
      connectHeaders: {
        Authorization: `Bearer ${localStorage.getItem('auth_token')}`,
      },
      reconnectDelay: 200,
      debug: (msg: string) => console.log(`STOMP: ${msg}`),
    });
    this.rxStomp.activate();
  }

  disconnect() {
    this.rxStomp.deactivate().then(r => console.log(r));
  }
}
