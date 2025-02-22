import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {MatCard, MatCardMdImage, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';

@Component({
  selector: 'app-user-recordings',
  imports: [
    MatCard,
    MatCardMdImage,
    MatCardTitleGroup,
    MatCardTitle,
    MatCardSubtitle,
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

}
