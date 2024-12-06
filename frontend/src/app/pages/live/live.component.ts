import {Component, signal} from '@angular/core';
import {MatCard, MatCardHeader, MatCardImage, MatCardTitle} from '@angular/material/card';

export type StreamCardContent = {
  title: string;
  imageUrl: string;
};

@Component({
  selector: 'app-live',
  imports: [
    MatCardTitle,
    MatCardHeader,
    MatCard,
    MatCardImage
  ],
  templateUrl: './live.component.html',
  styleUrl: './live.component.scss'
})
export class LiveComponent {

  cards = signal<StreamCardContent[]>([]);

  images = [
    '/thumb-test/thumb-2160p.jpeg',
    '/thumb-test/thumb-1440p.jpeg',
    '/thumb-test/thumb-1080p.jpeg',
    '/thumb-test/thumb-720p.jpeg',
    '/thumb-test/thumb-480p.jpeg',
    '/thumb-test/thumb-360p.jpeg',
    '/thumb-test/thumb-240p.jpeg',
    '/thumb-test/thumb-144p.jpeg',
  ]

  constructor() {
    const cards: StreamCardContent[] = [];

    for(let i = 0; i < this.images.length; i++) {
      cards.push({
        title: `Card ${i + 1}`,
        imageUrl: this.images[i],
      })
    }

    this.cards.set(cards);

  }

}
