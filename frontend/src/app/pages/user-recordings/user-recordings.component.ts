import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';

@Component({
  selector: 'app-user-recordings',
  imports: [],
  templateUrl: './user-recordings.component.html',
  styleUrl: './user-recordings.component.scss'
})
export class UserRecordingsComponent implements OnInit {

  username: string = '';
  recordings: RecordingData[] = [];

  private requestsService = inject(RequestsService);

  private route = inject(ActivatedRoute);

  ngOnInit() {
    this.route.params.subscribe(params => {
     this.username = params['username'];
    });

    this.requestsService.getRecordingsByUsername(this.username).subscribe(recData => {
      this.recordings = recData;
    });
  }
}
