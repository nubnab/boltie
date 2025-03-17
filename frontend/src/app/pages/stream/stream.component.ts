import {
  Component, computed,
  inject,
  OnInit,
} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {FormsModule} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput} from '@angular/material/input';
import {AuthService} from '../../services/auth.service';
import OvenPlayer from 'ovenplayer';

export type StreamTitle = {
  title: string;
}

export type StreamData = {
  id?: number;
  username?: string;
  title?: string;
  streamUrl?: string;
}

export type MessageDto = {
  sender: string;
  content: string;
}

@Component({
  selector: 'app-stream',
  imports: [
    FormsModule,
    MatButton,
    MatInput,
    MatFormField
  ],
  templateUrl: './stream.component.html',
  styleUrl: './stream.component.scss'
})

export class StreamComponent implements OnInit {

  id: number = 0;
  username: string = '';
  streamTitle: string = '';
  streamLink: string = '';
  tempTitle: string = '';
  isEditMode: boolean = false;

  private requestsService = inject(RequestsService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);


  async ngOnInit() {

    this.route.params.subscribe(params => {
      this.username = params['username'];
    });

    this.requestsService.getStreamByUsername(this.username).subscribe({
      next: (data: StreamData) => {
        if(data.id != null) {
          this.id = data.id;
        }
        if (data.username != null) {
          this.username = data.username;
        }
        if (data.title != null) {
          this.streamTitle = data.title;
        }
        if (data.streamUrl != null) {
          this.streamLink = data.streamUrl;
        }
      },
      error: err => {console.log(err);},
      complete: () => {
        const player = OvenPlayer.create('player_id', {
          sources: [{
            label: 'webrtc',
            type: 'webrtc',
            file: this.streamLink,
          }]
        });
      }
    });
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

;

}
