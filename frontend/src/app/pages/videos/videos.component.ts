import {Component, inject} from '@angular/core';
import {RequestsService} from '../../services/requests.service';
import {Router} from '@angular/router';
import {MessageDto} from '../stream/stream.component';
import {RxstompService} from '../../services/rxstomp.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-videos',
  imports: [
    FormsModule
  ],
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.scss'
})
export class VideosComponent {

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  test() {
    this.requestsService.getTest().subscribe(res => {
      console.log(res);
    })
  }

  testVideos() {
    this.router.navigate(['/mirela']);
  }

  testVideosNabs() {
    this.router.navigate(['/nabs']);
  }

  message: MessageDto = {sender: 'Nabs', content: ''};
  messages: MessageDto[] = [];

  private rxStompService = inject(RxstompService);

  ngOnInit() {
    this.rxStompService.watchMessages().subscribe((message) => {
      console.log(JSON.parse(message.body).message);
      this.messages.push(JSON.parse(message.body));
    });
  }

  sendMessage() {
    this.rxStompService.sendMessage('/app/chat/send/1', this.message);
    this.message = {sender: 'Nabs', content: ''};
  }

}
