import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RequestsService} from '../../services/requests.service';
import {AuthService} from '../../services/auth.service';

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
  private authService = inject(AuthService);


  getHistory() {
    console.log(this.authService.getCurrentUserRoles());
    console.log(this.authService.isAdmin());
  }

}
