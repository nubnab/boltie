import {AfterViewChecked, Component, computed, ElementRef, inject, OnDestroy, OnInit, ViewChild,} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {FormsModule} from '@angular/forms';
import {MatButton, MatMiniFabButton} from '@angular/material/button';
import {MatFormField, MatInput} from '@angular/material/input';
import {AuthService} from '../../services/auth.service';
import OvenPlayer from 'ovenplayer';
import {RxstompService} from '../../services/rxstomp.service';
import {MatIcon} from '@angular/material/icon';
import {MenuItem} from '../../layout/navigation/custom-sidenav/custom-sidenav.component';
import {Subscription} from 'rxjs';

export type StreamTitle = {
  title: string;
}

export type StreamData = {
  id: number;
  username: string;
  title: string;
  streamUrl: string;
}

export type MessageDto = {
  senderName: string;
  content: string;
  sentAt: string;
}

export type SimpleMessageDto = {
  content: string;
}

@Component({
  selector: 'app-stream',
  imports: [
    FormsModule,
    MatButton,
    MatInput,
    MatFormField,
    MatIcon,
    MatMiniFabButton,
  ],
  templateUrl: './stream.component.html',
  styleUrl: './stream.component.scss'
})

export class StreamComponent implements OnInit, AfterViewChecked, OnDestroy {
  id: number = 0;
  username: string = '';
  isEditMode: boolean = false;

  streamTitle: string = '';
  streamLink: string = '';
  tempTitle: string = '';

  sendIcon: MenuItem = {
    icon: "chevron_right",
    label: "eh",
    route: ''
  }

  testMessage: SimpleMessageDto = { content: '' }
  messages: MessageDto[] = [];

  private requestsService = inject(RequestsService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private rxStompService = inject(RxstompService);

  private routeParamsSubscription!: Subscription;
  private stompMessageSubscription!: Subscription;
  private player: any;


  ngOnInit() {

    this.routeParamsSubscription = this.route.params.subscribe(params => {
      this.username = params['username'];
    });

    this.requestsService.getStreamByUsername(this.username).subscribe({
      next: (data: StreamData) => {
        this.id = data.id;
        this.streamTitle = data.title;
        this.streamLink = data.streamUrl;

        this.initPlayer();

        this.requestsService.getRecentMessages(data.id).subscribe(messages => {
          messages.forEach(recentMessage => {
            this.messages.unshift(this.utcToLocal(recentMessage));
          })
        });

        this.stompMessageSubscription =
          this.rxStompService.watchMessages(data.id).subscribe((message) => {
          const receivedMessage: MessageDto = JSON.parse(message.body);
          this.messages.push(this.utcToLocal(receivedMessage));
        });
      },
      error: err => {console.log(err);}
    });
  }

  ngAfterViewChecked() {
    this.autoScroll();
  }

  ngOnDestroy() {
    if (this.routeParamsSubscription) {
      this.routeParamsSubscription.unsubscribe();
    }
    if (this.stompMessageSubscription) {
      this.stompMessageSubscription.unsubscribe();
    }
    if (this.player) {
      this.player.remove();
    }
    this.rxStompService.disconnect();
  }

  userIsStreamOwnerSignal = computed(() =>
    this.username === this.authService.currentUserSignal());


  editTitle() {
    this.tempTitle = this.streamTitle;
    this.isEditMode = true;
  }

  saveTitle() {
    const newTitle: StreamTitle = {
      title: this.streamTitle,
    }
    this.requestsService.editStreamTitle(newTitle).subscribe(res => {
      console.log(res); // return new title and set it
    })

    this.isEditMode = false;
  }

  cancelEdit() {
    this.streamTitle = this.tempTitle;
    this.isEditMode = false;
  }

  sendMessage() {
    if(this.testMessage.content.trim()){
      this.rxStompService.sendMessage('/app/chat/send/' + this.id, this.testMessage);
      this.testMessage = { content: '' };
    }
  }

  private utcToLocal(message: MessageDto): MessageDto {
    const utcTime = new Date(message.sentAt);
    message.sentAt = utcTime.toLocaleTimeString('en-GB', {
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
    });

    return message;
  }

  private autoScroll() {
    const chatContainer = document.getElementById('chatContainer');

    if(chatContainer) {
      chatContainer.scrollTop = chatContainer.scrollHeight;
    }
  }

  private initPlayer() {
    this.player = OvenPlayer.create('player_id', {
      sources: [{
        label: 'webrtc',
        type: 'webrtc',
        file: this.streamLink,
      }]
    });
  }

  //TODO: Stomp disconnect on destroy
  //TODO: forbid empty messages

}
