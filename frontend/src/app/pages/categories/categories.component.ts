import {Component, inject} from '@angular/core';
import {RequestsService} from '../../services/requests.service';

@Component({
  selector: 'app-categories',
  imports: [],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})
export class CategoriesComponent {

  private requestsService = inject(RequestsService);

  getTest() {
    this.requestsService.getAuthTest().subscribe(res => {
      console.log(res);
    })
  }




}
