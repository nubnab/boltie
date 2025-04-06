import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {MatCard, MatCardMdImage, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';

@Component({
  selector: 'app-user-recordings',
  imports: [
    MatCard,
    MatCardMdImage,
    MatCardTitleGroup,
    MatCardTitle,
    MatCardSubtitle,
    MatIcon,
    MatIconButton,
    MatMenuTrigger,
    MatMenu,
    MatMenuItem,
  ],
  templateUrl: './user-recordings.component.html',
  styleUrl: './user-recordings.component.scss'
})
export class UserRecordingsComponent implements OnInit {

  username: string = '';
  recordings: RecordingData[] = [];
  baseUrl: string = "http://192.168.1.2:9998";

  private requestsService = inject(RequestsService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  ngOnInit() {
    this.route.params.subscribe(params => {
     this.username = params['username'];
    });

    this.requestsService.getRecordingsByUsername(this.username).subscribe(recData => {
      this.recordings = recData;
    });
  }

  navigateToVideo(username: string, recordingId: number) {
    this.router.navigate([username, "recordings", recordingId]);
  }

  addToWatchLater(username: string, userRecordingTrackingId: number) {
    this.requestsService.addToWatchLater(username, userRecordingTrackingId).subscribe();
  }

}
