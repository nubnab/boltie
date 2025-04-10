import {Component, inject, OnInit} from '@angular/core';
import {MatCard, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {RequestsService} from '../../services/requests.service';
import {Router} from '@angular/router';
import {RecordingData} from '../watch-recording/watch-recording.component';

@Component({
  selector: 'app-watch-later',
  imports: [
    MatCardTitleGroup,
    MatCardTitle,
    MatCardSubtitle,
    MatCard
  ],
  templateUrl: './watch-later.component.html',
  styleUrl: './watch-later.component.scss'
})
export class WatchLaterComponent implements OnInit {
  protected readonly window = window;

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  recordings: RecordingData[] = [];

  ngOnInit() {
    this.requestsService.getWatchLater().subscribe((recData) => {
      this.recordings = recData;
    })
  }

  navigateToRecording(username: string, userRecordingId: number) {
    this.router.navigate([`/${username}/recordings/${userRecordingId}`]);
  }

}
