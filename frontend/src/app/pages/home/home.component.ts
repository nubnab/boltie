import { Component, inject, OnDestroy, OnInit} from '@angular/core';
import {RequestsService} from '../../services/requests.service';
import {Subject, takeUntil} from 'rxjs';
import {Router} from '@angular/router';
import {
  MatCard,
  MatCardSubtitle,
  MatCardTitle,
  MatCardTitleGroup
} from '@angular/material/card';

export type StreamDetails = {
  username: string;
  title: string;
  streamUrl: string;
}

@Component({
  selector: 'app-home',
  imports: [
    MatCard,
    MatCardTitle,
    MatCardSubtitle,
    MatCardTitleGroup,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})


export class HomeComponent implements OnInit, OnDestroy {
  protected readonly window = window;

  private destroy$= new Subject<void>();
  private requestsService = inject(RequestsService);
  private router = inject(Router);

  streams: StreamDetails[] = [];

  ngOnInit() {
    this.getStreams();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getStreams() {
    return this.requestsService.getLiveStreamInfo()
      .pipe(takeUntil(this.destroy$))
      .subscribe((streams) => {
      this.streams = streams.reverse();
    });
  }

  generateThumbnail(username: string) {
    return `${window.__env.thumbnailUrl}/boltie/${username}_preview/thumb.jpg`;
  }

  navigateToStream(username: string) {
    this.router.navigate([`/${username}`]);
  }

}
