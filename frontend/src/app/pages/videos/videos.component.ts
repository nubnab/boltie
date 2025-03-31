import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RequestsService} from '../../services/requests.service';

@Component({
  selector: 'app-videos',
  imports: [
    FormsModule
  ],
  templateUrl: './videos.component.html',
  styleUrl: './videos.component.scss'
})
export class VideosComponent {

  private requestsService = inject(RequestsService);


  getHistory() {
    this.requestsService.getWatchHistory().subscribe(history => {
      console.log(history);
    })
  }

}
