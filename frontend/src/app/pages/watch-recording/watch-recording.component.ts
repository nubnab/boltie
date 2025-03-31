import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import OvenPlayer from 'ovenplayer';
import Hls from 'hls.js'

window.Hls = Hls;

export type RecordingData = {
  userRecordingTrackingId: number,
  owner: string,
  title: string,
  folderName: string,
}

@Component({
  selector: 'app-watch-user-recordings',
  imports: [],
  templateUrl: './watch-recording.component.html',
  styleUrl: './watch-recording.component.scss'
})
export class WatchRecordingComponent implements OnInit {

  username: string = '';
  recordingId: number = 0;

  private route = inject(ActivatedRoute);
  private requestsService = inject(RequestsService);
  private router = inject(Router);

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.username = params['username'];
      this.recordingId = params['recordingId'];
    });

    if (this.recordingId <= 0) {
      this.router.navigate([`/${this.username}/recordings`]);
    }

    this.requestsService.getRecordingByUsernameAndId(this.username, this.recordingId).subscribe(res =>{
      const player = OvenPlayer.create('player_id', {
        sources: [{
          label: 'llhls-user-recordings',
          type: 'llhls',
          file: `${window.__env.cdnUrl}/${this.username}/${res.folderName}/llhls.m3u8`,
        }]
      });
    })
  }
}
