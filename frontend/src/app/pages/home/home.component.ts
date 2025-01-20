import { Component, inject, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {RequestsService} from '../../services/requests.service';
import {Subject, takeUntil} from 'rxjs';
import {Router} from '@angular/router';

export type StreamDetails = {
  username: string;
  title: string;
  streamUrl: string;
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

      if (streams && streams.length > 0) {
        console.log(streams);
        this.renderGrid(streams);
      }
    });
  }

  generateThumbnail(username: string) {
    return `http://192.168.1.2:20080/boltie/${username}_preview/thumb.jpg`;
  }

  renderGrid(streams: StreamDetails[]) {
    const documentRef = document.getElementById('responsive-grid');

    if(documentRef) {
        documentRef.innerHTML = ``;
        streams.forEach((stream => {
          for (let i = 0; i < 6; i++) {
            this.renderCard(documentRef, stream);
          }
        }));
      }
  }

  renderCard(documentRef: HTMLElement, stream: StreamDetails) {
    const matCard = document.createElement('mat-card');
    const matCardHeader = document.createElement('mat-card-header');
    const matCardTitle = document.createElement('mat-card-title');
    const matCardSubtitle = document.createElement('mat-card-subtitle');
    const thumbnailImgContainer = document.createElement('div');
    const profileImgContainer = document.createElement('div');
    const profileImg = document.createElement('img');
    const thumbnailImg = document.createElement('img');
    const cardContentContainer = document.createElement('div');


    thumbnailImg.src = this.generateThumbnail(stream.username);
    thumbnailImg.style.width = '100%';
    thumbnailImg.style.height = 'auto';
    thumbnailImg.style.display = 'block';
    profileImg.style.objectFit = 'cover';
    profileImg.style.width = '40px';
    profileImgContainer.style.display = 'flex';
    profileImgContainer.style.alignItems = 'center';
    profileImgContainer.style.borderRadius = '100%';
    profileImgContainer.style.overflow = 'hidden';
    profileImgContainer.style.margin = '10px';
    matCard.style.borderRadius = '8px';
    matCard.style.overflow = 'hidden';
    thumbnailImg.style.borderRadius = '8px';
    thumbnailImgContainer.style.overflow = 'hidden';
    profileImg.src = '/default-profile-picture.png';
    matCardTitle.innerHTML = stream.title;
    matCardTitle.style.fontSize = '1.2rem';
    matCardTitle.style.fontWeight = '500';
    matCardSubtitle.innerHTML = stream.username;
    matCardSubtitle.style.fontSize = '0.9rem';
    matCardSubtitle.style.color = '#606060';
    matCardHeader.style.display = 'flex';
    matCardHeader.style.flexDirection = 'column';
    matCardHeader.style.justifyContent = 'center';
    matCard.addEventListener('click', () => {
      this.router.navigate([`/${stream.username}`]);
    })
    cardContentContainer.style.display = 'flex';

    matCard.style.cursor = 'pointer';

    matCard.addEventListener('mouseenter', () => {
      matCard.style.backgroundColor = '#e1e1e2';
    });
    matCard.addEventListener('mouseleave', () => {
      matCard.style.backgroundColor = '#faf9fd';
    });



    profileImgContainer.appendChild(profileImg);
    thumbnailImgContainer.appendChild(thumbnailImg);


    matCardHeader.appendChild(matCardTitle);
    matCardHeader.appendChild(matCardSubtitle);

    matCard.appendChild(thumbnailImgContainer);
    matCard.appendChild(profileImgContainer);

    cardContentContainer.appendChild(profileImgContainer);
    cardContentContainer.appendChild(matCardHeader);

    matCard.appendChild(cardContentContainer);

    documentRef.appendChild(matCard);
  }


}
