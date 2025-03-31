import {Component, inject, OnInit} from '@angular/core';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {RequestsService} from '../../services/requests.service';
import {MatCard, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {Router} from '@angular/router';

@Component({
  selector: 'app-history',
  imports: [
    MatCardTitleGroup,
    MatCardTitle,
    MatCard,
    MatCardSubtitle
  ],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})

export class HistoryComponent implements OnInit {

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  baseUrl: string = "http://192.168.1.2:9998";
  recordings: RecordingData[] = [];


  ngOnInit() {
    this.requestsService.getWatchHistory().subscribe((recData) => {
      this.recordings = recData;
    })
  }

  navigateToRecording(username: string, userRecordingId: number) {
    this.router.navigate([`/${username}/recordings/${userRecordingId}`]);
  }

}
