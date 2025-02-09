import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import OvenPlayer from 'ovenplayer';

@Component({
  selector: 'app-recording',
  imports: [],
  templateUrl: './recording.component.html',
  styleUrl: './recording.component.scss'
})
export class RecordingComponent implements OnInit {

  username: string = '';

  private requestsService = inject(RequestsService);


  private route = inject(ActivatedRoute);

  ngOnInit() {
    this.route.params.subscribe(params => {
     this.username = params['username'];
    });

    this.requestsService.getRecordingsByUsername(this.username).subscribe(response => {
      console.log(response);
    });


  }

}
