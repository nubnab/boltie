import {Component, inject} from '@angular/core';
import {RequestsService} from '../../services/requests.service';

@Component({
  selector: 'app-videos',
  imports: [],
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.scss'
})
export class VideosComponent {

  private requestsService = inject(RequestsService);

  test() {
    this.requestsService.getTest().subscribe(res => {
      console.log(res);
    })
  }


}
