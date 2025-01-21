import { Component, inject, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
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
  streams: StreamDetails[] = [];
  private destroy$= new Subject<void>();

  private authService = inject(AuthService);
  private requestsService = inject(RequestsService);
  private router = inject(Router);

  loginState = this.authService.loginStateSignal;

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
      this.streams = streams;
    });
  }

  generateThumbnail(username: string) {
    return `http://192.168.1.2:20080/boltie/${username}_preview/thumb.jpg`;
  }

  navigateToStream(username: string) {
    this.router.navigate([`/${username}`]);
  }

}
