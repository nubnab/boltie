import {AfterViewInit, Component, ElementRef, inject, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {RequestsService} from '../../services/requests.service';
import {filter, Subject, takeUntil} from 'rxjs';
import {NavigationEnd, Router} from '@angular/router';

export type StreamDetails = {
  username: string;
  title: string;
  streamLink: string;
}

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})


export class HomeComponent implements OnInit, OnDestroy {
  private destroy$= new Subject<void>();

  private authService = inject(AuthService);
  private requestsService = inject(RequestsService);

  loginState = this.authService.loginStateSignal;

  ngOnInit() {
    this.getStreams();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getStreams() {
    return this.requestsService.getStreams()
      .pipe(takeUntil(this.destroy$))
      .subscribe((streams) => {
      const thumbnails: string[] = [];

      if (streams && streams.length > 0) {

        for (let i = 0; i < streams.length; i++) {
          const thumbnailUrl = this.getThumbnails(streams[i].username);
          thumbnails.push(thumbnailUrl);
        }
        this.renderGrid(thumbnails);
      }
    });
  }

  getThumbnails(username: string) {
    return `http://192.168.1.2:20080/boltie/${username}_preview/thumb.jpg`;
  }

  renderGrid(thumbnails: string[]) {
    const documentRef = document.getElementById('grid-container');

    if(documentRef) {
        documentRef.innerHTML = ``;
        thumbnails.forEach((thumbnail => {
          const imgElement = document.createElement('img');
          imgElement.src = thumbnail;
          //imgElement.classList.add('img-fluid');
          imgElement.style.width = '100%';
          documentRef.appendChild(imgElement);
        }));
      }}

}
