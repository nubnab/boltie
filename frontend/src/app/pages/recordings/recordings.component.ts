import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {MatCard, MatCardMdImage, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {Router} from '@angular/router';

@Component({
  selector: 'app-recordings',
  imports: [
    FormsModule,
    MatCard,
    MatCardMdImage,
    MatCardSubtitle,
    MatCardTitle,
    MatCardTitleGroup,
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger
  ],
  templateUrl: './recordings.component.html',
  styleUrl: './recordings.component.scss'
})
export class RecordingsComponent implements OnInit {
  protected readonly window = window;

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  recordings: RecordingData[] = [];

  ngOnInit() {
    this.requestsService.getRecordings().subscribe(recData => {
      this.recordings = recData.reverse();
    });
  }

  navigateToVideo(username: string, recordingId: number) {
    this.router.navigate([username, "recordings", recordingId]);
  }

  addToWatchLater(username: string, userRecordingTrackingId: number) {
    this.requestsService.addToWatchLater(username, userRecordingTrackingId).subscribe();
  }

}
