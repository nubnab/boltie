import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';

@Component({
  selector: 'app-stream',
  imports: [],
  templateUrl: './stream.component.html',
  styleUrl: './stream.component.scss'
})

export class StreamComponent implements OnInit {

  private requestsService = inject(RequestsService);

  username: string = '';

  constructor(private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.username = params['username'];
    });

    this.requestsService.getStreamByUsername(this.username).subscribe({
      next: data => {
        console.log(this.username);
      }
    })
  }
}
