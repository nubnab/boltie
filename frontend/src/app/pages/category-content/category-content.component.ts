import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {MatCard, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {StreamDetails} from '../home/home.component';
import {MatTab, MatTabGroup} from '@angular/material/tabs';

@Component({
  selector: 'app-category-content',
  imports: [
    MatCard,
    MatCardSubtitle,
    MatCardTitle,
    MatCardTitleGroup,
    MatTabGroup,
    MatTab
  ],
  templateUrl: './category-content.component.html',
  styleUrl: './category-content.component.scss'
})
export class CategoryContentComponent implements OnInit {
  protected readonly window = window;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private requestsService = inject(RequestsService);

  categoryUrl: string = '';
  streams: StreamDetails[] = [];
  recordings: RecordingData[] = [];

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.categoryUrl = params['categoryUrl'];
    });

    this.requestsService.getLiveStreamsFromCategory(this.categoryUrl).subscribe(res => {
      this.streams = res.reverse();
    })

    this.requestsService.getCategoryContent(this.categoryUrl).subscribe(res => {
      this.recordings = res.reverse();
    })
  }

  navigateToRecording(username: string, userRecordingId: number) {
    this.router.navigate([`/${username}/recordings/${userRecordingId}`]);
  }

  navigateToStream(username: string) {
    this.router.navigate([`/${username}`]);
  }

  generateThumbnail(username: string) {
    return `${window.__env.thumbnailUrl}/boltie/${username}_preview/thumb.jpg`;
  }

}
