import {Component, inject} from '@angular/core';
import {RequestsService} from '../../services/requests.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-videos',
  imports: [],
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.scss'
})
export class VideosComponent {

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  test() {
    this.requestsService.getTest().subscribe(res => {
      console.log(res);
    })
  }

  testVideos() {
    this.router.navigate(['/mirela']);
  }

  testVideosNabs() {
    this.router.navigate(['/nabs']);
  }



}
