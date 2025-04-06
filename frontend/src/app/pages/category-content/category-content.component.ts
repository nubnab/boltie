import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RequestsService} from '../../services/requests.service';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {MatCard, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';

@Component({
  selector: 'app-category-content',
  imports: [
    MatCard,
    MatCardSubtitle,
    MatCardTitle,
    MatCardTitleGroup
  ],
  templateUrl: './category-content.component.html',
  styleUrl: './category-content.component.scss'
})
export class CategoryContentComponent implements OnInit {

  categoryUrl: string = '';
  recordings: RecordingData[] = [];

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private requestsService = inject(RequestsService);

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.categoryUrl = params['categoryUrl'];
    });

    this.requestsService.getCategoryContent(this.categoryUrl).subscribe(res => {
      this.recordings = res;
    })
  }

  navigateToRecording(username: string, userRecordingId: number) {
    this.router.navigate([`/${username}/recordings/${userRecordingId}`]);
  }

  protected readonly window = window;
}
