import {Component, inject} from '@angular/core';
import {MatCard, MatCardSubtitle, MatCardTitle, MatCardTitleGroup} from '@angular/material/card';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {RecordingData} from '../watch-recording/watch-recording.component';
import {RequestsService} from '../../services/requests.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-categories',
  imports: [
    MatCard,
    MatCardTitle,
  ],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})
export class CategoriesComponent {
  username: string = '';
  recordings: RecordingData[] = [];

  private requestsService = inject(RequestsService);
  private router = inject(Router);


}
